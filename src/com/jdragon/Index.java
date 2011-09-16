package com.jdragon;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.jdragon.system.*;
import com.jdragon.system.chunk.Chunk;
import com.jdragon.system.chunk.ChunkEntry;
import com.jdragon.system.element.JDErrorHandler;
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
		
		String reqURI=request.getRequestURI();
		reqURI=reqURI.substring(request.getContextPath().length());

		try 
		{
			BaseElement elem = null;
			String callback="";
			try
			{
				String[] ecArr=RouteHandler.getElementAndCallback(reqURI);
				if(ecArr==null || ecArr.length<2)
					throw new JDException("Error Occured retrieving element for: "+reqURI);
				elem = BaseElement.getElementByName(ecArr[0]);
				callback=ecArr[1];
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			/* If we are not able to get element, there is something wrong. In this case,
			 * default to error handler element & do not process POST params & form
			 */
			
			String formName="", formElementName="";
			boolean isPost=method.equals("POST");
			if(elem==null)
			{
				elem=new JDErrorHandler();
				callback="mainContent";
			}
			else if(isPost)
			{
				String[] formIDs=request.getParameterValues(JDCONST.FORM_ID);
				if(formIDs.length!=1 || formIDs[0]==null || formIDs[0].equals(""))
				{
					//throw error here
				}
				String formID=formIDs[0];
				String formIDArr[]=formID.split("#");
				
				if(formIDArr.length<2)
				{
					throw new JDException("Something wrong with Form");
				}
				
				formElementName=formIDArr[0];
				formName=formIDArr[1];
				
				if(elem.getClass().getCanonicalName().equals(formElementName))
					processPostData(request, elem, formName);
				
			}
			
			Map<String, Object> vars=new HashMap<String, Object>();
			
			String content="";
			Class<? extends BaseElement> cls=elem.getClass();
			Method callbackMethod=cls.getMethod(callback, (Class<?>[])null);
			content=(String)callbackMethod.invoke(elem, (Object[])null);
			
			
			vars.put("content", content);
			vars.put("title", "Main Content");
			
			String redirURL=JDSession.getRedirectURL();
			if(redirURL!=null && !redirURL.equals(""))
			{
				response.sendRedirect(redirURL);
				return;
			}
// TODO Handle case of same elements for multiple chunks
			List<ChunkEntry> seList=getChunks("");
			for(int indx=0; indx<seList.size(); indx++)
			{
				ChunkEntry se=seList.get(indx);
				String sPosition=se.getPosition();
				String sName=se.getName();
				String selemName=se.getChunk();
				BaseElement chunkElem=BaseElement.getElementByName(selemName);

				if(isPost && chunkElem.getClass().getCanonicalName().equals(formElementName))
					processPostData(request, chunkElem, formName);
				
				Chunk s=chunkElem.chunk(sName);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("title", s.getTitle());
				map.put("data", s.getContent());
				String str=PageHandler.processTemplate(map, "seasonings.ftl");
				
				String contentStr=(String)vars.get(sPosition);
				if(contentStr==null)contentStr="";
				contentStr=contentStr+str;
				vars.put(sPosition, contentStr);
			}

			String htmlOut=PageHandler.processTemplate(vars, "html.ftl");

			PrintWriter out = response.getWriter();
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

	private void processPostData(HttpServletRequest request, BaseElement elem, String formName)
	{
		HashMap<String, String[]> params=new HashMap<String, String[]>();
		Enumeration<?> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
			String paramName = (String)paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			params.put(paramName, paramValues);
			Form.setFormValues(params);
		}
		
		if(elem.validateForm(formName, params)==true)
			elem.submitForm(formName, params);
		
		elem.setSubmit(true);
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

	private List<ChunkEntry> getChunks(String path) throws SQLException
	{
		List<ChunkEntry> seList = new ArrayList<ChunkEntry>();
		Connection conn=DBAccess.getConnection();
		Statement stmt = conn.createStatement();
		String sql=DBAccess.SQL("select * from [chunks]");
		ResultSet rs=stmt.executeQuery(sql);
		while (rs.next())
		{
			ChunkEntry se=new ChunkEntry();
			se.setName(rs.getString("name"));
			se.setChunk(rs.getString("element"));
			se.setPosition(rs.getString("position"));
			seList.add(se);
		}
		
		return seList;
	}
}
