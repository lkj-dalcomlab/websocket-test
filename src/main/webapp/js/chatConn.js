require(['wsConn'], function(wsModule) {
  wsModule_ = wsModule;
  document.getElementById("btnConn").onclick = connect;
  document.getElementById("btnDisconn").onclick = disconnect;
  document.getElementById("btnSend").onclick = send;
  btnConn = document.getElementById("btnConn");
});

function connect() {
  idBox = document.getElementById("chatID");
  var id = idBox.value;
  if(id === "") {
    idBox.focus();
    return;
  }
  btnConn.disabled = true;
  idBox.disabled = true;
  connected(id);
}

function connected(id) {
  // var target = "ws://localhost:8080/tomcatchat/chat/"+id;
  // var target = "ws://localhost:8088/chat2/"+id+"/aaa";

  // var target = "ws://localhost/chat/"+id;
  var target = "ws://"  + location.host + "/chat/"+id;
  var ws = wsModule_.connWS(target);
  if(ws == undefined) {
    return false;
  }
  var chatView = document.getElementById("chatView");
  ws.onopen = function () {
    chatView.value += 'Info: WebSocket connection opened.\n';
    // ws.send(idBox.value + " entry.");
  };

  ws.onmessage = function (event) {
    chatView.value += event.data + '\n';
  };

  ws.onclose = function () {
    chatView.value += 'Info: WebSocket connection closed.' + '\n';
  };

  ws.onerror = function() {
    ws.close();
    idBox.disabled = false;
  };
  return true;
}

function send() {
  var chatText = document.getElementById("chatText").value;
  wsModule_.sendMessage(chatText);
}

function disconnect() {
  wsModule_.closeWS();
  idBox.disabled = false;
  btnConn.disabled = false;
}
