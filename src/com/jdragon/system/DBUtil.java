package com.jdragon.system;

import java.sql.*;

public class DBUtil implements DBAccess
{
	public Connection conn=null;
	public void connect()
	{
        try
        {
            String userName = "test";
            String password = "test";
            String url = "jdbc:mysql://localhost/jd";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
        	System.err.println ("Cannot connect to database server");
        }
	}
	
	public void disconnect()
	{
        if (conn != null)
        {
            try
            {
                conn.close ();
                System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
	}

	@Override
	public Connection getConnection() 
	{
		return conn;
	}
}
