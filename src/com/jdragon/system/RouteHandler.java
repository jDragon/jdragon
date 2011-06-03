/**
 * 
 */
package com.jdragon.system;

import java.sql.*;
import java.util.Hashtable;

/**
 * @author RIS
 *
 */
public class RouteHandler
{
	private Hashtable<String, String> _routeHash=new Hashtable<String, String>();
	
	public static String getIngredientName(String path, DBAccess db) throws SQLException
	{
		Connection conn=db.getConnection();
		Statement stmt = conn.createStatement();
		String sql="select * from jd_routes where '"+path+"' like CONCAT(path, '%')";
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="";
		while (rs.next())
		{
			  ingr = rs.getString("ingredient");
		}
		return ingr;
	}
	
	private void initRoutes(DBAccess db) throws SQLException
	{
		Connection conn=db.getConnection();
		Statement stmt = conn.createStatement();
		String sql="select * from jd_routes";
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="", path="";
		while (rs.next())
		{
			  ingr = rs.getString("path");
			  ingr = rs.getString("ingredient");
			  _routeHash.put(path, ingr);
		}
	}
}
