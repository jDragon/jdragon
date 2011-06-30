package com.jdragon.system.form;

import java.util.*;

import com.jdragon.util.XMLBuilder;

@SuppressWarnings("unchecked")
public class Select extends FormComponent
{
	Map<String, String> _value=null;
	
	public Select(String name){super(name); super.type("select");}
	@Override
	public String Render(Map<String, String> errorMap)
	{
		XMLBuilder builder = new XMLBuilder();
		builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("select");

					for(Map.Entry<String, String> entry : _value.entrySet())
					{
						builder.tag("option").attr("value", entry.getKey()).text(entry.getValue()).end();
					}

				builder
				.end()
			.end()
		.end();
		
		return builder.toString();
	}
	@Override
	public FormComponent value(Object valueMap) throws ClassCastException
	{
		_value=(HashMap<String, String>)valueMap;
		return this;
	}
	@Override
	public Object value()
	{
		return _value;
	}
}
