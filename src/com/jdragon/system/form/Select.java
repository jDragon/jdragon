package com.jdragon.system.form;

import java.util.*;

@SuppressWarnings("unchecked")
public class Select extends FormComponent
{
	Map<String, String> _value=null;
	
	public Select(String name){super(name); super.type("select");}
	@Override
	public String Render()
	{
		String str="<div>" +
		"<label>"+title()+"</label>\n" +
		"<div>" +
		"<select>";
		for(Map.Entry<String, String> entry : _value.entrySet())
		{
			str=str+"<option value=\""+entry.getKey()+"\">"+ entry.getValue()+"</option>";
		}
		str=str+
		"</select>" +
		"</div></div>" ;
		return str;
	}
	@Override
	public FormComponent value(Object valueStr) throws ClassCastException
	{
		_value=(HashMap)valueStr;
		return this;
	}
	@Override
	public Object value()
	{
		return _value;
	}
}
