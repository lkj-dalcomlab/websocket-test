package com.dalcom.file;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

public class PotaFile {
	public JSONObject getPathToJSONObject(String path) {
		JSONObject joRoot = new JSONObject();
		JSONArray jaRoot = new JSONArray();
//		joRoot.put(path.isEmpty() ? "root" : "path", jaRoot);
		File fRoot = new File(path);
		if(!fRoot.exists()) {
			return joRoot;
		}
		joRoot.put("name", fRoot.getName());
		addDirectoryInFiles(fRoot, joRoot, jaRoot);
		return joRoot;
	}
//	private void addFile(File file, JSONObject joFile) {
//		
//	}
	private long addDirectoryInFiles(File files, JSONObject cover, JSONArray dir) {
		long dirSize = 0;
		cover.put("list", dir);
		cover.put("type", "dir");
		for(File file : files.listFiles()) {
			JSONObject jo = new JSONObject();
			dir.put(jo);
			jo.put("name", file.getName());;
			if(file.isDirectory()) {
//				dirSize += addDirectoryInFiles(file, jo, new JSONArray());
//				jo.put("size", dirSize);
				jo.put("type", "dir");
				jo.put("size", getDirSize(file));
			} else {
				dirSize += file.length();
				jo.put("type", "file");
				jo.put("size", file.length());
			}
		}
		return dirSize;
	}
	
	private long getDirSize(File files) {
		long dirSize = 0;
		for(File file : files.listFiles()) {
			dirSize += file.isDirectory() ? getDirSize(file) : file.length();
		}
		return dirSize;
	}
}
