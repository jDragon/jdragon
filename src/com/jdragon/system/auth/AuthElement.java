/**
 * 
 */
package com.jdragon.system.auth;

import java.util.HashMap;
import java.util.List;

import com.jdragon.system.BaseElement;
import com.jdragon.system.JDSession;
import com.jdragon.system.Render;
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
		if(args.size()<=0)
			return "Invalid Arguments";
		
		if("login".equals(args.get(0)))
		{
			Boolean isLoggedIn=(Boolean)api("isLoggedIn", null);
			
			if(this.submitted() && _isValid)
			{
				return "Login Successful";
			}

			if(isLoggedIn.equals(Boolean.TRUE))
				return "Already Logged in";
			
			Form loginForm=getForm("loginForm");

			return Render.form(loginForm);
		}
		else if("logout".equals(args.get(0)))
		{
			JDSession.destroy();
			JDSession.requestRedirect("/jdragon/login");
			return "";//"Successfully Logged out";
		}
		
		return "Something wrong here";

	}

	@Override
	public Form getForm(String formName)
	{
		if(formName.equals("loginForm"))
		{
			Form frm=new Form(formName);
			frm.addComponent(new TextBox("username").title("User Name"));
			frm.addComponent(new Password("passwd").title("Password"));
			frm.addComponent(new Submit("Submit").title("Go!"));
			return frm;
		}
		return null;
	}

	@Override
	public boolean submitForm(String formName, HashMap<String, String[]> params)
	{
		JDSession.setCredentials(params.get("username")[0], "1", "authenticated");
		return false;
	}

	@Override
	public boolean validateForm(String formName, HashMap<String, String[]> params)
	{
		if(((String[])params.get("FORMNAME"))[0].equals("loginForm"))
		{
			if(((String[])params.get("username"))[0].equals("test") && ((String[])params.get("passwd"))[0].equals("Password"))
				_isValid=true;
			else
				Form.setError("passwd", "Invalid credentials!");
			return _isValid;
		}
		return false;
	}

	@Override
	public String[] urlpatterns()
	{
		return new String[]{"/login", "/logout"};
	}
	
	public Boolean isLoggedIn(Object[] o)
	{
		if(JDSession.getUser()!=null)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
}
