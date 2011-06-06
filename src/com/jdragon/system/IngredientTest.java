/**
 * 
 */
package com.jdragon.system;

import java.util.*;

/**
 * @author raghukr
 *
 */
public class IngredientTest extends BaseIngredient 
{
	/* (non-Javadoc)
	 * @see com.jdragon.system.Ingredient#content()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String mainCourse(List<String> args) throws Exception 
	{
		HashMap vars = new HashMap();

		vars.put("welcome1", _t("Welcome to ingredient test"));
		vars.put("welcome2", _t("Hello, how are you?"));
		vars.put("args", args);
		this.setError("Test Error", "Test Error Message");
		
		return TemplateHandler.processTemplate(vars, "test.ftl");
	}
	
	public String[] seasoningsList()
	{
		String[] strList={"sample1", "sample2"};
		return strList;
	}

	public Seasoning seasoning(String name)
	{
		if(name.equals("sample1"))
		{
			Seasoning s=new Seasoning();
			s.setTitle(_t("Sample 1"));
			s.setContent("Hello, World!");
			return s;
		}
		if(name.equals("sample2"))
		{
			Seasoning s=new Seasoning();
			s.setTitle(_t("Sample 2"));
			s.setContent("Have a good day!");
			return s;
		}
		return null;
	}
}
