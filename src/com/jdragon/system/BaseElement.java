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

import com.jdragon.system.chunk.Chunk;
import com.jdragon.system.form.Form;

/**
 * @author raghukr
 *
 */
public abstract class BaseElement 
{
	private boolean _isPost=false;
	public final boolean submitted()
	{
		return _isPost;
	}

	public final void setSubmit(boolean post)
	{
		_isPost = post;
	}

	public abstract String mainContent(List<String> args) throws Exception;
	
	public String[] seasoningsList()
	{
		String[] strList=new String[0];
		return strList;
	}
	
	public Chunk chunk(String name)
	{
		return null;
	}

	public String[] urlpatterns()
	{
		return new String[]{};
	}
	
	public String[] accessCodes()
	{
		return new String[]{};
	}

/** Ingredient definition Methods ends*/
	
	protected final String _t(String originalStr)
	{
		return originalStr;
	}
	
/** Form (Post request) processing methods */
	public Form getForm(String formName)
	{
		return null;
	}

	public boolean validateForm(String formName, HashMap<String, String[]> params)
	{
		return true;
	}

	public boolean submitForm(String formName, HashMap<String, String[]> params)
	{
		return false;
	}

//	protected final String getForm(String formName)
//	{
//		Form form= this.form(formName);
//		XMLBuilder builder=new XMLBuilder();
//		if(form==null)
//		{
//			return builder.tag("FORM").name(formName).text("The form "+formName+" is Empty ").end().toString();
//		}
//		
//		String formStr=builder
//		.tag("FORM").attr("name", formName).attr("method", "POST")
//			.input().type("HIDDEN").name("FORMNAME").value(formName)
//			.end()
//			.text(form.Render())
//		.end()
//		.toString();
//
//		return formStr;
//	}
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
	public final Object api(String methodName, Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Class<? extends BaseElement> cls=this.getClass();
		Method m=cls.getMethod(methodName, new Class[]{Object[].class});
		return m.invoke(this, new Object[]{args});
		
	}
	
	public static final BaseElement getElementByName(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		Class<?> c=null;
		if(name.indexOf("com.jdragon.element")==0)
		{
			JDClassLoader loader = new JDClassLoader(JDClassLoader.class.getClassLoader());
			loader.addURL(new File("E:/jdragon/WebContent/App/Default/Elements/").toURI().toURL());
			c=loader.loadClass(name);
		}
		else
		{
			c = Class.forName(name);
		}
		
		BaseElement inst=(BaseElement)c.newInstance();

		return inst;
	}
}
