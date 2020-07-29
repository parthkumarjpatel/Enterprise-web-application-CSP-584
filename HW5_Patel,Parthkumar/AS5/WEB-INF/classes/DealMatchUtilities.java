
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DealMatchUtilities extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User currentUser = null;
		Cart cart = null;

		if (session != null) {
			currentUser = (User) session.getAttribute("user");
			cart = (Cart) session.getAttribute("cart");
			if (cart != null) {
				StartupServlet.cart = cart;
			}
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
		// out.print(utility.printHtml(_dirPath + "Content.html"));

		out.print(getHTMLContent());

		out.println(getDealMatch());

		out.print(getFooter());

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String getDealMatch() throws IOException {
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		HashMap<String, Product> productMap = new HashMap<String, Product>();
		HashMap<String, Product> selectProduct = new HashMap<String, Product>();
		productMap = msdsu.readAllProduct();
		String _header = "" + "<center>"
				+ "We beat our competitors in all aspects. Price Match Guarantee.</center>";
		String line = "";
		String _content = "";
		for (Map.Entry<String, Product> entry : productMap.entrySet()) {
			if (selectProduct.size() < 2 && !selectProduct.containsKey(entry.getKey())) {
				BufferedReader reader = new BufferedReader(
						new FileReader(new File(getServletContext().getRealPath("/") + "/webdata/DealMatches.txt")));
				line = reader.readLine();
				if (line == null || line.isEmpty()) {
					_content += "<h2 align='center'>No Offers Found</h2>";
					break;
				} else {
					do {
						if (line.toLowerCase().contains(entry.getKey().toLowerCase())) {
							_content += "<h5>" + line
									+ "</h5>"
									+ "<br/>";
							selectProduct.put(entry.getKey(), entry.getValue());
							System.out.println(line);
							break;
						}
					} while ((line = reader.readLine()) != null);
				}
			}
		}
		String _Heading = "" + "<center><h2 class='form-signin-heading'>"
				+ "Best Deal"
				+ "</h2></center>"
				+ "";

		String _Product = "";
	
		for (Map.Entry<String, Product> entry : selectProduct.entrySet()) {
			Product p = entry.getValue();


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
								: "class='availibility red'>Currently Unavailable")
						+ "</div>";

					if (p.getQuantity()>0) {
					_Product +=
						 "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$"
						+ p.getPrice() + "</h3>"
						+ "<form action='/AS5/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
						+ "<table>" + "<tr>" + "<td>"

						+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
						+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.getProductId()
						+ "'   type='submit'>Add to Cart</button>" + "</td>" + "</tr>" + "<tr>" + "<td>"
						+ "<a class='btn btn-link' role='button' href='/AS5/ViewProductServlet?productId="
						+ p.getProductId() + "'>View Product</a>" + "</td>" + "</tr>" + "</table>" + "</form>" + "</div>"
						+ "</div>" + "<hr class='draw-line'>";
					}
					else{
					_Product +=
						 "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$"
						+ p.getPrice() + "</h3>"
						+ "<table>" 
						+ "<tr>" + "<td>"
						+ "<button class='btn btn-outline-danger' name='cartProduct'>Add to Cart</button>" 
						+ "</td>" + "</tr>" 
						+ "<tr>" + "<td>"
						+ "<a class='btn btn-link' role='button' href='/AS5/ViewProductServlet?productId="
						+ p.getProductId() + "'>View Product</a>" 
						+ "</td>" + "</tr>" 
						+ "</table>"
						+ "</div>"
						+"</div>" + "<hr class='draw-line'>";
					}

		}

		return _header + _content
				+ _Heading
				+ _Product;
	}

	private String getHTMLContent() {
		String _Heading = "" + "<div id='body'>"
				+ "<section id='content'>"
				+ "";
		String msg = "<div class='container'>"
					+"<center>"
					+"<img class='img-fluid' width='80%'src='imagesNew/banner.jpg' alt='Buildings'/>"
					+"</center>"
					+"</div>"
					+"<h3><center>"
					+"BestDeal, an easy and 24 x 7 available electronics store for online shopping."
		 			+"</center></h3>"
		 			+"<br><br>";
		String _Content = "<article>"
				+ "<p>"
				+ msg
				+ "</p>"
				// + "<a href='/AS5/productstodb'>Add All Products</a>"
				+ "</article>";

		return _Heading + _Content;
	}

	private String getFooter() {

		return "</section>" + "</div>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
