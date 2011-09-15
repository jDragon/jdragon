/**
 * 
 */
package com.jdragon.system.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jdragon.system.*;
import com.jdragon.system.form.*;

/**
 * @author raghukr
 *
 */
public class JDAuth extends BaseElement
{
	boolean _isValid=false;

	public boolean authenticate(String username, String passwd)
	{
		_isValid=false;
		Statement stmt;
		try 
		{
			Connection conn=DBAccess.getConnection();
			stmt = conn.createStatement();
			String sql= DBAccess.SQL("select * from [users] where username='%s'", username) ;
			ResultSet rs=stmt.executeQuery(sql);

			if(rs.next() && rs.getString("passwd").equals(MD5(passwd)) && !rs.next())
				_isValid = true;
			else
			{
				PageHandler.setError(JDMessage.getJDMessage("InvalidCredential"));
			}
		} 
		catch (Exception e) 
		{
			PageHandler.setError(JDMessage.getJDMessage("UnknownError"));
			e.printStackTrace();
		}
		return _isValid;
	}	
	
	public String login() throws Exception
	{
		boolean isLoggedIn=isLoggedIn(null);
		
		if(this.submitted() && _isValid)
		{
			return "Login Successful";
		}

		if(isLoggedIn)
			return "Already Logged in";
		
		Form loginForm=getForm("loginForm");

		return Render.form(loginForm);		
	}

	public void loginForm(Form frm)
	{
		frm.addComponent(new TextBox("username").title("User Name"));
		frm.addComponent(new Password("passwd").title("Password"));
		frm.addComponent(new Submit("Submit").title("Go!"));
	}

	public boolean loginForm_submit(HashMap<String, String[]> params)
	{
		String usr=((String[])params.get("username"))[0];
		JDSession.setUser(JDUser.getUser(usr));
		return false;
	}

	public boolean loginForm_validate(HashMap<String, String[]> params)
	{
		String usr=((String[])params.get("username"))[0];
		String passwd=((String[])params.get("passwd"))[0];

		return authenticate(usr, passwd);
	}

	public String logout() throws Exception
	{
		JDSession.destroy();
		JDSession.requestRedirect("/jdragon/login");
		return "";//"Successfully Logged out";	
	}
	
	public String register() throws Exception
	{
		if(this.submitted() && _isValid)
		{
			PageHandler.setMessage(JDMessage.getJDMessage("RegistrationSuccess"));
			return "";
		}
		return Render.form(getForm("registrationForm"));		
	}
	
	public void registrationForm(Form frm)
	{
		frm.addComponent(new TextBox("username").title("User Name"));
		frm.addComponent(new Password("passwd").title("Password"));
		frm.addComponent(new TextBox("email").title("E-Mail"));
		frm.addComponent(new Submit("Submit").title("Register"));
	}

	public boolean registrationForm_submit(HashMap<String, String[]> params)
	{
		String usr=((String[])params.get("username"))[0];
		String passwd=((String[])params.get("passwd"))[0];
		String email=((String[])params.get("email"))[0];
		
		passwd=MD5(passwd);
		
		Connection conn=DBAccess.getConnection();
		try 
		{
			conn.setSavepoint();
			Statement stmt = conn.createStatement();
			String sql=DBAccess.SQL("insert into [users] (username, passwd, email) values ('%s', '%s', '%s')", 
								usr, passwd, email);
			stmt.execute(sql);

			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean registrationForm_validate(HashMap<String, String[]> params)
	{
		// TODO validate usr/email/etc

		_isValid=true;

		String usr=((String[])params.get("username"))[0];
//		String passwd=((String[])params.get("passwd"))[0];
		String email=((String[])params.get("email"))[0];

		Statement stmt;
		try 
		{
			Connection conn=DBAccess.getConnection();
			stmt = conn.createStatement();
			String sql=DBAccess.SQL("select * from [users] where username='%s'", usr);
			ResultSet rs=stmt.executeQuery(sql);

			if(rs.next())
			{
				Form.setError("username", JDMessage.getJDMessage("UserExists"));
				_isValid=false;
			}
			
			if(!isValidEmailAddress(email))
			{
				Form.setError("email", JDMessage.getJDMessage("InvalidEmail"));
				_isValid=false;
			}
		} 
		catch (Exception e) 
		{
			PageHandler.setError(JDMessage.getJDMessage("UnknownError"));
			e.printStackTrace();
			_isValid=false;
		}
		
		return _isValid;
	}

	@Override
	public void urlpatterns(Map<String, String> urlCallbackMap)
	{
		urlCallbackMap.put("/login", "login");
		urlCallbackMap.put("/logout", "logout");
		urlCallbackMap.put("/register", "register");
	}
	
	public boolean isLoggedIn(Object[] o)
	{
		return JDSession.getUser()!=JDUser.getGuestUser(); 
	}
	
	public String MD5(String str) 
	{
		try 
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) 
			{
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} 
		catch (java.security.NoSuchAlgorithmException e) 
		{
		}
		return null;
	}
	
	public boolean isValidEmailAddress(String emailAddress)
	{  
		String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		CharSequence inputStr = emailAddress;  
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(inputStr);  
		return matcher.matches();  
	} 
}
