package com.jdragon.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JDSession 
{
	private String sessionUser=null;
	private String userRole=null;
	private String userID=null;
	private HttpSession _session=null;
	
	public static String getUser() {
		
		return jdSessHolder.get().sessionUser;
	}

	public static String getUserID() {
		return jdSessHolder.get().userID;
	}
	
	public static void setCredentials(String sessionUser, String uid, String role) {
		JDSession sess=jdSessHolder.get();
		sess.sessionUser = sessionUser;
		sess.userRole = role;
		sess.userID=uid;
	}

	public static String getUserRole() {
		return jdSessHolder.get().userRole;
	}
	
	private static ThreadLocal<JDSession> jdSessHolder = new ThreadLocal<JDSession>();
	private static ThreadLocal<String> redirURLHolder = new ThreadLocal<String>();
	
	public static void init(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		JDSession sessionObj=(JDSession)session.getAttribute("JDSession");
		if(sessionObj==null)
		{
			sessionObj=new JDSession();
			session.setAttribute("JDSession", sessionObj);
			sessionObj._session=session;
		}

		jdSessHolder.set(sessionObj);
		
		redirURLHolder.set("");
	}
	
	public static void destroy()
	{
		JDSession jdsession=jdSessHolder.get();
		if(jdsession!=null)
		{
			if(jdsession._session!=null)
			{
				jdsession._session.removeAttribute("JDSession");
				jdsession._session.invalidate();
			}
			jdSessHolder.remove();
		}
	}
	
	public static void requestRedirect(String url)
	{
		redirURLHolder.set(url);
	}
	
	public static String getRedirectURL()
	{
		return redirURLHolder.get();
	}
}
