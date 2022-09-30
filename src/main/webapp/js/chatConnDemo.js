require(['wsConn'], function(wsModule) {
  wsModule_ = wsModule;
  ws = undefined;
  document.getElementById("btnConn").onclick = connect;
  document.getElementById("btnDisconn").onclick = disconnect;
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

  var target = "ws://localhost:8080/chat/"+id;
  // var target = "ws://"  + location.host + "/chat/"+id;
  // alert(target);
  ws = wsModule_.connWS(target);
  if(ws == undefined) {
    return false;
  }
  var chatView = document.getElementById("chatView");
  ws.onopen = function () {
    chatView.value += 'Info: WebSocket connection opened.\n';
    ws.send(idBox.value + " entry.");
  };

  ws.onmessage = function (event) {
    chatView.value += event.data + '\n';
    var receiveMsg = event.data.split(':');
    if (receiveMsg.length >= 2) {
      receiveMessage(receiveMsg[0], receiveMsg[1]);
    }
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

// function receiveMessage(msg) {
//   var app = angular.module('chatApp', []);
//   app.controller('MessageCtrl', function($scope) {
//     alert("receive");
//     var message = {
//       Name: '111',
//       Message: msg
//     };
//     $scope.messages.push(message);
//   });
// }

function sendMessage() {
  if(ws !== undefined) {
    var chatText = document.getElementById("sendText").value;
    wsModule_.sendMessage(chatText);
  }
  document.getElementById("sendText").value = "";
}

function disconnect() {
  wsModule_.closeWS();
  idBox.disabled = false;
  btnConn.disabled = false;
}

(function() {
  var app = angular.module('chatApp', []);

  var getKeyboardEventResult = function(keyEvent) {
    return (window.event ? keyEvent.keyCode : keyEvent.which);
  }

  app.controller('MessageCtrl', function($scope) {
    $scope.messages = [];

    $scope.sendMessage = function($event) {
      if (getKeyboardEventResult($event) == 13) {
        sendMessage();
      };
    };

    receiveMessage = function(id, msg) {
      var message = {
        Name: id,
        Message: msg
      };
      $scope.messages.push(message);
      $scope.$apply();
    };

    $scope.connect = connect;
  });

})();
