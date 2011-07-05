/**
 * 
 */
package com.jdragon.system;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author raghukr
 *
 */
public class PageHandler 
{
	public static String processTemplate(Map<String, Object> vars, String templateName) throws IOException, TemplateException
	{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(
                new File("E:\\jdragon\\eclipseprj\\WebContent\\Templates"));
		Template tpl=cfg.getTemplate(templateName);
		
		String cssStr="";
		List<String> cssList=StaticContent.getCSSList();
		for(int i=0; i<cssList.size(); i++)
		{
			cssStr=cssStr+"<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssList.get(i) + "\" />";
		}
		vars.put("css", cssStr);

		List<String> errList=StaticContent.getErrorList();
		vars.put("errors", errList);

		
		Writer writer=new StringWriter();
		tpl.process(vars, writer);

		return writer.toString();
	}
	
	public static void init()
	{
		StaticContent.init();
	}

	public static void cleanup()
	{
		StaticContent.clear();
	}
	
	public static void addCSS(String cssStr)
	{
		StaticContent.addCSS(cssStr);
	}
	
	public static void addJS(String jsStr)
	{
		StaticContent.addJS(jsStr);
	}
	
	public static void setError(String errStr)
	{
		StaticContent.setError(errStr);
	}
	
}

class StaticContent
{
	private static ThreadLocal<List<String>> tlCssList= new ThreadLocal<List<String>>();
	private static ThreadLocal<List<String>> tlJsList= new ThreadLocal<List<String>>();
	private static ThreadLocal<List<String>> tlErrList= new ThreadLocal<List<String>>();
	
	static void init()
	{
		tlCssList.set(new ArrayList<String>());
		tlJsList.set(new ArrayList<String>());
		tlErrList.set(new ArrayList<String>());
	}
	
	static void addJS(String jsStr)
	{
		List<String> jsList=tlJsList.get();
		jsList.add(jsStr);
	}

	static void addCSS(String cssStr)
	{
		List<String> cssList=tlCssList.get();
		if(cssList==null)
		{
			cssList=new ArrayList<String>();
			tlCssList.set(cssList);
		}
		cssList.add(cssStr);
	}
	
	static List<String> getCSSList()
	{
		return tlCssList.get();
	}
	
	static List<String> getErrorList()
	{
		return tlErrList.get();
	}
		static void clear()
	{
		tlCssList.remove();
		tlJsList.remove();
		tlErrList.remove();
	}
	
	static void setError(String errStr)
	{
		tlErrList.get().add(errStr);
	}
	
}
