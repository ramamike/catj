package com.ramamike.cat.models.config;

import java.util.HashMap;

public class Feature {
	private String title;
	private HashMap<String, String> feutureConfig;
	
	public Feature(String title, HashMap<String, String> feutureConfig) {
		this.title = title;
		this.feutureConfig = feutureConfig;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public HashMap<String, String> getFeutureConfig() {
		return feutureConfig;
	}
	public void setFutureConfig(HashMap<String, String> futureConfig) {
		this.feutureConfig = futureConfig;
	}
	
}
