package com.jdragon.system.form;

import java.util.*;

@SuppressWarnings({"unused"})
public abstract class FormComponent
{
	
	private String _type, _name, _title, _value;
	
/*
	private Map _hashMap=new HashMap();
	public String get(Object key)
	{
		return (String)_hashMap.get(key);
	}

	public Object put(Object key, Object value)
	{
		return _hashMap.put(key, value);
	}
*/
	public FormComponent(String name)
	{
		_name=name;
	}

	protected void type(String type) { _type=type; }
	protected String type() { return _type; }
	protected String name() { return _name; }
	protected String title() { return _title; }
	
	public final FormComponent title(String titleStr)
	{
		_title=titleStr;
		return this;
	}
	
	public abstract FormComponent value(Object valueStr);
	public abstract Object value();
	
	public abstract String Render(HashMap<String, String> errorMap);
	
}
