package com.jdragon.system.form;

import java.util.*;

import com.jdragon.system.PageHandler;

public class Form
{
	private static ThreadLocal<Map<String, String>> formErrMap= new ThreadLocal<Map<String, String>>();
	private static ThreadLocal<Map<String, String[]>> formValues= new ThreadLocal<Map<String, String[]>>();

	private String _name="";
	private List<FormComponent> _elements=new ArrayList<FormComponent>();

	public String getName()
	{
		return _name;
	}

	public void setName(String _name)
	{
		this._name = _name;
	}
	
	public void addComponent(FormComponent comp)
	{
		_elements.add(comp);
	}
	
	public String Render()
	{
		String str="";
		for(int i=0; i<_elements.size(); i++)
		{
			FormComponent comp=(FormComponent)_elements.get(i);
			str=str+comp.Render();
		}
		return str;
	}
	
	public static void setError(String title, String message)
	{
		formErrMap.get().put(title, message);
		PageHandler.setError(title+": "+message);
	}
	
	public static Map<String, String> getErrorMap()
	{
		return formErrMap.get();
	}
	
	public static void setFormValues(Map<String, String[]> formValMap)
	{
		formValues.set(formValMap);
	}
	
	public static Map<String, String[]> getFormValues()
	{
		return formValues.get();
	}

	public static void init()
	{
		formErrMap.set(new HashMap<String, String>());
		formValues.set(new HashMap<String, String[]>());
	}
}
