wsModule_ = undefined;
ws = undefined;
require(['wsConn'], function(wsModule) {
  wsModule_ = wsModule;
  let target = "ws://localhost:8080/tomcatchat/sessionInfo/"; // tomcat
  // let target = "ws://localhost:8088/sessionInfo/asd/123?a=1&b=2"; // jetty
  ws = wsModule.connWS(target);
  if(ws == undefined) {
    return false;
  }
  // alert(ws._socket.remoteAddress);

  document.getElementById("message").onclick = sendMessage;
  document.getElementById("reConn").onclick = reConn;

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

function sendMessage() {
  wsModule_.sendMessage("test");
}

function reConn() {
  // wsModule_.closeWS();
  let pathParam = document.getElementById("pathParam").value;
  let pathParam1 = document.getElementById("pathParam1").value;
  let target = `ws://localhost:8080/tomcatchat/sessionInfo/${pathParam}/${pathParam1}`;
  ws = wsModule_.connWS(target);
}
