package com.jdragon.system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class JDUser
{
	private String _user, _firstname, _lastname;
	private int _uid=0;
	private Map<Integer, String> _roles=new HashMap<Integer, String>();
	
	private static JDUser _guest=new JDUser();
	static
	{
		_guest._uid=-1;
		_guest._user=JDMessage.getJDMessage("GuestUser");
		_guest._firstname=JDMessage.getJDMessage("GuestFirstName");
		_guest._lastname=JDMessage.getJDMessage("GuestLastName");
	}

	public String getUsername()
	{
		return _user;
	}

	public String getFirstname()
	{
		return _firstname;
	}

	public String getLastname()
	{
		return _lastname;
	}

	public int getUid()
	{
		return _uid;
	}

	public Map<Integer, String> getRoles()
	{
		return _roles;
	}

	public static JDUser getUser(String username)
	{
		JDUser user=new JDUser();
		
		Statement stmt;
		try 
		{
			Connection conn=DBAccess.getConnection();
			stmt = conn.createStatement();
			String sql= DBAccess.SQL("select * from [users] where username='%s'", username) ;
			ResultSet rs=stmt.executeQuery(sql);

			if(rs.next())
			{
				user._uid=rs.getInt("id");
				user._user=rs.getString("username");
				user._firstname=rs.getString("firstname");
				user._lastname=rs.getString("lastname");
			}
			
			stmt = conn.createStatement();
			
			sql= DBAccess.SQL("select * from [user_role] where uid=%d", user._uid) ;
			System.out.println(sql);
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println("RID: "+rs.getInt("rid"));
// TODO get role name here				
				user._roles.put(rs.getInt("rid"), "role");
			}
		} 
		catch (Exception e) 
		{
			PageHandler.setError(JDMessage.getJDMessage("UnknownError"));
			e.printStackTrace();
		}

		
		return user;
	}

	public static JDUser getGuestUser()
	{
		return _guest;
	}
}
