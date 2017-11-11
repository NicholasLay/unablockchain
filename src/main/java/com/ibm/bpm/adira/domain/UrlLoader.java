package com.ibm.bpm.adira.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UrlLoader 
{
	Properties properties = new Properties();
	InputStream inputStream = null;
	private String service;
	private String url;
	
	public UrlLoader(String service)
	{
		this.service = service;
	}
	
	public String getUrl() throws IOException
	{
		inputStream = new FileInputStream("C:/Work/adira-api/src/main/resources/url.properties");
		properties.load(inputStream);
		url = properties.getProperty(this.service);
		return url;
		
	}
}
