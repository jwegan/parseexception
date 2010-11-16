package com.parseexception.actionbeans;

import javax.servlet.http.*;

import java.util.*;
import org.apache.struts.action.*;

import com.parseexception.IPInfo;
import com.parseexception.Log;
import com.parseexception.displaybeans.UserBean;
import com.parseexception.model.*;

public class BaseAction extends Action {
	/*
	 * Variables
	 */
	protected User curUser;
	private Random rng = new Random();

	protected enum ListType {
		SOLUTION, MESSAGE, COMMENT
	}

	public BaseAction() {
		super();
		curUser = null;
	}

	protected boolean deleteCookie( String name,
								    HttpServletRequest request,
									HttpServletResponse response) {
		// Get user info from cookie
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().compareTo(name) == 0) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				break;
			}
		}

		return true;
	}
	
	protected String getCookie(String name, HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		String ret = null;
		
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().compareTo(name) == 0) {
				ret = cookies[i].getValue();
				break;
			}
		}
		
		return ret;
	}

	protected boolean deleteLoginCookie(HttpServletRequest request,
			HttpServletResponse response) {
		// Get user info from cookie
		deleteCookie("uid", request, response);
		deleteCookie("login_cookie", request, response);
		request.setAttribute("loggedin_uid", new Integer(0));
		return true;
	}

	protected boolean setLoginCookie(int uid, int maxAge,
			HttpServletRequest request, HttpServletResponse response) {
		// Get user
		User u = User.getUser(uid);
		int rand = rng.nextInt();
		
		// Delete keep_loggedin cookie
		deleteCookie("keep_loggedin", request, response);
		deleteCookie("oid_provider", request, response);

		// Save uid in cookie
		Cookie ck = new Cookie("uid", Integer.toString(u.getUid()));
		ck.setMaxAge(maxAge);
		response.addCookie(ck);

		// Save password in cookie
		if (u.setLoginCookie(rand)) {
			ck = new Cookie("login_cookie", Integer.toString(rand));
			ck.setMaxAge(maxAge);
			response.addCookie(ck);
		} else {
			return false;
		}

		// Store uid in request because cookies in response won't be valid
		// until the user initiates another request
		request.setAttribute("loggedin_uid", new Integer(u.getUid()));

		return true;
	}

	protected int validateLoginCookie(HttpServletRequest request) {
		int uid = 0, login_cookie = 0;
		
		String str = getCookie("uid", request);
		if(str != null)
			uid = Integer.parseInt(str);
		
		str = getCookie("login_cookie", request);
		if(str != null)
			login_cookie = Integer.parseInt(str);

		User u = User.getUser(uid);
		return (uid > 0 && u.getLoginCookie() == login_cookie) ? uid : 0;
	}

	protected void saveLastURL(HttpServletRequest request,
			HttpServletResponse response) {
		// Save last URL so we can redirect to it after login
		String urlstr = request.getServletPath();
		if (!urlstr.contains("login") && !urlstr.contains("ajax")) {
			String query = request.getQueryString();
			if (query != null) {
				urlstr += "?" + query;
			}

			// Save lasturl in cookie
			Cookie ck = new Cookie("lasturl", urlstr);
			ck.setMaxAge(-1);
			response.addCookie(ck);
		}
	}

	public boolean verifyLogin(HttpServletRequest request,
			HttpServletResponse response, boolean required) throws Exception {

		// Log IP
		IPInfo ip = IPInfo.getIPInfo(request.getRemoteAddr());
		ip.incrementPV();
		long timeout = 5000;
		if(ip.getIsSpammer())
		{
			timeout = 10000;
		}
		
		if ((ip.getPV() >= 8 && ip.getRequestPeriodMs() <= timeout)) {
			// Handle possible bot
			String useragent = request.getHeader("user-agent");
			String bypass = request.getParameter("dbgmode");
			if (useragent != null
					&& (useragent.contains("msnbot")
							|| useragent.contains("oogle") || useragent
							.contains("yahoo"))) {
				Log.log("IPFilter: logged bot access from "
						+ request.getRemoteAddr() + " with user-agent "
						+ useragent, Log.INFO);
			} else if (bypass == null || !bypass.equals("true")) { // Is a
																	// spammer
				if (!ip.getIsSpammer()) {
					Log.log("IPFilter: bot activity detected from "
							+ request.getRemoteAddr() + " with user-agent "
							+ useragent, Log.INFO);
					ip.setIsSpammer(true);
				}

				throw new RuntimeException("Spam like access pattern detected");
			}
		} else if (ip.getRequestPeriodMs() > timeout) {
			ip.reset();
		}

		// Save url
		saveLastURL(request, response);

		// Get user info from cookie or from request
		int uid = 0;
		boolean bLoggedIn = false;

		Integer uidint = (Integer) request.getAttribute("loggedin_uid");

		if (uidint != null) {
			uid = uidint.intValue();
		} else {
			uid = validateLoginCookie(request);
		}

		// Validate user info
		if (uid > 0) {
			bLoggedIn = true;
		} else if (required) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("login.notloggedin"));
			saveErrors(request, errors);
			return false;
		}

		curUser = (bLoggedIn) ? User.getUser(uid) : null;
		request.setAttribute("loggedIn", Boolean.toString(bLoggedIn));

		if (bLoggedIn) {
			UserBean curBean = new UserBean(curUser, null);
			request.setAttribute("curUser", curBean);

			int unreadCount = DBQueries.getUnreadMessageCount(uid);
			if (unreadCount > 0)
				request.setAttribute("unreadCount", new Integer(unreadCount));
		}

		return true;
	}

	protected void setNextPrev(HttpServletRequest request, int pageNum,
			ReturnList<?> results) {
		if (pageNum > 0) {
			request.setAttribute("prev", Integer.toString(pageNum - 1));
		}

		if (results.bMore) {
			request.setAttribute("next", Integer.toString(pageNum + 1));
		}
	}
}
