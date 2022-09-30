package com.dalcom.tomcat.chat;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.dalcom.object.Dog;
import com.dalcom.serialization.decoder.DogTextDecoder;
import com.dalcom.tomcat.handler.WholeMessageHandler;
//@ClientEndpoint
//@ServerEndpoint("/chat/{userId}/{test}")
@ServerEndpoint("/chat/{userId}")
//@ServerEndpoint(value = "/chat/{userId}/a/b", decoders = {DogTextDecoder.class})
public class ServerChat {
//	public ServerChat(String str) {
//		// TODO Auto-generated constructor stub
//		System.out.println(str);
//	}
	//Duplicate Error
//	@OnOpen
//	public void chatOpen() {
//		System.out.println("chatOpen(noParam)");
//	}
    @OnOpen
	public void chatOpen(Session session, Session session1, EndpointConfig config, @PathParam("userId") String userId) {
		System.out.println("chatOpen: " + session.getRequestURI() + " - id: " + session1.getId() + " test : " + userId);
		
//		허용하지 않음.
//		session.addMessageHandler(char[].class, (msg) -> {
//			try {
//				session.getBasicRemote().sendText("send");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(msg.toString() + " interface message");
//		});
		
		
//		session.addMessageHandler(String.class, new Whole<String>() {
//
//			@Override
//			public void onMessage(String message) {
//				System.out.println(message);
//			}
//		});
//		session.addMessageHandler(String.class, new WholeMessageHandler<String>());
		
//		MessageHandler handler = new WholeMessageHandler<String>();
//		session.addMessageHandler(handler);
		
//		session.addMessageHandler(new MessageHandler.Whole<String>() {
//			@Override
//			public void onMessage(String message) {
//				System.out.println(message);
//			}
//		});
    }
	
	@OnMessage
	public void message(String msg, @PathParam("userId")String userId, Session session) {
		System.out.println(session.getRequestURI());
		System.out.println(msg);
		session.getOpenSessions().forEach(se->{
//			System.out.println(se.getRequestURI());
			try {
				se.getBasicRemote().sendText(userId + " : " + msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
//		throw new IllegalArgumentException();
	}
//	@OnMessage
//	public void message(Dog dog, Session session, boolean last) {
//		System.out.println(session.getRequestURI());
//		System.out.println(dog.toString());
//	}
    
//    @OnMessage
//    public void partialMessage(String message, Session session, boolean isLast) {
//    	System.out.println("------------------------------------------");
//    	System.out.println("partial Msg(" + isLast + ") : " + message);
//    	System.out.println("------------------------------------------");
//    }
    
//    @OnMessage
//	public void chatMessage(WsSession session, /* byte[] bytes, boolean b, int i, */String msg/* , int j */, boolean b) {
//    	System.out.println(session.getRequestURI());
//    	System.out.println(msg);
//    }
//	@OnMessage
//    public void chatMessage(boolean b) {
//
//    }
	
//	@OnMessage
//    public void chatMessage(short s) {
//
//    }
    
//    @OnMessage
//    public void chatMessage(int i, boolean last) {
//    	System.out.println(i + " : " + last);
//    }
    
	  @OnMessage
	  public void chatMessage(PongMessage b) {
		  System.out.println(b);
	  }
    
//	  @OnMessage
//	  public void chatMessage(PongMessage b, ByteBuffer byteBuffer) {
//	
//	  }
    
//	  @OnMessage
//	  public String chatMessage(byte b, Session session) {
//		  System.out.println(session.getRequestURI());
//		  System.out.println(b);
//		  return "test";
//	  }
    
//    @OnMessage
//	public void chatMessage(String msg, boolean isLast) {
//		System.out.println(msg + "/ last: " + isLast);
//	}
//    @OnMessage
//	public void chatMessage(int i, boolean isLast) {
//		System.out.println(i);
//	}

//    @OnMessage
//	public void chatMessage(ByteBuffer byteBuffer) {
//		System.out.println();
//	}
//    @OnMessage
//	public void chatMessage(byte b) {
//		System.out.println(b);
//	}
    
//    @OnMessage
//	public void chatMessage(int i, Boolean isLast) {
//		System.out.println();
//	}
    
//    @OnMessage
//	public void chatMessage() {
//		System.out.println();
//	}

//    @OnMessage
//	public void chatMessage(char message, @PathParam("asd") String userId, Session session) {
//    	String chatMessage = "msg[" + userId + "(" + session.getId() + ")]: " + message;// + ", " + test;
//    	int maxTextSize = session.getMaxTextMessageBufferSize();
//    	int maxBinarySize = session.getMaxBinaryMessageBufferSize();
//    	System.out.println(chatMessage);
//    	session.getOpenSessions().forEach(new Consumer<Session>() {
//    		@Override
//    		public void accept(Session session) {
//    			if(session.isOpen()) {
//    				session.getAsyncRemote().sendText(chatMessage);
//    			}
//    		}
//		});
//    }
    
//    @OnMessage
//    public void chatMessage(char[] massage) {
//    	System.out.println(massage);
//    }
    
//	@OnMessage
//	public void chatMessage(Object obj) {
//		
//	}

    @OnClose
	public void chatClose(CloseReason reason, Session session, CloseReason reason1) {
		System.out.println("Closing chat: " + reason.getReasonPhrase());
    }
    
    @OnError
	public void chatError(Session session, Throwable e, Throwable e1/* , @PathParam("asd") int i */) {
    	System.out.println("chat Error[" + session.getRequestURI() + "] " + e.getMessage());
//    	try {
//			session.close();
//		} catch (IOException exception) {
//			// TODO Auto-generated catch block
//			exception.printStackTrace();
//		}
    }
}
