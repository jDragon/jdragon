package com.jdragon.system.form;

import java.util.HashMap;

public class Submit extends FormComponent
{
	
	public Submit(String name){super(name); super.type("submit");}
	@SuppressWarnings("unchecked")
	@Override
	public String Render(HashMap errorMap)
	{
		String str="<div>" +
			"<input type=\"" + type() + "\" name=\""+ name() + "\" value=\"" + title() + "\" / >" +
		"</div>" ;
		return str;
	}
	@Override
	public FormComponent value(Object valueStr)
	{
		return this;
	}
	@Override
	public Object value()
	{
		return title();
	}
}