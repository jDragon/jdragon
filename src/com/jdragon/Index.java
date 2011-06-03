package com.jdragon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdragon.system.*;

/**
 * Servlet implementation class Index
 */
public class Index extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    }
    
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DBUtil dbutil = new DBUtil();
		dbutil.connect();

		PrintWriter out = response.getWriter();

		String reqURI=request.getRequestURI();
		reqURI=reqURI.substring(request.getContextPath().length());
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(reqURI);
		while(tok.hasMoreTokens())
			list.add(tok.nextToken("/"));
		
		try 
		{
			BaseIngredient i=getIngredient(reqURI, dbutil);
			Map vars=new HashMap();
			vars.put("content", i.mainCourse(list));
			
			String htmlOut=TemplateHandler.processTemplate(vars, "html.ftl");
			out.println(htmlOut);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbutil.disconnect();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	private BaseIngredient getIngredient(String path, DBAccess db) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		Class<?> c = Class.forName(RouteHandler.getIngredientName(path, db));
		BaseIngredient inst=(BaseIngredient)c.newInstance();
		return inst;
	}
}
