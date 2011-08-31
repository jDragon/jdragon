/**
 * 
 */
package com.jdragon.system.element;

import java.sql.*;
import java.util.*;

import com.jdragon.system.BaseElement;
import com.jdragon.system.DBAccess;
import com.jdragon.system.PageHandler;
import com.jdragon.system.Render;
import com.jdragon.system.RouteHandler;
import com.jdragon.system.form.Form;
import com.jdragon.system.form.Submit;
import com.jdragon.system.form.TextBox;

/**
 * @author raghukr
 *
 */
public class JDSettings extends BaseElement
{
	private static Hashtable<String, String> _settingsHash=null;
	
	public static String getSetting(String name)
	{
		if(_settingsHash==null)
		{
			_settingsHash=new Hashtable<String, String>();
			try
			{
				initSettings();
			} 
			catch (SQLException e)
			{
				return null;
			}
		}
		return _settingsHash.get(name);
	}
	
	private static void initSettings() throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.resolvePrefix("select * from [settings]");
		ResultSet rs=stmt.executeQuery(sql);
		String ingr="", path="";
		while (rs.next())
		{
			  path = rs.getString("name");
			  ingr = rs.getString("value");
			  _settingsHash.put(path, ingr);
		}
	}
	
	public static void createSetting(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=getSetting(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
			sql="insert into [settings] (name, value) values ('"+name+"', '"+value+"')";
		else
			sql="update [settings] set value='"+value+"' where name='"+name+"'";
		sql=DBAccess.resolvePrefix(sql);
		stmt.execute(sql);
		conn.commit();
		reloadCache();
	}
	
	public static void removeSetting(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=getSetting(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
			sql="insert into [settings] (name, value) values ('"+name+"', '"+value+"')";
		else
			sql="update [settings] set value='"+value+"' where name='"+name+"'";
		sql=DBAccess.resolvePrefix(sql);
		stmt.execute(sql);
		conn.commit();
		reloadCache();
	}
	
	static void reloadCache()
	{
		_settingsHash=new Hashtable<String, String>();
		try
		{
			initSettings();
			RouteHandler.reload();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jdragon.system.Ingredient#content()
	 */
	@Override
	public String mainContent(List<String> args) throws Exception 
	{
		if(args.size()>2 && "reload".equals(args.get(2)))
		{
			reloadCache();
			PageHandler.setError("Cache Reloaded");
		}

//		HashMap<String, Object> vars = new HashMap<String, Object>();
//		vars.put("form", RenderForm(getForm("JDSettingsForm")));
		
		return Render.form(getForm("JDSettingsForm"));
	}


	public String[] urlpatterns()
	{
		return new String[]{"/admin/settings", "/admin/settings/reload"};
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
