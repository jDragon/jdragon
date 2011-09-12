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
			String sql=DBAccess.SQL("delete from [routes] where element='%s'", element);
			stmt.execute(sql);
			
			for(String urlpattern : urlpatterns)
			{
				stmt = conn.createStatement();
				sql=DBAccess.SQL("insert into [routes] (path, element) values ('%s', '%s')", urlpattern, element);
				
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
