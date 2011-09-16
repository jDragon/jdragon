/**
 * 
 */
package com.jdragon.system.element;

import java.sql.*;
import java.util.*;

import com.jdragon.system.BaseElement;
import com.jdragon.system.DBAccess;
import com.jdragon.system.JDRequest;
import com.jdragon.system.PageHandler;
import com.jdragon.system.Render;
import com.jdragon.system.RouteHandler;
import com.jdragon.system.form.Form;
import com.jdragon.system.form.Submit;
import com.jdragon.system.form.TextBox;
import com.jdragon.util.XMLBuilder;

/**
 * @author raghukr
 *
 */
public class JDSettings extends BaseElement
{
	private static Hashtable<String, String> _settingsHash=null;
	
	public static String get(String name, String defaultValue)
	{
		String val=get(name);
		if(val==null)
			val=defaultValue;
		return val;
	}

	public static String get(String name)
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
		String sql=DBAccess.SQL("select * from [settings]");
		ResultSet rs=stmt.executeQuery(sql);
		String elem="", path="";
		while (rs.next())
		{
			  path = rs.getString("name");
			  elem = rs.getString("value");
			  _settingsHash.put(path, elem);
		}
	}
	
	public static void set(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=get(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
			sql=DBAccess.SQL("insert into [settings] (name, value) values ('%s', '%s')", name, value);
		else
			sql=DBAccess.SQL("update [settings] set value='%s' where name='%s'", value, name);

		stmt.execute(sql);
		conn.commit();
		reloadCache();
	}
	
	public static void delete(String name, String value) throws SQLException
	{
		Connection conn=DBAccess.getConnection();
		String existingVal=get(name);
		Statement stmt = conn.createStatement();
		String sql;
		if(existingVal==null)
		{
			sql=DBAccess.SQL("delete [settings] where name = '%s'", name);
			stmt.execute(sql);
			conn.commit();
		}
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
			e.printStackTrace();
		}
	}
	
	public String settings() throws Exception 
	{
		List<String> args=JDRequest.args();

		if(args.size()>2 && "reload".equals(args.get(2)))
		{
			reloadCache();
			PageHandler.setMessage("Cache Reloaded");
		}
		
		boolean isEdit=args.size()>3 && "edit".equals(args.get(3));
		
		XMLBuilder builder=new XMLBuilder();
		builder.tag("table")
			.tag("tr")
				.tag("th").text("Name").end()
				.tag("th").text("Value").end()
				.tag("th").text("Actions").end()
			.end();
		
		String sql=DBAccess.SQL("select * from [settings]");
		Connection conn=DBAccess.getConnection();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		String name="", value="", id="";
		while (rs.next())
		{
			  builder.tag("tr");
			  id=rs.getString("id");
			  name = rs.getString("name");
			  value = rs.getString("value");
			  builder
			  	.tag("td")
			  		.text(name)
			  	.end()
			  	.tag("td")
			  		.text(value)
			  	.end()
			  	.tag("td")
		  		.text(Render.link("Edit", "/admin/settings/"+id+"/edit"))
		  		.end();
			  builder.end();//tr
			  
			  if(isEdit && id.equals(args.get(2)))
			  {
				  Map<String, String[]> formVals=new HashMap<String, String[]>();
				  formVals.put("sname", new String[]{name});
				  formVals.put("svalue", new String[]{value});
				  Form.setFormValues(formVals);
			  }
		}
		builder.end();//table
		
		return Render.form(getForm("JDSettingsForm")) + "<br />" + builder.toString();
	}

	public void urlpatterns(Map<String, String> urlCallbackMap)	
	{
		urlCallbackMap.put("/admin/settings", "settings");
		urlCallbackMap.put("/admin/settings/reload", "settings");
		urlCallbackMap.put("/admin/settings/%/edit", "settings");
	}

	public void JDSettingsForm(Form form)
	{
		form.addComponent(new TextBox("sname").title("Name").value(""));
		form.addComponent(new TextBox("svalue").title("Value").value(""));
		
		form.addComponent(new Submit("submitbtn").title("Add!"));
	}
	
	public boolean JDSettingsForm_validate(HashMap<String, String[]> params)
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

	public boolean JDSettingsForm_submit(HashMap<String, String[]> params)
	{
		String name = params.get("sname")[0];
		String value = params.get("svalue")[0];
		try
		{
			JDSettings.set(name, value);
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
