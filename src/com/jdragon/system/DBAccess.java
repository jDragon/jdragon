package com.jdragon.system;

import java.sql.*;
import java.util.regex.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBAccess
{
	private static ThreadLocal<Connection> tlConnection=new ThreadLocal<Connection>();

	final static Pattern pattern =
        Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
    
	public static Connection connect()
	{
		Connection conn=tlConnection.get();
		if(conn!=null)
			return conn;

		try
        {
        	InitialContext initContext = new InitialContext();
        	// Look up the data source
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	DataSource dataSource =(DataSource)envContext.lookup ("jdbc/jdDB");
        	conn = dataSource.getConnection();
			tlConnection.set(conn);
			conn.setAutoCommit(false);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
        	System.err.println ("Cannot connect to database server");
        	e.printStackTrace();
        }
        return conn;
	}
	
	public static void disconnect()
	{
		Connection conn=tlConnection.get();
		if (conn != null)
        {
            try
            {
                conn.close ();
                System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
		tlConnection.remove();
	}

	public static Connection getConnection() 
	{
		return connect();
	}

	public static String SQL(String sql, Object... args)
	{
		String sqlStr=replaceValues(sql, "jd_");
		if(args.length>0)
			sqlStr=String.format(sqlStr, args);
		
		return sqlStr;
	}
/*
	public static String resolvePrefix(String sql)
	{
		String sqlStr=replaceValues(sql, "jd_");
		return sqlStr;
	}
*/	
	private static String replaceValues(final String template, final String prefix)
	{
		    final StringBuffer sb = new StringBuffer();
		    final Matcher matcher = pattern.matcher(template);
		    while(matcher.find())
		    {
		        final String name = matcher.group(1);
		        final String replacement = prefix+name;
		        matcher.appendReplacement(sb, replacement);
		    }
		    matcher.appendTail(sb);
		    return sb.toString();
		}
}

/*
String userName = "test";
String password = "test";
String url = "jdbc:mysql://localhost/jd";
Class.forName ("com.mysql.jdbc.Driver").newInstance ();
conn = DriverManager.getConnection (url, userName, password);
*/