package com.jdragon.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JDSession 
{
	private String sessionUser=null;
	private String userRole=null;
	private String userID=null;
	
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
	
	public static void init(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Object sessionObj=session.getAttribute("JDSession");
		if(sessionObj==null)
		{
			sessionObj=new JDSession();
			session.setAttribute("JDSession", sessionObj);
		}
		
		jdSessHolder.set((JDSession)sessionObj);
	}
}
