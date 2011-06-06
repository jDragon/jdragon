package com.jdragon;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.jdragon.system.*;

/**
 * @author raghukr
 *
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
			BaseIngredient ingr=getIngredient(reqURI, dbutil);
			ingr.setRequest(request);
			
			Map vars=new HashMap();
			vars.put("content", ingr.mainCourse(list));
			
			List<SeasoningEntry> seList=getSeasoningEntries("", dbutil);
			for(int indx=0; indx<seList.size(); indx++)
			{
				SeasoningEntry se=seList.get(indx);
				String sPosition=se.getPosition();
				String sName=se.getName();
				String sIngrName=se.getIngredient();
				BaseIngredient sIngr=getIngredientByName(sIngrName);
				sIngr.setRequest(request);
				
				Seasoning s=sIngr.seasoning(sName);
				HashMap map=new HashMap();
				map.put("title", s.getTitle());
				map.put("data", s.getContent());
				String str=TemplateHandler.processTemplate(map, "seasonings.ftl");
				
				String contentStr=(String)vars.get(sPosition);
				if(contentStr==null)contentStr="";
				contentStr=contentStr+str;
				vars.put(sPosition, contentStr);
			}
			
//Error Messages			
			HashMap errMap=(HashMap)request.getAttribute("jDr_ErrorMap");
			if(errMap!=null)
				vars.put("errors", errMap);
			
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
	
	private BaseIngredient getIngredientByName(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		Class<?> c = Class.forName(name);
		BaseIngredient inst=(BaseIngredient)c.newInstance();
		return inst;
	}
	
	private List<SeasoningEntry> getSeasoningEntries(String path, DBAccess db) throws SQLException
	{
		List<SeasoningEntry> seList = new ArrayList<SeasoningEntry>();
		Connection conn=db.getConnection();
		Statement stmt = conn.createStatement();
		String sql="select * from jd_seasonings";
		ResultSet rs=stmt.executeQuery(sql);
		while (rs.next())
		{
			SeasoningEntry se=new SeasoningEntry();
			se.setName(rs.getString("name"));
			se.setIngredient(rs.getString("ingredient"));
			se.setPosition(rs.getString("position"));
			seList.add(se);
		}
		
		return seList;
	}
}
