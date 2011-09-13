/**
 * 
 */
package com.jdragon.system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;
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

	public String[] chunksList()
	{
		String[] strList=new String[0];
		return strList;
	}
	
	public Chunk chunk(String name)
	{
		Class<? extends BaseElement> cls=this.getClass();
		Method m;
		try
		{
			m = cls.getMethod("chunk_"+name, (Class<?>[])null);
			return (Chunk) m.invoke(this, (Object[])null);
		} catch (SecurityException e1)
		{
			e1.printStackTrace();
		} catch (NoSuchMethodException e1)
		{
			PageHandler.setError("Chunk "+name+" not defined in "+this.getClass().getName());
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void urlpatterns(Map<String, String> urlCallbackMap)
	{
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
		try 
		{
			String methodName= formName;
			Class<? extends BaseElement> cls=this.getClass();
			Method m=cls.getMethod(methodName, (Class<?>[])null);
			return (Form)m.invoke(this, (Object[])null);
		}
		catch (Exception e) 
		{
			return null;
		}
		
	}

	public boolean validateForm(String formName, HashMap<String, String[]> params)
	{
		String methodName= formName + "_validate";
		Class<? extends BaseElement> cls=this.getClass();
		Method m;
		try 
		{
			m = cls.getMethod(methodName, new Class[]{ HashMap.class});
			return (Boolean) m.invoke(this, new Object[]{params});		
		} 
		catch (Exception e) 
		{
			return true;
		}
	}

	public boolean submitForm(String formName, HashMap<String, String[]> params)
	{
		String methodName= formName + "_submit";
		Class<? extends BaseElement> cls=this.getClass();
		Method m;
		try 
		{
			m = cls.getMethod(methodName, new Class[]{ HashMap.class});
			return (Boolean) m.invoke(this, new Object[]{params});		
		} 
		catch (Exception e) 
		{
			return false;
		}
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
	public final Object api(String methodName, Object[] args) throws JDException
	{
		try
		{
			Class<? extends BaseElement> cls=this.getClass();
			Method m=cls.getMethod(methodName, new Class[]{Object[].class});
			return m.invoke(this, new Object[]{args});
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new JDException(e);
		}
	}
	
	public static final BaseElement getElementByName(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		Class<?> c = Class.forName(name);
	
		BaseElement inst=(BaseElement)c.newInstance();

		return inst;
	}
}
