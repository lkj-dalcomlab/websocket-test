let transferID = ['a', 'b', 'c', 'd', 'e'];
let transferNum = 0;
require(['wsConn'], function(wsModule){
  wsModule_ = wsModule;

  document.getElementById('files').addEventListener('change', handleFileSelect, false);
  document.getElementById('syncRun').onclick = syncRun;
  document.getElementById('asyncRun').onclick = asyncRun;
  // asyncRun().then(function(text) {
  //   console.log(text);
  // }, function(error){
  //   console.error(error);
  // });
});

function syncRun() {
  let target = "ws://localhost:8080/tomcatchat/sync";
  let ws = wsModule_.connWS(target);

  let socketLog = document.getElementById("paper");
  ws.onopen = function () {
    socketLog.innerText += 'Info: WebSocket connection opened.\n';
  };

  // ws.onmessage = function (event) {
  //   socketLog.innerText += 'Received: ' + event.data + '\n';
  // };

  ws.onmessage = function (event) {
    socketLog.innerText += 'Received(): ' + event.data + '\n';
  };

  ws.onclose = function () {
    socketLog.innerText += 'Info: WebSocket connection closed.' + '\n';
  };

  ws.onerror = function() {
    socketLog.innerText += 'Info: WebSocket Error.' + '\n';
    ws.close();
  };
}

function asyncRun() {
  // return new Promise((resolve, reject)=> {
  //   let count = 0;
  //   let curTransferID = transferID[transferNum++];
  //   let intervalID = setInterval(function() {
  //     wsModule_.sendMessage(curTransferID + ": " + ++count);
  //     // reject("error msg");
  //   }, 2000);
  //   setTimeout(function() {
  //     clearInterval(intervalID);
  //   }, 10000);
  // });
}

function handleFileSelect(evt) {
  removeChildNodes(document.getElementById('list'));
  let files = evt.target.files;
  for(let i = 0, f; f=files[i]; ++i) {
    if(!f.type.match('image.*')) {
      continue;
    }
    let reader = new FileReader();
    reader.onload = (function(theFile) {
      return function(e) {
        let span = document.createElement('span');
        span.innerHTML = ['<img class="thumb", src="', e.target.result, '"title="', escape(theFile.name), '"/>'].join('');
        document.getElementById('list').insertBefore(span, null);
        wsModule_.sendMessage("up:"+theFile.name);
        e.result
      };
    })(f);

    reader.readAsDataURL(f);
  }
}
function removeChildNodes(parent) {
  let count = parent.childElementCount;
  for(let i = 0; i < count; ++i) {
    parent.removeChild(parent.childNodes[i]);
  }
}
