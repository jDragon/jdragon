/**
 * 
 */
package com.jdragon.system;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jdragon.system.form.Form;
import com.jdragon.system.seasonings.Seasoning;

/**
 * @author raghukr
 *
 */
public abstract class BaseIngredient 
{
	protected DBAccess _db=null;
	private boolean _isPost=false;
	
	public final boolean submitted()
	{
		return _isPost;
	}

	public final void setSubmit(boolean post)
	{
		_isPost = post;
	}

	public final void setDB(DBAccess _db)
	{
		this._db = _db;
	}

	private HttpServletRequest _request=null;
	
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
	
	protected final String _t(String originalStr)
	{
		return originalStr;
	}
	
	@SuppressWarnings("unchecked")
	protected final void setError(String title, String message)
	{
		HashMap errMap=(HashMap)_request.getAttribute("_jDr_ErrorMap");
		if(errMap==null)
		{
			errMap=new HashMap();
			_request.setAttribute("_jDr_ErrorMap", errMap);
		}
		errMap.put(title, message);
	}

	public final void setRequest(HttpServletRequest _request)
	{
		this._request = _request;
	}
	
	public Form form(String formName)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean formValidate(String formName, HashMap params)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean formSubmit(String formName, HashMap params)
	{
		return false;
	}
	
	protected final String getForm(String formName)
	{
		Form form= this.form(formName);
		if(form==null)
			return "<FORM name= \""+formName+"\" > " +
					"The form "+formName+" is Empty " +
							"</FORM>";
		
		String formStr="<FORM name=\""+formName+"\" method=\"POST\" >" +
		"<INPUT type=\"HIDDEN\" name=\"FORMNAME\" value=\""+formName+"\"/>" +
		form.Render() +
		"</FORM>";
		return formStr;
	}
}
