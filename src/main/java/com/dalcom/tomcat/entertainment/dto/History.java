package com.dalcom.tomcat.entertainment.dto;

import org.json.JSONArray;
import org.json.JSONObject;

public class History {
	private JSONObject jsonRoot = new JSONObject();
	private JSONArray historyList = new JSONArray();
	
	public History() {
		jsonRoot.put("state", "history");
		jsonRoot.put("list", historyList);
	}
	
	public History addData(Omok data) {
		JSONObject rowData = new JSONObject();
		rowData.put("x", data.getX());
		rowData.put("y", data.getY());
		rowData.put("color", data.getTurnColor());
		historyList.put(rowData);
		return this;
	}
	
	public String toHistory() {
		return jsonRoot.toString();
	}
}
