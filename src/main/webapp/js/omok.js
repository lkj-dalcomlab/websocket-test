require(['wsConn'], function(wsModule) {
  wsModule_ = wsModule;
  turnOff = false;
  document.getElementById("btnConn").onclick = connect;
  document.getElementById("btnClose").onclick = close;
  // document.getElementById("btnResult").onclick = particleAlphabet.cancelAnimate;
  omokBoard = document.getElementById("omokBoard");
  idBox = document.getElementById("id");
});

function connect() {
  resultKeyword = "???";
  resultColor = "black";
  resultCanvas = undefined;
  // target = "ws://localhost:8088/chat/text";
  var id = idBox.value;
  target = "ws://" + location.host + "/omok/"+id;
  // target = "ws://localhost:8088/omok/"+id;
  // target = "ws://localhost:8080/tomcatchat/omok/"+id;
  var ws = wsModule_.connWS(target);
  if(ws == undefined) {
    return false;
  }

  var socketLog = document.getElementById("socketLog");
  ws.onopen = function () {
    socketLog.value += 'Info: WebSocket connection opened.\n';
    idBox.disabled = true;
  };

  ws.onmessage = function (event) {
    socketLog.value += 'Received: ' + event.data + '\n';
    const json = JSON.parse(event.data);
    if (json.state == "start") {
      turnColor = json.turnColor;
      placeStone(json.y, json.x);
      turnOff = true;
    } else if (json.state == "ready") {
      originColor = json.turnColor;
      turnOff = json.turnOff;
      createBoard(omokBoard);
    } else if (json.state == "history") {
      originColor = undefined;
      turnOff = true;
      createBoard(omokBoard);
      initPlaceHistory(json.list);
    } else if (json.state == "end") {
      turnColor = json.turnColor;
      placeStone(json.y, json.x);
    }
  };

  ws.onclose = function () {
      socketLog.value += 'Info: WebSocket connection closed.' + '\n';
  };

  ws.onerror = function() {
    socketLog.value += 'Info: WebSocket Error.' + '\n';
    ws.close();
  };
}

function close() {
  if(wsModule_ == undefined) {
    return false;
  }
  wsModule_.sendMessage("{\"state\":\"end\", \"x\":-1, \"y\":-1, \"turnColor\":\"" + originColor + "\"}");
  particleAlphabet.cancelAnimate();
  marix = createArray(board_rows, board_cols);
  wsModule_.closeWS();
  idBox.disabled = false;

  for (var y = 0; y < board_rows; ++y) {
      for (var x = 0; x < board_cols; ++x) {
        var cell = document.getElementById("cell"+x+y);
        cell.className = "";
      }
  }
}

// https://codepen.io/drshoggoth/pen/wCcBy
// https://stackoverflow.com/questions/31417976/css-display-inline-block-text-inside-issue
// margin: auto;
// ?????? ?????? ?????? ????????? auto??? ????????? ?????? ??????????????? ???????????? ????????? ?????? ????????? ?????? ??? ??? ????????????. ??????????????? ???????????? ????????? ????????? ????????? ??????, ????????? ????????? ??? ????????? ???????????? ????????? ????????????.
var board_rows = 15;
var board_cols = 15;
var marix = createArray(board_rows, board_cols);
var turnColor = "black";

function createArray(rows) {
    var arr = new Array(rows);
    for (var i = 0; i < arr.length; ++i)
        arr[i] = [];//Array.from({length: cols}, (v, i) => undefined);
    return arr;
}

/*
 * DIV ????????? ???????????? ?????????.
 */
function createDiv(className, x, y) {
    var element = document.createElement("div");
    if (element !== undefined)
        element.className = className;
        if (x !== undefined)
          element.id = "cell"+x+y;
    return element;
}

/*
 * ????????? ?????? ???????????? ???????????? ????????? ??? ????????? ?????????. ?????? ????????? dy, dx ??? ??????
 * ?????????
 * -------------------------------------------
 *    dx     dy
 * -------------------------------------------
 *    -1     0  :  ??????????????? ??????
 *     1     0  :  ???????????? ??????
 *     0    -1  :  ???????????? ??????
 *    -1     0  :  ??????????????? ??????
 *    -1    -1  :  ??????????????? ??????
 *     1     1  :  ?????????????????? ??????
 *     1    -1  :  ??????????????? ??????
 *    -1     1  :  ?????????????????? ??????
 */
