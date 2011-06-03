/**
 * 
 */
package com.jdragon.system;

import java.util.*;

/**
 * @author RIS
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
		Map vars = new HashMap();

		vars.put("welcome1", _t("Welcome to ingredient test"));
		vars.put("welcome2", _t("Hello, how are you?"));
		
		return TemplateHandler.processTemplate(vars, "test.ftl");
	}
}
