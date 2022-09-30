define(function() {
  var ws = undefined;
  var exports = {
    connWS: function(target) {
      if('WebSocket' in window) {
        ws = new WebSocket(target);
      } else if('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
      } else {
        alert('WebSocket is not supported by this browser')
      }
      return ws;
    },

    sendMessage: function(msg) {
      if(msg === "") {
        return;
      }
      ws.send(msg);
    },

    closeWS: function() {
      ws.close();
    },

    getState: function() {
      return ws.readyState;
    }
  }
  return exports;
});
