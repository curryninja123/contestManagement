package contestWebsite;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

@SuppressWarnings("serial")
public class Directions extends HttpServlet
{
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException
	{
		resp.setContentType("text/html");
		
		UserCookie userCookie = UserCookie.getCookie(req);
		boolean loggedIn = userCookie != null && userCookie.authenticate();

		Properties p = new Properties();
		p.setProperty("file.resource.loader.path", "html");
		Velocity.init(p);
		VelocityContext context = new VelocityContext();
		context.put("year", Calendar.getInstance().get(Calendar.YEAR));
		context.put("loggedIn", loggedIn);
		if(loggedIn)
		{
			context.put("user", userCookie.getUsername());
			context.put("admin", userCookie.isAdmin());
		}
		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate("directions.html", context, sw);
		sw.close();

		resp.getWriter().print(HTMLCompressor.compressor.compress(sw.toString()));
	}
}