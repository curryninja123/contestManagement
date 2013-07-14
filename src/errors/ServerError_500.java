package errors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import util.HTMLCompressor;
import util.UserCookie;

@SuppressWarnings("serial")
public class ServerError_500 extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException
	{
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "html/errors, html/snippets");
		ve.init();
		Template t = ve.getTemplate("error500.html");
		VelocityContext context = new VelocityContext();
		
		UserCookie userCookie = UserCookie.getCookie(req);
		boolean loggedIn = userCookie != null && userCookie.authenticate();
		
		context.put("year", Calendar.getInstance().get(Calendar.YEAR));
		context.put("loggedIn", loggedIn);
		context.put("error", req.getAttribute("javax.servlet.error.message"));
		context.put("uri", req.getAttribute("javax.servlet.error.request_uri"));
		context.put("servlet", req.getAttribute("javax.servlet.error.servlet_name"));
		context.put("date", new Date().toString());
		if(loggedIn)
		{
			context.put("user", userCookie.getUsername());
			context.put("admin", userCookie.isAdmin());
		}
		
		StringWriter sw = new StringWriter();
		t.merge(context, sw);
		sw.close();
		resp.setContentType("text/html");
		resp.getWriter().print(HTMLCompressor.customCompress(sw));
	}
}