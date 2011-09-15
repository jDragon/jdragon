package com.jdragon.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JDSession 
{

	private static ThreadLocal<JDSession> jdSessHolder = new ThreadLocal<JDSession>();
	private static ThreadLocal<String> redirURLHolder = new ThreadLocal<String>();
	
	private HttpSession _session=null;
	
	private JDUser _user=JDUser.getGuestUser();
	
	public static JDUser getUser() {
		
		return jdSessHolder.get()._user;
	}

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
		
		JDRequest.init(request);
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

	public static void setUser(JDUser user)
	{
		JDSession.jdSessHolder.get()._user = user;
	}
}
