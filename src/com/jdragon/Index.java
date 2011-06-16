package com.jdragon;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.jdragon.system.*;
import com.jdragon.system.seasonings.Seasoning;
import com.jdragon.system.seasonings.SeasoningEntry;

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

	@SuppressWarnings("unchecked")
	protected void processRequests(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String method=request.getMethod();
		DBUtil dbutil = new DBUtil();
		dbutil.connect();
		
//		HttpSession session = request.getSession();
//		String sessionID = session.getId();
		
		PrintWriter out = response.getWriter();

		String reqURI=request.getRequestURI();
		reqURI=reqURI.substring(request.getContextPath().length());
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(reqURI);
		while(tok.hasMoreTokens())
			list.add(tok.nextToken("/"));
		
		try 
		{
			BaseIngredient ingr = null;
			try
			{
				ingr = getIngredient(reqURI, request, dbutil);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if(ingr==null)
			{
				ingr=new JDErrorHandler();
				ingr.setRequest(request);
				ingr.setDB(dbutil);
			}
			
			if(method.equals("POST"))
			{
				String[] formNames=request.getParameterValues("FORMNAME");
				if(formNames.length!=1 || formNames[0]==null || formNames[0].equals(""))
				{
					//throw error here
				}
				String formName=formNames[0];
				
				HashMap params=new HashMap();
				Enumeration paramNames = request.getParameterNames();
				while(paramNames.hasMoreElements()) 
				{
					String paramName = (String)paramNames.nextElement();
					String[] paramValues = request.getParameterValues(paramName);
					params.put(paramName, paramValues);
				}
				
				ingr = getIngredient(reqURI, request, dbutil);
				if(ingr.formValidate(formName, params)==true)
					ingr.formSubmit(formName, params);
				
				ingr.setSubmit(true);
			}
			
			Map vars=new HashMap();
			vars.put("content", ingr.mainCourse(list));
			
			List<SeasoningEntry> seList=getSeasoningEntries("", dbutil);
			for(int indx=0; indx<seList.size(); indx++)
			{
				SeasoningEntry se=seList.get(indx);
				String sPosition=se.getPosition();
				String sName=se.getName();
				String sIngrName=se.getIngredient();
				BaseIngredient sIngr=getIngredientByName(sIngrName, request, dbutil);
				
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
			HashMap errMap=(HashMap)request.getAttribute("_jDr_ErrorMap");
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequests(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		processRequests(request, response);
	}

	private BaseIngredient getIngredient(String path, HttpServletRequest request, DBAccess db) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		return getIngredientByName(RouteHandler.getIngredientName(path, db), request, db);
	}

	private BaseIngredient getIngredientByName(String name, HttpServletRequest request, DBAccess db) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		BaseIngredient inst=BaseIngredient.getIngredientByName(name, this);
		inst.setRequest(request);
		inst.setDB(db);

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
