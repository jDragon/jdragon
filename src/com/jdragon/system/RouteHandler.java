/**
 * 
 */
package com.jdragon.system;

import java.sql.*;
import java.util.Hashtable;
import java.util.regex.*;

/**
 * @author raghukr
 *
 */
public class RouteHandler
{
	private static Hashtable<String, String> _routeHash=null;
	
	public static String getIngredientName(String path, DBAccess db) throws SQLException
	{
		if(_routeHash==null)
		{
			_routeHash=new Hashtable<String, String>();
			initRoutes(db);
		}
		for(String key : _routeHash.keySet())
		{
			if(match(key, path))
				return _routeHash.get(key);
		}
		return "";
	}
	
	private static void initRoutes(DBAccess db) throws SQLException
	{
		Connection conn=db.getConnection();
		Statement stmt = conn.createStatement();
		String sql="select * from jd_routes";
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="", path="";
		while (rs.next())
		{
			  path = rs.getString("path");
			  ingr = rs.getString("ingredient");
			  _routeHash.put(path, ingr);
		}
	}
	
	private static boolean match(String patternStr, String url)
	{
		patternStr="\\Q"+patternStr+"\\E";
		patternStr=patternStr.replaceAll("%", Matcher.quoteReplacement("\\E[a-zA-Z_0-9.]+\\Q"));
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(url);
		
		return matcher.matches();
	}
}
