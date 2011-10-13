/**
 * 
 */
package com.jdragon.system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author raghukr
 *
 */
public class AccessHandler 
{
	public static boolean access(String realm, Integer rid)
	{
		try
		{
			if(_accessHash==null)
			{
				_accessHash=new HashMap<String, List<Integer>>();
				initAccess();
			}
			
			List<Integer> ridList=_accessHash.get(realm);
			if(ridList!=null && ridList.contains(rid))
				return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean access(String realm)
	{
		JDUser usr=JDSession.getUser();
		if(usr.getUid()==1) //admin
			return true;
		
		Map<Integer, String> roles=usr.getRoles();
		for(int rid : roles.keySet())
		{
			System.out.println("RID: "+rid);
			if(access(realm, rid)) 
				return true;
		}

		return false;
	}

	private static void initAccess() throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.SQL("select * from [role_access]");
		ResultSet rs=stmt.executeQuery(sql);
		String access_token="";
		int rid=-1;
		while (rs.next())
		{
			  rid = rs.getInt("rid");
			  access_token = rs.getString("access_token");
			  List<Integer> ridList=_accessHash.get(access_token);
			  if(ridList==null)
			  {
				  ridList=new ArrayList<Integer>();
				  _accessHash.put(access_token, ridList);
			  }
			  ridList.add(rid);
			  _accessHash.put(access_token, ridList);
		}
	}

	private static Map<String, List<Integer>> _accessHash = null;
}
