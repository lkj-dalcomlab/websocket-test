package com.dalcom.tomcat.entertainment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.dalcom.serialization.decoder.OmokDecoder;
import com.dalcom.serialization.encoder.OmokEncoder;
import com.dalcom.tomcat.entertainment.dto.MatchRoom;
import com.dalcom.tomcat.entertainment.dto.Omok;
import com.dalcom.tomcat.entertainment.dto.User;

@ServerEndpoint(value = "/omok/{userId}", decoders = {OmokDecoder.class}, encoders = {OmokEncoder.class})
public class OmokEndpoint {
	private static Map<String, User> users = new HashMap<>();
	private static MatchRoom matchRoom = new MatchRoom();
	
	@OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId) {
		if (checkUser(session, userId)) {
			return;
		}
		matchRoom.addUser(new User(userId, session));
		try {
			if (matchRoom.isReady()) {
				matchRoom.start();
			} else if (matchRoom.isStart()) {
				session.getBasicRemote().sendText(matchRoom.getHistory().toHistory());
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@SuppressWarnings("finally")
	private boolean checkUser(Session session, String userId) {
		if (hasUser(userId)) {
			try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasUser(String userId) {
		return users.containsKey(userId);
	}
	
	@OnMessage
	public void onMessage(Session session, @PathParam("userId")String userId, Omok msg) {
//		System.out.println(session.getRequestURI() + ", id :" + userId + "msg(Omok) : " + msg);
		session.getOpenSessions().forEach(s -> {
            try {
            	if (session != s) {
            		s.getBasicRemote().sendObject(msg);
            	}
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
				e.printStackTrace();
			}
        });
		matchRoom.getHistory().addData(msg);
	}
	
//	@OnError
//	public void onError(Throwable t) {
//		System.out.println(t.get);
//	}
	
//	@OnMessage
//	public void onMessage(Session session, @PathParam("userId")String userId, String msg) {
//		System.out.println(session.getRequestURI() + ", id :" + userId + "msg : " + msg);
////		session.getOpenSessions().forEach(s -> {
////            try {
////            	if (session != s) {
////            		s.getBasicRemote().sendText(msg);
////            	}
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        });
//	}
}
