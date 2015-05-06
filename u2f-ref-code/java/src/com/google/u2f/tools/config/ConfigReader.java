// Package to read config file - Robin Nandi 06/05/2015

package com.google.u2f.tools.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private Properties prop = new Properties();
	
	public void readConfig() throws IOException {
		
		String configFileName = "config.properties";
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
		 
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + configFileName + "' not found in the classpath");
		}
		
	}
	
	public String getPropertyValue(String propertyName) {
		
		String result = "";
 
		result = prop.getProperty(propertyName);
 
		return result;
	}
}