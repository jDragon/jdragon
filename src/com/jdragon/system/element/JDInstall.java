package com.jdragon.system.element;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.jdragon.system.BaseElement;
import com.jdragon.system.DBAccess;
import com.jdragon.system.PageHandler;
import com.jdragon.system.Render;
import com.jdragon.system.form.Form;
import com.jdragon.system.form.Submit;
import com.jdragon.system.form.TextBox;

public class JDInstall extends BaseElement
{
	public String mainContent() throws Exception
	{
		String str = Render.form(getForm("InstallerForm"));
		return str;
	}
	
	public Boolean install(String element)
	{
		try
		{
			BaseElement ingr=BaseElement.getElementByName(element);
			Map<String, String> urlCallbackMap=new HashMap<String, String>();

			ingr.urlpatterns(urlCallbackMap);

			Connection conn=DBAccess.getConnection();
			conn.setSavepoint();
			Statement stmt = conn.createStatement();
			String sql=DBAccess.SQL("delete from [routes] where element='%s'", element);
			stmt.execute(sql);
			
			for(Map.Entry<String, String> urlcb : urlCallbackMap.entrySet())
			{
				stmt = conn.createStatement();
				sql=DBAccess.SQL("insert into [routes] (path, element, callback) values ('%s', '%s', '%s')", urlcb.getKey(), element, urlcb.getValue());
				
				stmt.execute(sql);
			}
			conn.commit();
			return Boolean.TRUE;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return Boolean.FALSE;
		} 
	}
	
	public Form InstallerForm()
	{
		Form form=new Form("InstallerForm");
		form.addComponent(new TextBox("element").title("Element").value(""));
		
		form.addComponent(new Submit("submitbtn").title("Install"));
		return form;
	}
	
	public boolean InstallerForm_validate(HashMap<String, String[]> params)
	{
	    String elem = params.get("element")[0];
	    boolean ret=true;
		if(elem==null)
		{
		    Form.setError("element", "Name cannot be blank");
			ret=false;
		}
		return ret;
	}

	public boolean InstallerForm_submit(HashMap<String, String[]> params)
	{
	    String elem = params.get("element")[0];
	    boolean ret = install(elem);
		if(ret)
			PageHandler.setMessage( "Installed Element: " + elem );
		else
			Form.setError("element", "Element Installation failed");
	    return ret;
	}		
}
