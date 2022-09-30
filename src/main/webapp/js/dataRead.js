let output;
require(['wsConn'], function(wsModule) {
  wsModule_ = wsModule;
  initWsState();
  initEvent(wsModule);

  isText = document.getElementById("selection").value === "true";
  // target = "ws://localhost:8088/chat/text";
  target = "ws://localhost:8080/tomcatchat/chat/text";
  var ws = wsModule_.connWS(target);
  if(ws == undefined) {
    return false;
  }

  var socketLog = document.getElementById("socketLog");
  ws.onopen = function () {
    socketLog.value += 'Info: WebSocket connection opened.\n';
  };

  ws.onmessage = function (event) {
    socketLog.value += 'Received: ' + event.data + '\n';
  };

  ws.onclose = function () {
      socketLog.value += 'Info: WebSocket connection closed.' + '\n';
  };

  ws.onerror = function() {
    socketLog.value += 'Info: WebSocket Error.' + '\n';
    ws.close();
  };
});

window.onunload = function() {
  wsModule_.closeWS();
}

// function upload() {
//   var filePath = "../example/maxText.txt"
// }
function initWsState() {
  CONNECTING = 0;
  OPEN = 1;
  CLOSING = 2;
  CLOSED = 3;
}
function initEvent(wsModule) {
  document.getElementById("fileLoad").onclick = openFile;
  document.getElementById("selection").onchange = changeDataType;
  document.getElementById("dataSend").onclick = send;
}

function send() {
  if(wsModule_.getState() == CLOSED) {
    wsModule_.connWS(target);
  }
  // if(!isText) {
  //   wsModule_.sendMessage("fn:" + fileName);
  // }
  wsModule_.sendMessage(output.value);
  // if(!isText) {
  //   wsModule_.sendMessage("end");
  // }
}

function changeDataType() {
  wsModule_.closeWS();
  isText = document.getElementById("selection").value === "true";
  if(isText) {
    target = "ws://localhost:8080/tomcatchat/chat/text";
  } else {
    target = "ws://localhost:8080/file";
  }
  wsModule_.connWS(target);
}

function openFile() {
    var input = document.createElement("input");
    var fileInfo = document.getElementById("fileInfo");

    input.type = "file";
    input.accept = "text/plain, image/jpeg, .pkg, .dmg, .mp4"; // 확장자가 xxx, yyy 일때, ".xxx, .yyy"

    if(isText) {
      output = document.getElementById("textdoc");
    }
    else {
      output = { value: undefined };
    }
    input.onchange = function (event) {
        processFile(event.target.files[0], isText);
        var fileSize = Math.round(event.target.files[0].size / (1024*1024) * 10) / 10;
        fileName = event.target.files[0].name;
        fileInfo.value = fileName + " - " + fileSize+"Mb";
    };

    input.click();
}

function processFile(file, isText) {
    var reader = new FileReader();

    reader.onload = function() {
        output.value = reader.result;
        // output = reader.result;
    };
    if(isText) {
      reader.readAsText(file, /* optional */ "utf-8");
    }
    else {
      reader.readAsArrayBuffer(file);
    }
}