function countOfSequence(color, y, x, dy, dx) {
    var count = 0;
    while (true) {
        if (x >= board_cols || y >= board_rows ||
            x < 0 || y < 0)
            break;
        if (marix[y][x] !== color)
            break;
        count++;
        y += dy;
        x += dx;
    }
    return count;
}

/*
 * ????????? ????????????(????????? ?????? ?????? ???????????? 5??? ?????? ??????) ?????????.
 */
function judgementFinish(color, y, x) {
    var dx = [-1, 1, 0, 0, -1, 1, 1, -1];
    var dy = [0, 0, -1, 1, -1, 1, -1, 1];
    var counts = [0, 1, 2, 3, 4, 5, 6, 7].map(function (i) {
        return countOfSequence(color, y, x, dy[i], dx[i]);
    });

    if (counts[0] + counts[1] - 1 == 5 ||
        counts[2] + counts[3] - 1 == 5 ||
        counts[4] + counts[5] - 1 == 5 ||
        counts[6] + counts[7] - 1 == 5)
        return true;
    return false;
}

/*
 * ????????? ????????? ???????????? ?????? ??????. ????????? ????????? ????????? ??? ?????? ???.
 */
function placeStone(y, x) {
      if (x < 0 || y < 0) {
        return;
      }
      if (marix[y][x] === undefined) {
          marix[y][x] = turnColor;
          var cell = document.getElementById("cell"+x+y);
          cell.className = "cell " + turnColor;
      }
      var state = "start";
      if (judgementFinish(turnColor, y, x)) {
          if (turnOff) {
            resultKeyword = "WIN";
            resultColor = "blue";
            // alert("you win!");
          } else {
            resultKeyword = "LOSE";
            resultColor = "red";
          }
          state = "end";
          particleAlphabet.init();
      }
      return state;
}

function sendPlaceStone(y, x) {
  return function () {
    if (!turnOff || originColor === undefined || marix[y][x] !== undefined) {
      return;
    }
    turnColor = originColor;
    var state = placeStone(y, x);
    turnOff = false;
    wsModule_.sendMessage("{\"state\":\"" + state + "\", \"x\":" + x + ", \"y\":" + y +
                          ", \"turnColor\":\"" + originColor + "\"}");
  }
}

/**
 * ????????? ?????? ?????? ???????????? ?????????.
 */

function createBoard(target) {
    if (target === undefined)
        return;
    var board = createDiv("board");
    var boardBG = createDiv("boardBG");
    for (var y = 0; y < board_rows; ++y) {
        var row = createDiv("row");
        var rowBG = createDiv("rowBG");
        for (var x = 0; x < board_cols; ++x) {
            var cell = createDiv("cell", x, y);
            var cellBG = createDiv("cellBG");
            cell.onclick = sendPlaceStone(y, x);
            row.appendChild(cell);
            rowBG.appendChild(cellBG);
        }
        board.appendChild(row);
        boardBG.appendChild(rowBG);
    }
    target.appendChild(boardBG);
    target.appendChild(board);
    registHover();
}

function registHover() {
    if (originColor === undefined) {
        return;
    }
    var property = 'background-color:'+ originColor + ';' +
        'width: 100%;' +
        'height: 100%;' +
        'border-radius: 50%;' +
        'box-shadow: 1px 1px 1px #404040;' +
        'opacity: 0.5; };';
    var hoverAfter = '.cell:not(.black):not(.white):hover:after {' + property;
    var hoverBefore = '.cell:not(.black):not(.white):hover:before {' + property;
    addCSS(hoverAfter);
    addCSS(hoverBefore);
}

function addCSS(css) {
  var style = document.createElement('style');
  if (style.styleSheet) {
    sytle.styleSheet.cssText = css;
  } else {
    style.appendChild(document.createTextNode(css));
  }
  document.getElementsByTagName('head')[0].appendChild(style);
}

