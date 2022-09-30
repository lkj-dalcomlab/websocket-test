package com.dalcom.serialization.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.tomcat.entertainment.dto.Omok;

public class OmokEncoder implements Encoder.Text<Omok> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Omok object) throws EncodeException {
		JSONObject omok = new JSONObject();
		omok.put("state", object.getState());
		omok.put("x", object.getX());
		omok.put("y", object.getY());
		omok.put("turnColor", object.getTurnColor());
		System.out.println(omok.toString());
		return omok.toString();
	}

}
