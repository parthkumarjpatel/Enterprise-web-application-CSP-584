import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddProduct extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}


		response.setContentType("text/html");
		String addProdcut = request.getParameter("addProduct");
		if (addProdcut != null) {
			addProduct(request, response);
		}
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		String content = getLoginHTML();
		out.print(content);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) {

		int productId = Integer.parseInt(request.getParameter("productId").trim());
		String model = request.getParameter("model");
		double price = Double.parseDouble(request.getParameter("price"));
		String img = request.getParameter("img");
		String manufacturer = request.getParameter("manufacturer");
		String condition = request.getParameter("condition");
		double discount = Double.parseDouble(request.getParameter("discount"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String type = request.getParameter("type");

		Product p = new Product();
		p.setProductId(productId);
		p.setModel(model);
		p.setPrice(price);
		p.setImage("imagesNew/"+img);
		p.setManufacturer(manufacturer);
		p.setCondition(condition);
		p.setDiscount(discount);
		p.setQuantity(quantity);
		p.setType(type);

		SaxParserReader.addProduct(p);

	}

	private String getLoginHTML() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/AS1/AddProduct?addProduct=true'>"
				+ "<center><h2 class='form-signin-heading'>Add Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='productId' class='sr-only'>Product Id</label>"
				 + "<input name='productId' type='text' id='productId' class='form-control'placeholder='productId' required='' autofocus=''>"
				 + ""
				 + ""
				 + "<label for='model' class='sr-only'>Model Name</label>"
				 + "<input name='model' type='text' id='model' class='form-control'placeholder='Model Name' required=''>"
				 + ""
				 + ""
				 + "<label for='price' class='sr-only'>Price</label>"
				 + "<input name='price' type='text' id='price' class='form-control'placeholder='Price' required=''>"
				 + ""
				 + ""
				 + "<label for='img' class='sr-only'>Image</label>"
				 + "Image:&nbsp <input name='img' type='file' id='mno' class='form-control' required='' >"
				 + ""
				 + ""
				 + "<label for='manufacturer' class='sr-only'>Manufacturer</label>"
				 + "<input name='manufacturer' type='text' id='manufacturer' class='form-control'placeholder='Manufactured By' required=''>"
				 + ""
				 + ""
				 + "<label for='condition' class='sr-only'>Condition</label>"
				 + "<input name='condition' type='text' id='condition' class='form-control'placeholder='Condition' required=''>"
				 + ""
				 + ""
				 + "<label for='discount' class='sr-only'>Discount</label>"
				 + "<input name='discount' type='text' id='discount' class='form-control'placeholder='Discount' required=''>"
				 + ""
				 + ""
				 + "<label for='quantity' class='sr-only'>Quantity</label>"
				 + "<input name='quantity' type='text' id='quantity' class='form-control'placeholder='Quantity' required=''>"
				 + ""
				 + ""
				 + "<div class='form-group'>"
				 + "<label for='role' class='sr-only'>Role</label>"
				 + "<div class='col-xs-5 selectContainer'>"
				 + "<select class='form-control' name='type' id='type'>"
				 + "<option value=''>Choose a type</option>"
				 + "<option value='TV'>TV</option>"
				 + "<option value='SoundSystems'>SoundSystems</option>"
				 + "<option value='Phones'>Phones</option>"
				 + "<option value='Laptops'>Laptops</option>"
				 + "<option value='VoiceAssistant'>VoiceAssistant</option>"
				 + "<option value='FitnessWatches'>FitnessWatches</option>"
				 + "<option value='SmartWatches'>SmartWatches</option>"
				 + "<option value='Headphones'>Headphones</option>"
				 + "<option value='WirelessPlan'>WirelessPlan</option>"
				 + "</select>"
				 + "</div>"
				 + "</div>"
				 + ""
				 + ""
				 + "<button class='btn btn-lg btn-outline-dark' type='submit'>Add Product</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"
				+ "</section>"
				+ "</div>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
