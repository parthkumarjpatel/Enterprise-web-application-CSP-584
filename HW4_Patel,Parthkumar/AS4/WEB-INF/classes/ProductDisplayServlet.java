import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProductDisplayServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
	
		
		User currentUser = null;
		if(session !=null) {
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

		String type = request.getParameter("type");

/*		if(type.equalsIgnoreCase("External_Storage")) {
			type="External Storage";
		}*/

		String manufacturer = request.getParameter("manufacturer");
		if(manufacturer==null) {
			out.print(printProductCatalog(type));
		}else {
			out.print(printProductCatalog(type,manufacturer));
		}


		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String printProductCatalog(String type, String manufacturer) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "<center><h2 class='form-signin-heading'>"
				+ type.toUpperCase() + "</h2></center>" + "";

//		HashMap<String, ArrayList<Product>> product = SaxParserReader.filterByManufacturer(type, manufacturer);
		HashMap<String, ArrayList<Product>> product = MySqlDataStoreUtilities.filterByManufacturer(type, manufacturer);

		String _Product = "";
		for (Map.Entry<String, ArrayList<Product>> entry : product.entrySet()) {
			ArrayList<Product> prod = entry.getValue();
			for (Product p : prod) {
				_Product += "<div class='row row-space'>" + "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" + p.getType()
						+ "' class='img-thumbnail'></a>" + "" + "</div>" + "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" 
						// + "<a href='#'>" 
						+ p.getModel() + "</a>" + "</h4>"
						+ "<label class='control-label'>Manufacturer: </label>" + "<label class='info-label '>"
						+ p.getManufacturer() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Condition: </label>" + "<label class='info-label '>"
						+ p.getCondition() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Discount: </label> " + "<label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" + "<label "
						+ (p.getQuantity() > 0 ? "class='availibility'>Currently Available</label>"
								: "class='availibility'>Currently Unavailable")
						+ "</div>" + "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$"
						+ p.getPrice() + "</h3>"
						+ "<form action='/AS4/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
						+ "<table>" + "<tr>" + "<td>"

						+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
						+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.getProductId()
						+ "'   type='submit'>Add to Cart</button>" + "</td>" + "</tr>" + "<tr>" + "<td>"
						+ "<a class='btn btn-link' role='button' href='/AS4/ViewProductServlet?productId="
						+ p.getProductId() + "'>View Product</a>" + "</td>" + "</tr>" + "</table>" + "</form>" + "</div>" + ""
						+ "</div>" + "<hr class='draw-line'>";
			}
		}

		String _Footer = "</section>" + "</div>";

		return _Heading + _Product + _Footer;
	}

	private String printProductCatalog(String type) {

		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "<center><h2 class='form-signin-heading'>"
				+ type.toUpperCase() + "</h2></center>" + "";

//		HashMap<String, ArrayList<Product>> product = SaxParserReader.filterByType(type);
		HashMap<String, ArrayList<Product>> product = MySqlDataStoreUtilities.filterByType(type);

		String _Product = "";
		for (Map.Entry<String, ArrayList<Product>> entry : product.entrySet()) {
			ArrayList<Product> prod = entry.getValue();
			for (Product p : prod) {
				_Product += "<div class='row row-space'>" + "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" + p.getType()
						+ "class='img-thumbnail'></a>" + "" + "</div>" + "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" + "<a href='#'>" + p.getModel() + "</a>" + "</h4>"
						+ "<label class='control-label'>Manufacturer: </label>" + " <label class='info-label '>"
						+ p.getManufacturer() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Condition: </label>"
						+ " <label class='info-label '>" + p.getCondition() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Discount: </label>" + " <label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" + "<label "
						+ (p.getQuantity() > 0 ? "class='availibility'>Currently Available</label>"
								: "class='availibility'>Currently Unavailable")
						+ "</div>"

						+ "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$" + p.getPrice()
						+ "</h3>"
						+ "<form action='/AS4/AddToCart' name='requestedForm' value='AddToCart' method='get'>"
						+ "<table>" + "<tr>" + "<td>"

						+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
						+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.getProductId()
						+ "'   type='submit'>Add to Cart</button>" + "</td>" + "</tr>" + "<tr>" + "<td>"
						+ "<a class='btn btn-link' role='button' href='/AS4/ViewProductServlet?productId="
						+ p.getProductId() + "'>View Product</a>" + "</td>" + "</tr>" + "</table>" + "</form>" + "</div>" + ""
						+ "</div>" + "<hr class='draw-line'>";
			}
		}

		String _Footer = "</section>" + "</div>";

		return _Heading + _Product + _Footer;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
