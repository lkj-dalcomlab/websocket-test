package com.dalcom.serialization.decoder;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.tomcat.entertainment.dto.Omok;

public class OmokDecoder implements Decoder.Text<Omok> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Omok decode(String s) throws DecodeException {
		JSONObject omok = new JSONObject(s);
		return new Omok(omok.getString("state"), omok.getInt("x"), omok.getInt("y"), 
				omok.getString("turnColor"));
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return true;
	}

}
