/**
 * 
 */
package com.jdragon.system;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jdragon.system.form.Form;
import com.jdragon.system.seasonings.Seasoning;
import com.jdragon.util.XMLBuilder;

/**
 * @author raghukr
 *
 */
public abstract class BaseIngredient 
{
	protected DBAccess _db=null;
	private boolean _isPost=false;
	private HttpServletRequest _request=null;
	
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

/** Ingredient definition Methods */
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

	String[] getUrlPatterns()
	{
		return urlpatterns();
	}

	protected String[] urlpatterns()
	{
		return new String[]{};
	}

/** Ingredient definition Methods ends*/
	
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

	@SuppressWarnings("unchecked")
	protected final void setFormError(String title, String message)
	{
		HashMap errMap=(HashMap)_request.getAttribute("_jDr_FormErrorMap");
		if(errMap==null)
		{
			errMap=new HashMap();
			_request.setAttribute("_jDr_FormErrorMap", errMap);
		}
		errMap.put(title, message);
		setError(title, message);
	}

	public final void setRequest(HttpServletRequest _request)
	{
		this._request = _request;
	}
	
/** Form (Post request) processing methods */
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

	@SuppressWarnings("unchecked")
	protected final String getForm(String formName)
	{
		Form form= this.form(formName);
		XMLBuilder builder=new XMLBuilder();
		if(form==null)
		{
			return builder.tag("FORM").attr("name", formName).text("The form "+formName+" is Empty ").end().toString();
		}
		
		HashMap errMap=(HashMap)_request.getAttribute("_jDr_FormErrorMap");
		
		String formStr=builder
		.tag("FORM").attr("name", formName).attr("method", "POST")
			.tag("INPUT").attr("type", "HIDDEN").attr("name", "FORMNAME").attr("value", formName)
			.end()
			.text(form.Render(errMap))
		.end()
		.toString();

		return formStr;
	}
/** Form (Post request) processing methods ends*/
	
	/**
	 * Allows calling methods in other ingredients by using BaseIngredient handler. 
	 * The methods that can be called should take Object[] as input and return Object as output
	 *  
	 * @param methodName
	 * @param args
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public final Object api(String methodName, Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Class cls=this.getClass();
		Method m=cls.getMethod(methodName, new Class[]{Object[].class});
		return m.invoke(this, new Object[]{args});
		
	}
	
	public static final BaseIngredient getIngredientByName(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		Class<?> c=null;
		if(name.indexOf("com.jdragon.ingredient")==0)
		{
			JDClassLoader loader = new JDClassLoader(JDClassLoader.class.getClassLoader());
			loader.addURL(new File("E:/jdragon/WebContent/App/Default/Ingredients/").toURI().toURL());
			c=loader.loadClass(name);
		}
		else
		{
			c = Class.forName(name);
		}
		
		BaseIngredient inst=(BaseIngredient)c.newInstance();

		return inst;
	}
}
