package com.dalcom.tomcat.chat;

import java.io.IOException;
import java.util.function.Consumer;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
//@ServerEndpoint("")
//@ServerEndpoint("1/chat2")
//@ServerEndpoint("/chat2/{{{param}}}")
//@ServerEndpoint("/@#$%[];")
//@ServerEndpoint("/")
//@ServerEndpoint("/chat2/{!@#$%^&*()_+`~[];:,<.>?'\"}")
//@ServerEndpoint("/chat2/{\t	 u\n\f\r\\x0B s e r I d }")
//@ServerEndpoint("/chat2/{us!e@r#Id}")
//@ServerEndpoint("/chat2/{p a r a m}/{param}")
//@ServerEndpoint("/chat2/{param}/{p a r a m}")
//@ServerEndpoint("/chat/123/a/b")
//@ServerEndpoint("/chat/{param}/{param}")
//@ServerEndpoint("/chat2/{param}12/{가나다}")
//@ServerEndpoint("/chat2/{param}/{가나다}/ ")
//@ServerEndpoint("/chat")
@ServerEndpoint("/chat/{param}/a")
public class ServerChat2 { //extends Endpoint {
	
    @OnOpen
	public void chatOpen(Session session) {
		System.out.println("chat2Open: " + session.getRequestURI() + " - id: " + session.getId());
    }

    @OnMessage
	public void chatMessage(String message, Session session, @PathParam("가나다") String userId) throws IOException {
    	String chatMessage = "msg[" + userId + "(" + session.getId() + ")]: " + message;
    	int maxTextSize = session.getMaxTextMessageBufferSize();
    	int maxBinarySize = session.getMaxBinaryMessageBufferSize();
    	System.out.println(chatMessage);
    	session.getOpenSessions().forEach(new Consumer<Session>() {
    		@Override
    		public void accept(Session session) {
    			session.getAsyncRemote().sendText(chatMessage);
    		}
		});
    }

    @OnClose
	public void chatClose(CloseReason reason, Session session) {
		System.out.println("Closing chat: " + reason.getReasonPhrase());
    }
    
    @OnError
    public void chatError(Session session, Throwable e) {
    	System.out.println("chat Error[" + session.getRequestURI() + "] " + e.getMessage());
    	try {
			session.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

//	@Override
//	public void onOpen(Session session, EndpointConfig config) {
//		// TODO Auto-generated method stub
//		System.out.println("Extends opne method.");
//	}
}
