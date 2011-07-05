package com.jdragon.system.form;

import java.util.Map;

import com.jdragon.util.XMLBuilder;

public class TextBox extends FormComponent
{
	String _value="";
	
	public TextBox(String name){super(name); super.type("textbox");}
	@Override
	public String Render()
	{
		Map<String, String> errorMap=Form.getErrorMap();
		String compClass="input-"+type();
		if(errorMap!=null)
		{
			String val=errorMap.get(name());
			if(val!=null && !"".equals(val))
				compClass=compClass+" error-"+type();
		}

		Map<String, String[]> valueMap=Form.getFormValues();
		if(valueMap!=null)
		{
			String[] valArr= valueMap.get(name());
			if(valArr!=null && valArr.length>0)
				_value=valArr[0];
		}

		XMLBuilder builder = new XMLBuilder();
		builder
		.tag("div")
			.tag("label").text(title()).end()
			.tag("div")
				.tag("input").attr("type", type()).attr("name", name()).attr("value", _value).attr("class", compClass)
				.end()
			.end()
		.end();
		
		
		String str = builder.toString();
		
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
