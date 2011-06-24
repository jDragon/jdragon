package com.jdragon.util;

import java.util.ArrayList;
import java.util.List;

public class StaticContent
{
	private static ThreadLocal<List<String>> tlCssList= new ThreadLocal<List<String>>();
	private static ThreadLocal<List<String>> tlJsList= new ThreadLocal<List<String>>();
	
	public static void addJS(String jsStr)
	{
		List<String> jsList=tlJsList.get();
		if(jsList==null)
		{
			jsList=new ArrayList<String>();
			tlJsList.set(jsList);
		}
		jsList.add(jsStr);
	}

	public static void addCSS(String cssStr)
	{
		List<String> cssList=tlCssList.get();
		if(cssList==null)
		{
			cssList=new ArrayList<String>();
			tlCssList.set(cssList);
		}
		cssList.add(cssStr);
	}
	
	public static List<String> getCSSList()
	{
		return tlCssList.get();
	}
	
	public static void clear()
	{
		tlCssList.remove();
		tlJsList.remove();
	}
}
