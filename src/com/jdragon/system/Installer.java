package com.jdragon.system;

import java.sql.*;
import java.util.List;

import com.jdragon.system.BaseElement;

public class Installer extends BaseElement
{
	@Override
	public String mainContent(List<String> args) throws Exception
	{
//		String op=args.get(0);
		String elemStr=args.get(1);
		BaseElement ingr=BaseElement.getElementByName(elemStr);
		String[] urlpatterns=ingr.urlpatterns();

		Connection conn=DBAccess.getConnection();
		conn.setSavepoint();
		Statement stmt = conn.createStatement();
		String sql="delete from jd_routes where ingredient='"+elemStr+"'";;
		stmt.execute(sql);

		
		for(String urlpattern : urlpatterns)
		{
			stmt = conn.createStatement();
			sql="insert into [routes] (path, ingredient) values ('"+urlpattern+"', '"+elemStr+"')";
			sql=DBAccess.resolvePrefix(sql);
			
			stmt.execute(sql);
		}
		conn.commit();
		
		return "Installed Element: " + elemStr;
	}

	public String TestMethod(Object[] a)
	{
		return "Hello!" + a[0] + "" + a[1];
	}
}
