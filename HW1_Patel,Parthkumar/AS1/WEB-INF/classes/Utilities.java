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
					+ "<li><a href='/AS1/ViewOrderServlet\'>View Order</a></li>"
					// + "<li><a href='/AS1/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";
		} else if (currentUser.getRole() == 1) {
			replaceBy = ""+ "<li>"+ "<h4>User Control</h4>"+ "<ul>"+ "<li><a href='/AS1/AddProduct'>Add Product</a></li>"
						+ "<li><a href='/AS1/UpdateProduct'>Update Product</a></li>"
						  //+ "<li><a href='/AS1/LogoutServlet'>Logout</a></li>"
						+ "</ul>"+ "</li>";

		} else if (currentUser.getRole() == 2) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/AS1/Registration\'>Register Customer</a></li>"
					+ "<li><a href='/AS1/UpdateOrder\'>View Order</a></li>"
					// + "<li><a href='/AS1/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";

		}

		result = HTMLStringReplace(result, lookupString, replaceBy);


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

		lookupString = "/AS1/LoginServlet";
		replaceBy = "/AS1/LogoutServlet";
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
