package com.jdragon.system.form;

public class TextBox extends FormComponent
{
	String _value="";
	
	public TextBox(String name){super(name); super.type("textbox");}
	@Override
	public String Render()
	{
		String str="<div>" +
		"<label>"+title()+"</label>\n" +
		"<div>" +
			"<input type=\"" + type() + "\" name=\""+ name() + "\" value=\"" + _value + "\" / >" +
		"</div></div>" ;
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
