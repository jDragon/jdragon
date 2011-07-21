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
		String ingrStr=args.get(1);
		BaseElement ingr=BaseElement.getElementByName(ingrStr);
		String[] urlpatterns=ingr.urlpatterns();

		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql="delete from jd_routes where ingredient='"+ingrStr+"'";;
		stmt.execute(sql);

		
		for(String urlpattern : urlpatterns)
		{
			stmt = conn.createStatement();
			sql="insert into jd_routes (path, ingredient) values ('"+urlpattern+"', '"+ingrStr+"')";;
			stmt.execute(sql);
			System.out.println("insert into jd_routes (path, ingredient) values ('"+urlpattern+"', '"+ingrStr+"')");
		}
		
		return "Installer Ingredient works!"+api("TestMethod", new Object[]{"From Caller!", "Second Arg"});
	}

	public String TestMethod(Object[] a)
	{
		return "Hello!" + a[0] + "" + a[1];
	}
}
