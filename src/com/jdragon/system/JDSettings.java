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
	
	public static String getSetting(String name)
	{
		if(_settingsHash==null)
		{
			_settingsHash=new Hashtable<String, String>();
			try
			{
				initSettings();
			} 
			catch (SQLException e)
			{
				return null;
			}
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
	
	public static void createSetting(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=getSetting(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
			sql="insert into [settings] (name, value) values ('"+name+"', '"+value+"')";
		else
			sql="update [settings] set value='"+value+"' where name='"+name+"'";
		sql=DBAccess.resolvePrefix(sql);
		stmt.execute(sql);
		conn.commit();
		reloadCache();
	}
	
	public static void removeSetting(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=getSetting(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
			sql="insert into [settings] (name, value) values ('"+name+"', '"+value+"')";
		else
			sql="update [settings] set value='"+value+"' where name='"+name+"'";
		sql=DBAccess.resolvePrefix(sql);
		stmt.execute(sql);
		conn.commit();
		reloadCache();
	}
	
	static void reloadCache()
	{
		_settingsHash=new Hashtable<String, String>();
		try
		{
			initSettings();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
