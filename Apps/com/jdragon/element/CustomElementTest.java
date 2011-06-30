/**
 * 
 */
package com.jdragon.element;

import java.util.*;

import com.jdragon.system.BaseElement;
import com.jdragon.system.TemplateHandler;
import com.jdragon.system.chunk.Chunk;
import com.jdragon.system.form.*;
import com.jdragon.util.JDHashMap;

/**
 * @author raghukr
 *
 */
public class CustomElementTest extends BaseElement 
{
	String _sum="";

	/* (non-Javadoc)
	 * @see com.jdragon.system.Ingredient#content()
	 */
	@Override
	public String mainContent(List<String> args) throws Exception 
	{
		HashMap<String, Object> vars = new HashMap<String, Object>();

		if(this.submitted())
		{
			vars.put("sum", _sum);
		}
		vars.put("welcome1", _t("Welcome to ingredient test"));
		vars.put("welcome2", _t("Hello, How are you?"));
		vars.put("args", args);
		vars.put("form", getForm("myForm"));

		this.setError("Test Error", "Test Error Message");

		return TemplateHandler.processTemplate(vars, "test.ftl");
	}

	public String[] seasoningsList()
	{
		String[] strList={"sample1", "sample2"};
		return strList;
	}

	public Chunk chunk(String name)
	{
		if(name.equals("sample1"))
		{
			Chunk s=new Chunk();
			s.setTitle(_t("Sample 1"));
			s.setContent("Hello, World!");
			return s;
		}
		if(name.equals("sample2"))
		{
			Chunk s=new Chunk();
			s.setTitle(_t("Sample 2"));
			s.setContent("Have a good day!");
			return s;
		}
		return null;
	}

	public String[] urlpatterns()
	{
		return new String[]{"/Index/%/view", "/Indextest"};
	}

	public Form form(String formName)
	{
		Form form=new Form();
		form.addComponent(new TextBox("num1").title("Number 1").value(""));
		form.addComponent(new TextBox("num2").title("Number 2").value(""));
		
		JDHashMap<String, String> selMap=new JDHashMap<String, String>();
		selMap.add("1", "1").add("2", "2");
		
		form.addComponent(new Select("select1").title("Select").value(selMap));
		
		form.addComponent(new Submit("submitbtn").title("Go!"));
		return form;
	}
	
	@Override
	public boolean formValidate(String formName, HashMap<String, String[]> params)
	{
	    String val1 = params.get("num1")[0];
	    String val2 = params.get("num2")[0];
	    boolean ret=true;
		try
		{
			Integer.parseInt(val1);
		} catch (NumberFormatException e)
		{
			setFormError("num1", "Not a Number");
			ret=false;
		}
		try
		{
			Integer.parseInt(val2);
		} catch (NumberFormatException e)
		{
			setFormError("num2", "Not a Number");
			ret=false;
		}
		return ret;
	}

	public boolean formSubmit(String formName, HashMap<String, String[]> params)
	{
		String val1 = params.get("num1")[0];
		String val2 = params.get("num2")[0];
		int ival1=Integer.parseInt(val1);
		int ival2=Integer.parseInt(val2);
		 
		_sum=(new Integer(ival1+ival2)).toString();
		 
		return true;
	}
}
