package com.jdragon.system.form;

import com.jdragon.util.XMLBuilder;

public class TextBox extends FormComponent
{
//	String _value="";
	
	public TextBox(String name){super(name); super.type("textbox");}
	@Override
	public String Render()
	{
		String compClass="input-"+type();

		String err=getError();
		if(err!=null && !"".equals(err))
			compClass=compClass+" error-"+type();
		
		if(value()==null)
			value("");

		XMLBuilder builder = new XMLBuilder();
		builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("input").attr("type", type()).attr("name", name()).attr("value", (String) value()).attr("class", compClass)
				.end()
			.end()
		.end();
		
		String str = builder.toString();
		
		return str;
	}
}
