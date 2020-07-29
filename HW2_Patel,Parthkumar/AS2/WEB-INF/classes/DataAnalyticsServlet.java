import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.ArrayList;

public class DataAnalyticsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MongoDBDataStoreUtilities mdsu = new MongoDBDataStoreUtilities();
		

		HttpSession session = request.getSession(false);
		User currentUser = null;
		if (session != null) {
			currentUser = (User) session.getAttribute("user");
		}

		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);

		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

		out.println(getHTMLContent());
		out.println(getContentFooter());
		
		
		

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	private String getContentFooter() {
		return "</section>" + "</div>";
	}
	private String getHTMLContent() {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";
		String _content ="";

		_content =""
				+ "<h1> Data Analytics on Review</h1>"
				+"<form id='datanalytics' action='ViewDataAnalytics'"
				+"<table>"
				+ "<tr>"
				+ "<td>"
				+"<input type='checkbox' name='querycheckbox' value='productname'/> Select"
				+"</td>"
				+"<td>"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"Product name"
				//+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				;

		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
		ArrayList<String> productnameslist = msdsq.getAllProductNames();

		_content+=""
				+"<select name='productnamedropdown'>"+ "<option value="+""+">"+""+"</option>";
		for(String productname: productnameslist)
		{
			_content+=""+"<option value="+productname+">"+productname+"</option>";
		}

		_content+=""
				+"</select>"
				+"</td>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"</tr>"

				//PRODUCT PRICE
				+"<tr>"
				+"<td>"
				+"<input type='checkbox' name='querycheckbox' value='productprice'/> Select"
				+"</td>"
				+"<td>"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"Product price"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"</td>"
				+"<td>"
				+"<input type='text' name='price' />"
				+"</td>"
				+"<td>"
				+"<input type='radio' name='compareprice' value='priceequals'/> Equals"
				+"<input type='radio' name='compareprice' value='pricegreaterthan'/> Greater Than"
				+"<input type='radio' name='compareprice' value='pricelessthan'/> Less Than"
				+"</td>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"</tr"
				
				
				
				//REVIEW RATE
				+"<tr>"
				+"<td>"
				+"<input type='checkbox' name='querycheckbox' value='reviewrating'> Select"
				+"<td>"
				+"<td>"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"Review Rating"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"<select name='reviewratedropdown'>"
				+"<option value='1'>"+1+"</option>"
				+"<option value='2'>"+2+"</option>"
				+"<option value='3'>"+3+"</option>"
				+"<option value='4'>"+4+"</option>"
				+"<option value='5'>"+5+"</option>"
				+"</select>"
				+"</td>"
				+"<td>"
				+"<input type='radio' name='comparerating' value='rateequals'/> Equals"
				+"<input type='radio' name='comparerating' value='rategreaterthan'/> Greater Than"
				+"</td>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"</tr>"
				
				
				
				
				//RETAILER CITY
				+"<tr>"
				+"<td>"
				+"<input type='checkbox' name='querycheckbox' value='retailercity'> Select"
				+"</td>"
				+"<td>"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"Retailer City"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"</td>"
				+"<td>"
				+"<input type='text' name='city' />"
				+"</td>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"</tr>"
				
				//GROUP BY
				+"<tr>"
				+"<td>"
				+"<input type='checkbox' name='querycheckbox' value='groupby'> Group by"
				+"</td>"
				+"<td>"
				+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
				+"<select name='groupbydropdown'>"
				+"<option value='city'>"+"city"+"</option>"
				+"<option value='product_name'>"+"product_name"+"</option>"
				+"<option value='zipcode'>"+"zipcode"+"</option>"
				+"</select>"
				+"</td>"
				+"<td>"
				+"<input type='radio' name='countordetail' value='wantcount'/> Count"
				+"<input type='radio' name='countordetail' value='wantdetail'/> Detail"
				+"</td>"
				+"<br>"
				+"<br>"
				+"<br>"
				+"</tr>"
				
				
				+"</table>"
				+"<input type='submit' value='Find Data'>"
				
				+"</form>";			

		return _Heading + _content;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	  
	  
}
