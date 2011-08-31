package com.jdragon.system;

import com.jdragon.system.form.Form;
import com.jdragon.util.XMLBuilder;

public class Render
{
	public static String form(Form form)
	{
		XMLBuilder builder=new XMLBuilder();

		if(form==null)
		{
			return builder.tag("FORM").name("Error").text("The form is Empty").end().toString();
		}

		String formName=form.name();
		
		String formStr=builder
		.tag("FORM").name(formName).attr("method", "POST")
			.input().type("HIDDEN").name("FORMNAME").value(formName)
			.end()
			.text(form.Render())
		.end()
		.toString();

		return formStr;		
	}
	
	public static String link(String display, String url)
	{
		return "<a href=" + Q(url) + ">" + display + "</a>";
	}
	
	public static String Q(String str)
	{
		return "\""+str+"\"";
	}

}
