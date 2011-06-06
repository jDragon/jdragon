/**
 * 
 */
package com.jdragon.system;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author raghukr
 *
 */
public abstract class BaseIngredient 
{
	private Connection connection=null;
	private HttpServletRequest _request=null;
	
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
	
	@SuppressWarnings("unchecked")
	protected void setError(String title, String message)
	{
		HashMap errMap=(HashMap)_request.getAttribute("jDr_ErrorMap");
		if(errMap==null)
		{
			errMap=new HashMap();
			_request.setAttribute("jDr_ErrorMap", errMap);
		}
		errMap.put(title, message);
	}

	public void setRequest(HttpServletRequest _request)
	{
		this._request = _request;
	}

	public HttpServletRequest getRequest()
	{
		return _request;
	}
}
