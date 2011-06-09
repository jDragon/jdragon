package com.jdragon.system.form;

import java.util.*;

@SuppressWarnings("unchecked")
public class FormComponent
{
	private Map _hashMap=new HashMap();
	public String get(Object key)
	{
		return (String)_hashMap.get(key);
	}

	public Object put(Object key, Object value)
	{
		return _hashMap.put(key, value);
	}

	public FormComponent(String name)
	{
		put("name", name);
	}
	
	public final FormComponent title(String titleStr)
	{
		put("title", titleStr);
		return this;
	}
	
	public final FormComponent type(String typeStr)
	{
		put("type", typeStr);
		return this;
	}
	
	public final FormComponent value(String valueStr)
	{
		put("value", valueStr);
		return this;
	}
	
	public String Render()
	{
		String title=get("title");
		String type=get("type");
		String value=get("value");
		String name=get("name");
		String str="<label>"+title+"</label>\n" +
				"<input type=\"" + type + "\" name=\""+ name + "\" value=\"" + value + "\" / >";
		return str;
	}
}
