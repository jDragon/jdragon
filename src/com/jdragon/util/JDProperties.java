/**
 * 
 */
package com.jdragon.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author RIS
 *
 */
public class JDProperties {
	private Properties prop = new Properties();
	
	public JDProperties(String filename) throws IOException
	{
		prop.load(new FileInputStream(filename));
	}
	
	public String get(String name)
	{
		return prop.getProperty(name);
	}
	
}
