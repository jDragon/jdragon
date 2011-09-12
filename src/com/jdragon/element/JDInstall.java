package com.jdragon.element;

import java.sql.*;
import java.util.List;

import com.jdragon.system.BaseElement;
import com.jdragon.system.DBAccess;

public class JDInstall extends BaseElement
{
	@Override
	public String mainContent(List<String> args) throws Exception
	{
//		String op=args.get(0);
		String elemStr=args.get(1);
		if(install(elemStr))
			return "Installed Element: " + elemStr;
		else
			return "Element Installation failed";
	}
	
	public Boolean install(String element)
	{
		try
		{
			BaseElement ingr=BaseElement.getElementByName(element);
			String[] urlpatterns=ingr.urlpatterns();

			Connection conn=DBAccess.getConnection();
			conn.setSavepoint();
			Statement stmt = conn.createStatement();
			String sql="delete from jd_routes where element='"+element+"'";;
			stmt.execute(sql);
			
			for(String urlpattern : urlpatterns)
			{
				stmt = conn.createStatement();
				sql="insert into [routes] (path, element) values ('"+urlpattern+"', '"+element+"')";
				sql=DBAccess.resolvePrefix(sql);
				
				stmt.execute(sql);
			}
			conn.commit();
			return Boolean.TRUE;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return Boolean.FALSE;
		} 
	}
}
