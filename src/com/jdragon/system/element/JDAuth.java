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
		else if("logout".equals(args.get(0)))
		{
			JDSession.destroy();
			JDSession.requestRedirect("/jdragon/login");
			return "";//"Successfully Logged out";
		}
		else if("register".equals(args.get(0)))
		{
			if(this.submitted() && _isValid)
			{
				return "Registration Successful";
			}
			return Render.form(getForm("registrationForm"));
		}
		
		return "Something wrong here";

	}

	public Form loginForm()
	{
		Form frm=new Form("loginForm");
		frm.addComponent(new TextBox("username").title("User Name"));
		frm.addComponent(new Password("passwd").title("Password"));
		frm.addComponent(new Submit("Submit").title("Go!"));
		return frm;
	}

	public boolean loginForm_submit(HashMap<String, String[]> params)
	{
		JDSession.setCredentials(params.get("username")[0], "1", "authenticated");
		return false;
	}

	public boolean loginForm_validate(HashMap<String, String[]> params)
	{
		String usr=((String[])params.get("username"))[0];
		String passwd=((String[])params.get("passwd"))[0];

		Statement stmt;
		try 
		{
			Connection conn=DBAccess.getConnection();
			stmt = conn.createStatement();
			String sql="select * from [users] where name='" + usr + "'" ;
			sql=DBAccess.resolvePrefix(sql);
			ResultSet rs=stmt.executeQuery(sql);

			if(rs.next() && rs.getString("passwd").equals(MD5(passwd)) && !rs.next())
				_isValid = true;
			else
			{
				PageHandler.setError("Invalid Credentials");
				_isValid = false;
			}
		} 
		catch (Exception e) 
		{
			PageHandler.setError("Error Occured");
			e.printStackTrace();
		}

		return _isValid;
	}
	
	public Form registrationForm()
	{
		Form frm=new Form("registrationForm");
		frm.addComponent(new TextBox("username").title("User Name"));
		frm.addComponent(new Password("passwd").title("Password"));
		frm.addComponent(new TextBox("email").title("E-Mail"));
		frm.addComponent(new Submit("Submit").title("Register"));
		return frm;
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
			String sql="insert into [users] (name, passwd, email) values ('" + usr + "', '" + passwd + "', '" + email +"')" ;
			sql=DBAccess.resolvePrefix(sql);
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
			String sql="select * from [users] where name='" + usr + "'" ;
			sql=DBAccess.resolvePrefix(sql);
			ResultSet rs=stmt.executeQuery(sql);

			if(rs.next())
			{
				Form.setError("username", "User already exists. Choose a different name");
				_isValid=false;
			}
			
			if(!isValidEmailAddress(email))
			{
				Form.setError("email", "Invalid E-Mail address");
				_isValid=false;
			}
		} 
		catch (Exception e) 
		{
			PageHandler.setError("Error Occured");
			e.printStackTrace();
			_isValid=false;
		}
		
		return _isValid;
	}

	@Override
	public String[] urlpatterns()
	{
		return new String[]{"/login", "/logout", "/register"};
	}
	
	public Boolean isLoggedIn(Object[] o)
	{
		if(JDSession.getUser()!=null)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public String MD5(String str) {
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
