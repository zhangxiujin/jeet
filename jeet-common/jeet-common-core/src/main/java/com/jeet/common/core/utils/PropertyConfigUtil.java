package com.jeet.common.core.utils;

import java.util.Properties;

/**
 *	@author kernel 
 */
public class PropertyConfigUtil {

	private static Properties props = new Properties(); 
	
	public static void loadConfig(String config) throws Exception {
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(config));
	}
	
	public static String getValue(String key) {
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    }
    
}