function initPlaceHistory(history) {
  history.forEach((item, i) => {
    marix[item.y][item.x] = item.color;
    var cell = document.getElementById("cell"+item.x+item.y);
    cell.className = "cell " + item.color;
  });
}

// function initBoard(target) {
//   if (target === )
// }
var particleAlphabet = {
  Particle: function(x, y) {
    this.x = x;
    this.y = y;
    this.radius = 1.5;
    this.draw = function(ctx) {
      ctx.save();
      ctx.translate(this.x, this.y);
      ctx.fillStyle = resultColor;
      ctx.fillRect(0, 0, this.radius, this.radius);
      ctx.restore();
    };
  },
  init: function() {
    resultCanvas = document.getElementById("gameResult");
    resultCanvas.style.visibility="visible"
    particleAlphabet.canvas = document.querySelector('canvas');
    particleAlphabet.ctx = particleAlphabet.canvas.getContext('2d');
    particleAlphabet.W = 450;//window.innerWidth;
    particleAlphabet.H = 450;//window.innerHeight;
    particleAlphabet.particlePositions = [];
    particleAlphabet.particles = [];
    particleAlphabet.tmpCanvas = document.createElement('canvas');
    particleAlphabet.tmpCtx = particleAlphabet.tmpCanvas.getContext('2d');

    particleAlphabet.canvas.width = particleAlphabet.W;
    particleAlphabet.canvas.height = particleAlphabet.H;

    particleAlphabet.getPixels(particleAlphabet.tmpCanvas, particleAlphabet.tmpCtx);

    particleAlphabet.makeParticles(1500);
    particleAlphabet.animate();
  },
  makeParticles: function(num) {
    for (var i = 0; i <= num; i++) {
      particleAlphabet.particles.push(new particleAlphabet.Particle(particleAlphabet.W / 2 + Math.random() * 400 - 200, particleAlphabet.H / 2 + Math.random() * 400 -200));
    }
  },
  getPixels: function(canvas, ctx) {
    var keyword = resultKeyword,
      gridX = 3;
      gridY = 3;
    canvas.width = 450;//window.innerWidth;
    canvas.height = 450;//window.innerHeight;
    ctx.fillStyle = 'red';
    ctx.font = 'italic bold 100px Noto Serif';
    ctx.fillText(keyword, canvas.width / 2 - ctx.measureText(keyword).width / 2, canvas.height / 3);
    var idata = ctx.getImageData(0, 0, canvas.width, canvas.height);
    var buffer32 = new Uint32Array(idata.data.buffer);
    if (particleAlphabet.particlePositions.length > 0) particleAlphabet.particlePositions = [];
    for (var y = 0; y < canvas.height; y += gridY) {
      for (var x = 0; x < canvas.width; x += gridX) {
        if (buffer32[y * canvas.width + x]) {
          particleAlphabet.particlePositions.push({x: x, y: y});
        }
      }
    }
  },
  animateParticles: function() {
    var p, pPos;
    for (var i = 0, num = particleAlphabet.particles.length; i < num; i++) {
        p = particleAlphabet.particles[i];
        pPos = particleAlphabet.particlePositions[i];
        if (particleAlphabet.particles.indexOf(p) === particleAlphabet.particlePositions.indexOf(pPos)) {
        p.x += (pPos.x - p.x) * .3;
        p.y += (pPos.y - p.y) * .3;
        p.draw(particleAlphabet.ctx);
      }
    }
  },
  animate: function() {
    cancelId = requestAnimationFrame(particleAlphabet.animate);
    particleAlphabet.ctx.fillStyle = 'white';
    particleAlphabet.ctx.fillRect(0, 0, particleAlphabet.W, particleAlphabet.H);
    particleAlphabet.ctx.clearRect(0, 0, particleAlphabet.W, particleAlphabet.H);
    particleAlphabet.animateParticles();
  },
  cancelAnimate: function() {
    if (resultCanvas !== undefined) {
      resultCanvas.style.visibility="hidden"
    }
    // cancelAnimate(cancelId);
  }
};

// window.onload = particleAlphabet.init;
