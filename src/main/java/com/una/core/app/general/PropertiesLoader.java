package com.una.core.app.general;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader 
{
	private Properties properties = new Properties();
	private InputStream inputStream = null;
	private String propertiesValue = "";
	
	public String loadProperties(String key)
	{
		try 
		{
			inputStream = getClass().getClassLoader().getResourceAsStream("global.properties");
			//inputStream = new FileInputStream("target/classes/global.properties");
			properties.load(inputStream);
			
			propertiesValue = properties.getProperty(key);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (inputStream!=null)
			{
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return propertiesValue;
	}
	
	
}
