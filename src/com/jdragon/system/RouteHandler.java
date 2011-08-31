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
public class RouteHandler
{
	private static Hashtable<String, String> _routeHash=null;
	
	public static String getIngredientName(String path) throws SQLException
	{
		if(_routeHash==null)
		{
			_routeHash=new Hashtable<String, String>();
			initRoutes();
		}
		for(String key : _routeHash.keySet())
		{
			if(match(key, path))
				return _routeHash.get(key);
		}
		return "";
	}
	
	private static void initRoutes() throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.resolvePrefix("select * from [routes]");
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="", path="";
		while (rs.next())
		{
			  path = rs.getString("path");
			  ingr = rs.getString("ingredient");
			  _routeHash.put(path, ingr);
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
	
	public static void reload()
	{
		_routeHash=new Hashtable<String, String>();
		try
		{
			initRoutes();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
