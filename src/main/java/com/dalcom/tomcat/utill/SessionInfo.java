package com.dalcom.tomcat.utill;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Extension;
import javax.websocket.Extension.Parameter;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/sessionInfo/")
public class SessionInfo {	
	@OnOpen
	public void onOpen(Session session) throws DeploymentException, IOException, URISyntaxException {
//		session.addMessageHandler(new MessageHandler.Partial<String>() {
//			@Override
//			public void onMessage(String message, boolean isLast) {
//				System.out.println("whole message : " + message);
//			}
//		});
//		session.addMessageHandler(new MessageHandler.Whole<String>() {
//			@Override
//			public void onMessage(String message) {
//				System.out.println("whole1 message : " + message);
//			}
//		});
		System.out.println("------------------------------------");
		
		session.addMessageHandler(ByteBuffer.class, (byteBuffer) -> {
			try {
				session.getBasicRemote().sendText("send");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(byteBuffer.toString() + " interface message");
		});
//		session.addMessageHandler(String.class, message -> {
//			System.out.println(message + " interface message");
//		});
		Iterator<MessageHandler> mhs = session.getMessageHandlers().iterator();
		while(mhs.hasNext()) {
			MessageHandler mh = mhs.next();
			System.out.println(mh.getClass().getName());
//			System.out.println(mh.getClass().getCanonicalName());
//			System.out.println(mh.getClass().getTypeName());
			System.out.println(mh.getClass().toString());
		}
//		for(MessageHandler mh = mhs.next(); mhs.hasNext(); mh = mhs.next()) {
//			System.out.println(mh.getClass().getName());
//		}
		System.out.println("sessionID : " + session.getId());
		System.out.println("getRequestURI : " + session.getRequestURI().toString());
		System.out.println("getProtocolVersion : " + session.getProtocolVersion());
		System.out.println("MaxBinaryMessageBufferSize : " + session.getMaxBinaryMessageBufferSize());
		System.out.println("getQueryString : " + session.getQueryString());
		System.out.println("MaxTextMessageBufferSize : " + session.getMaxTextMessageBufferSize());
		System.out.println("getNegotiatedSubprotocol : " + session.getNegotiatedSubprotocol());
		System.out.println("getNegotiatedExtensions : ");
		List<Extension> list = session.getNegotiatedExtensions();
		for(Extension ex : list) {
			System.out.println("\t" + ex.getName() + " : ");
			List<Parameter> params = ex.getParameters();
			for(Parameter param : params) {
				System.out.print("\t\tname : " + param.getName() + ", value : " + param.getValue());
			}
			System.out.println();
		}
		System.out.println("isSecure : " + session.isSecure());
		System.out.println("isOpen : " + session.isOpen());
//		session.setMaxIdleTimeout(1000);
		System.out.println("getMaxIdleTimeout : " + session.getMaxIdleTimeout());
		
		printMapList(session.getPathParameters(), "getPathParameters");
		printMapList(session.getUserProperties(), "getUserProperties");
		
//		session.close();
		printMapList(session.getRequestParameterMap(), "getRequestParameterMap");
		
		System.out.print("getUserPrincipal : ");
		System.out.println(session.getUserPrincipal() != null ? 
				session.getUserPrincipal().getName() : "null");
		
		System.out.println("getOpenSessions : ");
		Iterator<Session> sessions = session.getOpenSessions().iterator();
		while(sessions.hasNext()) {
			Session curSession = sessions.next();
			System.out.println("\tsessionId : " + curSession.getId());
			System.out.println("\t" + curSession.getRequestURI().toString());
		}
		
//		session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "normal_closure"));
//		session.close();
		
//		Session neighbor = session.getContainer().connectToServer(ServerChat.class, new URI("ws://localhost:8088/sessionInfo"));
//		neighbor.getAsyncRemote().sendText("hi chat");
		
//		ByteBuffer pingBuf = ByteBuffer.wrap("ping".getBytes());
//		session.getAsyncRemote().sendPing(pingBuf);
		session.getBasicRemote().sendText("test1", false);
		session.getBasicRemote().sendText("test2", true);
		
		System.out.println("------------------------------------");
	}
	
	private <T> void printMapList(Map<String, T> mapList, String mapName) {
		System.out.println(mapName + " :");
		Iterator<String> keyList = mapList.keySet().iterator();
		while(keyList.hasNext()) {
			String key = keyList.next();
			T value = mapList.get(key);
			System.out.println("\tkey :" + key + ", value: " + value.toString());
		}
	}
//	@OnMessage
//	public void onMessage(byte[] bytes) {
//		
//	}
	
//	@OnMessage
//	public void onMessage(byte[] bytes, boolean isLast) {
//		
//	}
	
	@OnMessage
	public void onMessage(String msg, Session session) {
		System.out.println("annotation message : " + msg);
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@OnMessage
//	public void onMessage(Reader reader) {
//		
//	}
//	@OnMessage
//	public void onMessage(InputStream is) {
//		
//	}
	
	
//	@OnMessage
//	public void onMessage(String msg, boolean last) {
//		System.out.println("annotation message : " + msg);
//	}
	
//	@OnMessage
//	public void onMessage(String msg, @PathParam("param") String param) {
//		System.out.println("annotation message : " + msg + "[" + param + "]");
//	}
		
	@OnMessage
	public void onMessage(PongMessage msg) {
		ByteBuffer pongBuf = msg.getApplicationData();
		byte bytes[] = new byte[pongBuf.limit()];
		pongBuf.get(bytes);
		String pongMsg = new String(bytes);
		System.out.println("pong message : " + pongMsg);
	}
	
	@OnClose
	public void onClose(CloseReason reason) {
		System.out.println("close session : " + reason.getCloseCode() + ", " + reason.getReasonPhrase() );
	}
	
	@OnError
	public void onError(Session session, Throwable e) {
		System.out.println(e.getMessage());
	}
}
