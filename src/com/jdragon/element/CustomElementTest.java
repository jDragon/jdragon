/**
 * 
 */
package com.jdragon.element;

import java.util.*;

import com.jdragon.system.BaseElement;
import com.jdragon.system.JDRequest;
import com.jdragon.system.JDSession;
import com.jdragon.system.PageHandler;
import com.jdragon.system.Render;
import com.jdragon.system.chunk.Chunk;
import com.jdragon.system.form.*;

/**
 * @author raghukr
 *
 */
public class CustomElementTest extends BaseElement 
{
	String _sum="";

	/* (non-Javadoc)
	 * @see com.jdragon.system.element#content()
	 */
	public String mainContent() throws Exception 
	{
		List<String> args=JDRequest.args();
		HashMap<String, Object> vars = new HashMap<String, Object>();

		if(this.submitted())
		{
			vars.put("sum", _sum);
		}
		vars.put("welcome1", _t("Welcome to JDragon Element test"));
		vars.put("welcome2", _t("Hello, How are you?"));
		vars.put("args", args);
		vars.put("form", Render.form(getForm("myForm")));

		PageHandler.setError("Test Error Message");

		return PageHandler.processTemplate(vars, "test.ftl");
	}

	public String[] chunksList()
	{
		String[] strList={"sample1", "sample2"};
		return strList;
	}

	public Chunk chunk_sample1()
	{
		Boolean isLoggedIn=Boolean.FALSE;
		try {
			BaseElement elem=BaseElement.getElementByName("com.jdragon.system.element.JDAuth");
			isLoggedIn=(Boolean)elem.api("isLoggedIn", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String lnk=isLoggedIn.equals(Boolean.FALSE)?"<a href='/jdragon/login'>Login</a>":"<a href='/jdragon/logout'>Logout</a>";
		
		Chunk s=new Chunk();
		s.setTitle(_t("Sample 1"));
		s.setContent("Hello, World! <br />" + lnk);
		return s;
	}
	
	public Chunk chunk_sample2()
	{
		Chunk s=new Chunk();
		s.setTitle(_t("Sample 2"));
		s.setContent("Have a good day! <br />"
				+ "Your role: " + (JDSession.getUserRole()!=null?JDSession.getUserRole():"Guest")
				);
		return s;
	}	
	
	public void urlpatterns(Map<String, String> urlCallbackMap)	
	{
		urlCallbackMap.put("/Index/%/view", "mainContent");
		urlCallbackMap.put("/Indextest", "mainContent");
	}

	public Form getForm(String formName)
	{
		Form form=new Form(formName);
		form.addComponent(new TextBox("num1").title("Number 1").value(""));
		form.addComponent(new TextBox("num2").title("Number 2").value(""));
		form.addComponent(new File("testfile").title("Upload a file"));
		
		form.addComponent(new Select("select1")
							.option("+", "Add")
							.selectedoption("-", "Subtract")
							.title("Select Operation")
		);
		
		form.addComponent(new Submit("submitbtn").title("Go!"));
		return form;
	}
	
	@Override
	public boolean validateForm(String formName, HashMap<String, String[]> params)
	{
	    String val1 = params.get("num1")[0];
	    String val2 = params.get("num2")[0];
	    boolean ret=true;
		try
		{
			Integer.parseInt(val1);
		} catch (NumberFormatException e)
		{
			Form.setError("num1", "Not a Number");
			ret=false;
		}
		try
		{
			Integer.parseInt(val2);
		} catch (NumberFormatException e)
		{
			Form.setError("num2", "Not a Number");
			ret=false;
		}
		return ret;
	}

	public boolean submitForm(String formName, HashMap<String, String[]> params)
	{
		String val1 = params.get("num1")[0];
		String val2 = params.get("num2")[0];
		int ival1=Integer.parseInt(val1);
		int ival2=Integer.parseInt(val2);
		
		if(params.get("select1")[0].equals("+"))
			_sum=(new Integer(ival1+ival2)).toString();
		else
			_sum=(new Integer(ival1-ival2)).toString();
		return true;
	}
}
