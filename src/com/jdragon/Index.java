package com.jdragon;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.jdragon.system.*;
import com.jdragon.system.chunk.Chunk;
import com.jdragon.system.chunk.ChunkEntry;
import com.jdragon.system.form.Form;

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
		DBAccess.connect();
		
		PageHandler.init();
		Form.init();
		JDSession.init(request);
		
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
			BaseElement ingr = null;
			try
			{
				ingr = getElement(reqURI, request);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if(ingr==null)
			{
				ingr=new JDErrorHandler();
			}
			
			if(method.equals("POST"))
			{
				String[] formNames=request.getParameterValues("FORMNAME");
				if(formNames.length!=1 || formNames[0]==null || formNames[0].equals(""))
				{
					//throw error here
				}
				String formName=formNames[0];
				
				HashMap<String, String[]> params=new HashMap<String, String[]>();
				Enumeration<String> paramNames = request.getParameterNames();
				while(paramNames.hasMoreElements()) 
				{
					String paramName = paramNames.nextElement();
					String[] paramValues = request.getParameterValues(paramName);
					params.put(paramName, paramValues);
					Form.setFormValues(params);
				}
				
				ingr = getElement(reqURI, request);
				if(ingr.formValidate(formName, params)==true)
					ingr.formSubmit(formName, params);
				
				ingr.setSubmit(true);
			}
			
			Map<String, Object> vars=new HashMap<String, Object>();
			vars.put("content", ingr.mainContent(list));
			vars.put("title", "Main Content");
			
			String redirURL=JDSession.getRedirectURL();
			if(redirURL!=null && !redirURL.equals(""))
				response.sendRedirect(redirURL);
			
			List<ChunkEntry> seList=getChunks("");
			for(int indx=0; indx<seList.size(); indx++)
			{
				ChunkEntry se=seList.get(indx);
				String sPosition=se.getPosition();
				String sName=se.getName();
				String sIngrName=se.getChunk();
				BaseElement sIngr=getIngredientByName(sIngrName, request);
				
				Chunk s=sIngr.chunk(sName);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("title", s.getTitle());
				map.put("data", s.getContent());
				String str=PageHandler.processTemplate(map, "seasonings.ftl");
				
				String contentStr=(String)vars.get(sPosition);
				if(contentStr==null)contentStr="";
				contentStr=contentStr+str;
				vars.put(sPosition, contentStr);
			}

//Error Messages			
			HashMap<String, String> errMap=(HashMap<String, String>)request.getAttribute("_jDr_ErrorMap");
			if(errMap!=null)
				vars.put("errors", errMap);

			PageHandler.addCSS("/jdragon/Templates/zengarden-sample.css");
			
			String htmlOut=PageHandler.processTemplate(vars, "html.ftl");
			out.println(htmlOut);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			PageHandler.cleanup();
		}
		DBAccess.disconnect();
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
	
	private BaseElement getElement(String path, HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		return getIngredientByName(RouteHandler.getIngredientName(path), request);
	}

	private BaseElement getIngredientByName(String name, HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, MalformedURLException
	{
		BaseElement inst=BaseElement.getElementByName(name);

		return inst;
	}

	private List<ChunkEntry> getChunks(String path) throws SQLException
	{
		List<ChunkEntry> seList = new ArrayList<ChunkEntry>();
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.resolvePrefix("select * from [seasonings]");
		ResultSet rs=stmt.executeQuery(sql);
		while (rs.next())
		{
			ChunkEntry se=new ChunkEntry();
			se.setName(rs.getString("name"));
			se.setChunk(rs.getString("ingredient"));
			se.setPosition(rs.getString("position"));
			seList.add(se);
		}
		
		return seList;
	}
}
