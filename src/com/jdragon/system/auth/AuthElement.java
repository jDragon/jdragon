/**
 * 
 */
package com.jdragon.system.auth;

import java.util.HashMap;
import java.util.List;

import com.jdragon.system.BaseElement;
import com.jdragon.system.form.*;

/**
 * @author raghukr
 *
 */
public class AuthElement extends BaseElement
{
	boolean _isValid=false;
	/* (non-Javadoc)
	 * @see com.jdragon.system.BaseElement#mainCourse(java.util.List)
	 */
	@Override
	public String mainContent(List<String> args) throws Exception
	{
		if(this.submitted() && _isValid)
		{
			return "Login Successful";
		}

		return getForm("loginForm");
	}

	@Override
	public Form form(String formName)
	{
		if(formName.equals("loginForm"))
		{
			Form frm=new Form();
			frm.addComponent(new TextBox("username").title("User Name"));
			frm.addComponent(new Password("passwd").title("Password"));
			frm.addComponent(new Submit("Submit").title("Go!"));
			return frm;
		}
		return null;
	}

	@Override
	public boolean formSubmit(String formName, HashMap<String, String[]> params)
	{
		return false;
	}

	@Override
	public boolean formValidate(String formName, HashMap<String, String[]> params)
	{
		if(((String[])params.get("FORMNAME"))[0].equals("loginForm"))
		{
			if(((String[])params.get("username"))[0].equals("test") && ((String[])params.get("passwd"))[0].equals("Password"))
				_isValid=true;
			else
				this.setFormError("passwd", "Invalid credentials!");
			return _isValid;
		}
		return false;
	}

	@Override
	protected String[] urlpatterns()
	{
		return new String[]{"/login"};
	}

}
