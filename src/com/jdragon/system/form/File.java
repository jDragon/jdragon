package com.jdragon.system.form;

import com.jdragon.util.XMLBuilder;

public class File extends FormComponent
{
//	String _value="";
	
	public File(String name){super(name); super.type("file");}
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
		.div()
			.label().text(title()).end()
			.div()
				.input().type(type()).name(name()).value((String) value()).cssclass(compClass)
				.end()
			.enddiv()
		.enddiv();
		
		String str = builder.toString();
		
		return str;
	}
}
