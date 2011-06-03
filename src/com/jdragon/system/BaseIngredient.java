/**
 * 
 */
package com.jdragon.system;

import java.sql.Connection;
import java.util.List;

/**
 * @author RIS
 *
 */
public abstract class BaseIngredient {

	private Connection connection=null;
	
	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see com.jdragon.system.Ingredient#content()
	 */
	public abstract String mainCourse(List<String> args) throws Exception;
	
	public String[] seasoningsList()
	{
		String[] strList=new String[0];
		return strList;
	}
	
	public Seasoning seasoning(String name)
	{
		return null;
	}
	
	protected String _t(String originalStr)
	{
		return originalStr;
	}
}
