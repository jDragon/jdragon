/**
 * 
 */
package com.jdragon.system;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

/**
 * @author raghukr
 *
 */
public class JDSettings
{
	private static Hashtable<String, String> _settingsHash=null;
	
	public static String getSetting(String name) throws SQLException
	{
		if(_settingsHash==null)
		{
			_settingsHash=new Hashtable<String, String>();
			initSettings();
		}
		for(String key : _settingsHash.keySet())
		{
			if(match(key, name))
				return _settingsHash.get(key);
		}
		return "";
	}
	
	private static void initSettings() throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.resolvePrefix("select * from [settings]");
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="", path="";
		while (rs.next())
		{
			  path = rs.getString("path");
			  ingr = rs.getString("ingredient");
			  _settingsHash.put(path, ingr);
		}
	}
	
	private static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();
	
	private static boolean match(String patternStr, String url)
	{
		Pattern patrn=patternMap.get(patternStr);
		if(patrn==null)
		{
			String patternStrEsc="\\Q"+patternStr+"\\E";
			patternStrEsc=patternStrEsc.replaceAll("%", Matcher.quoteReplacement("\\E[a-zA-Z_0-9.]+\\Q"));
			patrn = Pattern.compile(patternStrEsc);
			patternMap.put(patternStr, patrn);
		}
		Matcher matcher = patrn.matcher(url);
		
		return matcher.matches();
	}
}
