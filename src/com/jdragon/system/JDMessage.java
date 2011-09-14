package com.jdragon.system;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class JDMessage
{
	public static String getMessage(String resource, String key, Object... args)
	{
		String msg="";
		try
		{
			ResourceBundle rb=ResourceBundle.getBundle(resource);
			msg=rb.getString(key);
			msg=MessageFormat.format(msg, args);
		} catch (Exception e)
		{
			msg="Error while accessing resource: " + resource + "  Key: " + key;
			e.printStackTrace();
		}

		return msg;
	}
	
	public static String getJDMessage(String key, Object... args)
	{
		return getMessage("JDMessages", key, args);
	}

}
