import java.nio.file.*;
import java.io.*;
import javax.servlet.http.*;

public class Utilities extends HttpServlet {
	private PrintWriter out = null;

	public Utilities(PrintWriter out) {
		this.out = out;
	}

	public String printHtmlUserLeftNav(String file, User currentUser) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		String lookupString = "<x></x>";
		String replaceBy = "";

		if(currentUser.getRole()==0) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/AS2/ViewOrderServlet\'>View Order</a></li>"
					// + "<li><a href='/AS2/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";
		} else if (currentUser.getRole() == 1) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/AS2/AddProduct'>Add Product</a></li>"
					+ "<li><a href='/AS2/UpdateProduct'>Update/Delete Product</a></li>"
					// + "<li><a href='/AS2/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";

		} else if (currentUser.getRole() == 2) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/AS2/Registration\'>Register Customer</a></li>"
					+ "<li><a href='/AS2/UpdateOrder\'>View Order</a></li>"
					// + "<li><a href='/AS2/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";

		}

		result = HTMLStringReplace(result, lookupString, replaceBy);

		String lookupString2 = "<y></y>";
		String replaceBy2 = "";

		if(currentUser.getRole()==1) {
			replaceBy2=""
					+"<li>"
					+"<h4>Data Analytics</h4>"
					+"<ul>"
					+"<li><a href='/AS2/DataAnalyticsServlet\'>Data Analytics on Review</a></li>"
					+"</ul>"
					+"</li>";
		}
		result = HTMLStringReplace(result, lookupString2, replaceBy2);

		return result;
	}

	public String printHtmlUserHeader(String file, User currentUser) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		String lookupString = "Cart";
		String replaceBy = "Cart<sup>(" + StartupServlet.cart.size() + ")</sup>";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Hi There! Welcome.";
		replaceBy = "Hi " + currentUser.getFname() + "! Welcome.";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "/AS2/LoginServlet";
		replaceBy = "/AS2/LogoutServlet";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Sign in";
		replaceBy = "Sign out";
		result = HTMLStringReplace(result, lookupString, replaceBy);		

		return result;
	}

	public String printHtml(String file) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		if (file.contains("Header.html")) {
			String lookupString = "Cart";
			String replaceBy = "Cart<sup>(" + StartupServlet.cart.size() + ")</sup>";
			result = HTMLStringReplace(result, lookupString, replaceBy);
		}

		return result;
	}

	private String HTMLStringReplace(String result, String lookupString, String replaceBy) {
		String temp = result.replace(lookupString, replaceBy);
		return temp;
	}

	private String HTMLtoString(String file) {
		StringBuilder content = new StringBuilder();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null)
				content.append(str);
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();

	}

}
