package com.jdragon.system.form;

import java.util.HashMap;

import com.jdragon.util.XMLBuilder;

public class TextBox extends FormComponent
{
	String _value="";
	
	public TextBox(String name){super(name); super.type("textbox");}
	@SuppressWarnings("unchecked")
	@Override
	public String Render(HashMap errorMap)
	{
		String compClass="input-"+type();
		if(errorMap!=null)
		{
			String val=(String)errorMap.get(name());
			if(val!=null && !"".equals(val))
				compClass=compClass+" error-"+type();
		}
		XMLBuilder builder = new XMLBuilder();
		String str = builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("input").attr("type", type()).attr("name", name()).attr("value", _value).attr("class", compClass)
				.end()
			.end()
		.end().toString();
		
		return str;
	}
	@Override
	public FormComponent value(Object valueStr)
	{
		_value=(String)valueStr;
		return this;
	}
	@Override
	public Object value()
	{
		return _value;
	}
}
