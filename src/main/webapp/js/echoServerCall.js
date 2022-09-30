let ws = undefined;
require(['wsConn'], function(wsModule) {
  let target = "ws://echo.websocket.org";
  ws = wsModule.connWS(target);
  if(ws == undefined) {
    return false;
  }
  // alert(ws._socket.remoteAddress);

  document.getElementById("call").onclick = call;
  document.getElementById("close").onclick = close;

  var socketLog = document.getElementById("log");
  ws.onopen = function () {
    socketLog.innerText += 'Info: WebSocket connection opened.\n';
  };

  ws.onmessage = function (event) {
    socketLog.innerText += 'Received: ' + event.data + '\n';
  };

  ws.onclose = function () {
      socketLog.innerText += 'Info: WebSocket connection closed.' + '\n';
  };

  ws.onerror = function() {
    socketLog.innerText += 'Info: WebSocket Error.' + '\n';
    ws.close();
  };
});

function call() {
  let echoText = document.getElementById("echoText").value;
  ws.send(echoText);
}

function close() {
  ws.close();
}
