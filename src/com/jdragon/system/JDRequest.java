package com.jdragon.system;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class JDRequest
{
	private static ThreadLocal<List<String>> argsTL = new ThreadLocal<List<String>>();
	
	public static List<String> args()
	{
		return argsTL.get();
	}
	
	static void init(HttpServletRequest request)
	{
		String reqURI=request.getRequestURI();
		reqURI=reqURI.substring(request.getContextPath().length());
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(reqURI);
		while(tok.hasMoreTokens())
			list.add(tok.nextToken("/"));

		argsTL.set(list);
	}
}
