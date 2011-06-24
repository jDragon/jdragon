package com.jdragon.system;

import java.sql.*;
import java.util.regex.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil implements DBAccess
{
    final static Pattern pattern =
        Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
    
	public Connection conn=null;
	public void connect()
	{
        try
        {
/*
        	String userName = "test";
            String password = "test";
            String url = "jdbc:mysql://localhost/jd";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
*/
        	InitialContext initContext = new InitialContext();
        	// Look up the data source
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	DataSource dataSource =(DataSource)envContext.lookup ("jdbc/jdDB");
        	conn = dataSource.getConnection();
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
        	System.err.println ("Cannot connect to database server");
        	e.printStackTrace();
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

	@Override
	public String resolvePrefix(String sql)
	{
		String sqlStr=replaceValues(sql, "jd_");
		return sqlStr;
	}
	
	private static String replaceValues(final String template,
		    final String prefix){

		    final StringBuffer sb = new StringBuffer();
		    final Matcher matcher = pattern.matcher(template);
		    while(matcher.find()){
		        final String name = matcher.group(1);
		        final String replacement = prefix+name;
		        matcher.appendReplacement(sb, replacement);
		    }
		    matcher.appendTail(sb);
		    return sb.toString();
		}
}
