window.onload = function() {
  portal = document.getElementById("portal");
  document.getElementById("chatting").onclick = showChat;
  document.getElementById("chattingDemo").onclick = showChatDemo;
  // document.getElementById("maxData").onclick = showMaxdata;
  // document.getElementById("async").onclick = showAsync;
  // document.getElementById("sessionInfo").onclick = showSessionInfo;
  // document.getElementById("echoServerCall").onclick = echoServerCall;
  document.getElementById("playOmok").onclick = omokCall;
  // function Foo() {
  //   alert("Foo");
  //   var test = function test() {
  //     alert("test");
  //   }
  // }
  // Foo.prototype = portal;
  // var mock = new Foo();
  // Foo();
  // mock.test();
}

function showChat() {
  portal.src = "html/chatting.html";
}
function showChatDemo() {
  portal.src = "html/chattingDemo.html";
}
function showMaxdata() {
  portal.src = "html/maximumdata.html";
}
function showAsync() {
  portal.src = "html/remoteEndpoint.html";
}
function showSessionInfo() {
  portal.src = "html/sessionInfo.html";
}
function echoServerCall() {
  portal.src = "html/echoServerCall.html";
}
function omokCall() {
  portal.src = "html/omok_05.html";
}
