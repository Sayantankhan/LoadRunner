package com.application.WebserviceConnect.util;

import static com.application.WebserviceConnect.constant.Constant.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	private static PropertyReader propReader = null;
	private String propFile;
	
	private PropertyReader(String porpFile) {
		this.propFile = porpFile;
	}
	
	private PropertyReader() {
		this(PROP_FILE_NAME);
	}
	
	public static PropertyReader getInstance() {
		if(propReader == null)
			propReader = new PropertyReader();
		return propReader;
	}
	
	public String readProperty(String propName) throws IOException {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties.getProperty(propName);
	}
}
