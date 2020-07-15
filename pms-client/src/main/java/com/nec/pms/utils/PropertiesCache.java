package com.nec.pms.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesCache {
	
	private final Properties configProp = new Properties();
	String fileName = "./src/main/resources/application.properties";
		
	private PropertiesCache() {
	InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
	try {
		configProp.load(in);
		} 
	catch (IOException e) {
				// TODO Auto-generated catch block
		e.printStackTrace();
			}
		}
	private static class LazyHolder {
		
	private static final PropertiesCache INSTANCE = new PropertiesCache();	
			}	
	public static PropertiesCache getInstance() {
		return LazyHolder.INSTANCE;
	}
	public String getProperty(String key){
		System.out.println(configProp.get(key));
		return configProp.getProperty(key);
	}
			
	public boolean containsKey(String key){
		return configProp.containsKey(key);
		}
	public void setProperty(String key, String value) throws FileNotFoundException, IOException{
		configProp.setProperty(key, value);
		 FileOutputStream fos = new FileOutputStream(fileName);
		configProp.store(fos , null);
		fos.close();	  
				}

	}


