/**
 * 
 */
package com.jdragon.system;

import java.sql.*;
import java.util.*;

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
		return _settingsHash.get(name);
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
			  path = rs.getString("name");
			  ingr = rs.getString("value");
			  _settingsHash.put(path, ingr);
		}
	}
}
