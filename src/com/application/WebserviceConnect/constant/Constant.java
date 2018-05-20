package com.application.WebserviceConnect.constant;

public interface Constant {

	public String PROP_FILE_NAME="application.properties";
	public int DEFAULT_THREAD_EXECUTOR = 100;
	public String LOOGLY_API_URL = "http://logs-01.loggly.com/inputs/{client_token}/tag/http/";
	public String URL_FORMATION_PATTERN = "\\{[a-zA-Z0-9_]*\\}" ;
	//"{^a-zA-Z0-9_.]+}$";
}
