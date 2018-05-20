package com.application.WebserviceConnect.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.application.WebserviceConnect.constant.Constant.*;
public class Utility {

	public static String createUrl(String url,Object... objects) {
		
		String urlBuilder = "";
		int element = 0;
			
		String[] elementArray = LOOGLY_API_URL.split(URL_FORMATION_PATTERN);
		for(String elmentArray : elementArray) {
			urlBuilder += elmentArray;
			if(element < objects.length)
				urlBuilder += objects[element++];
		}
		return urlBuilder;
	}
	
//	public static void main(String[] args) throws IOException {
//		String api_key_token = PropertyReader.getInstance().readProperty("application.syslog.loggly.apikey");
//		String api_domain_name = PropertyReader.getInstance().readProperty("application.syslog.loggly.domainname");
//		System.out.println(createUrl(LOOGLY_API_URL,api_domain_name,api_key_token));
//	}
}
