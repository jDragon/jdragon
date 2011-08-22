package com.jdragon.system;

import java.sql.SQLException;
import java.util.*;

import com.jdragon.system.BaseElement;
import com.jdragon.system.PageHandler;
import com.jdragon.system.form.*;

/**
 * @author raghukr
 *
 */
public class SettingsElement extends BaseElement 
{

	/* (non-Javadoc)
	 * @see com.jdragon.system.Ingredient#content()
	 */
	@Override
	public String mainContent(List<String> args) throws Exception 
	{

//		HashMap<String, Object> vars = new HashMap<String, Object>();
//		vars.put("form", RenderForm(getForm("JDSettingsForm")));
		
		return Render.form(getForm("JDSettingsForm"));
	}


	public String[] urlpatterns()
	{
		return new String[]{"/admin/settings"};
	}

	public Form getForm(String formName)
	{
		Form form=new Form(formName);
		form.addComponent(new TextBox("sname").title("Name").value(""));
		form.addComponent(new TextBox("svalue").title("Value").value(""));
		
		form.addComponent(new Submit("submitbtn").title("Add!"));
		return form;
	}
	
	@Override
	public boolean validateForm(String formName, HashMap<String, String[]> params)
	{
	    String val1 = params.get("sname")[0];
	    String val2 = params.get("svalue")[0];
	    boolean ret=true;
		if(val1==null)
		{
		    Form.setError("sname", "Name cannot be blank");
			ret=false;
		}
		if(val2==null)
		{
		    Form.setError("svalue", "Value cannot be blank");
			ret=false;
		}
		return ret;
	}

	public boolean submitForm(String formName, HashMap<String, String[]> params)
	{
		String name = params.get("sname")[0];
		String value = params.get("svalue")[0];
		try
		{
			JDSettings.createSetting(name, value);
			return true;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			PageHandler.setError("Database Error Occured when saving setting");
			return false;
		}
	}
}
