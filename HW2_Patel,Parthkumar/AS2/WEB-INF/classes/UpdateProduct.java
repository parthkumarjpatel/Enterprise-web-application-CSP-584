import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateProduct extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}
		String src = request.getParameter("Referer");
		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");
		String findProduct = request.getParameter("findProduct");
		String updateProduct = request.getParameter("UpdateProduct");
		String productId = request.getParameter("productId");
		String deleteProduct = request.getParameter("DeleteProduct");
		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {
			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		String content = getHTMLContent();
		String contentFooter = getHTMLFooter();
		out.print(content);
		if (findProduct != null) {
			if (productId != null) {
//				Product  p =  SaxParserReader.findProductById(Integer.parseInt(productId));
				Product  p =  MySqlDataStoreUtilities.findProductById(Integer.parseInt(productId));
				if (p != null) {
					out.print(getProductView(p));
				} else {
					out.println("<center><h6>Mysql is not running or No Such Product Available.</h6></center>");
				}
			}
			out.print("");
		} else if (updateProduct != null) {
			updateProduct(request, response, src);
			//response.sendRedirect("/AS2/");
		} else if (deleteProduct != null) {
			int id = Integer.parseInt(deleteProduct);
			//SaxParserReader.deleteProduct(id);
			MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
			int resultcode= msdsq.deleteProduct(id);
			if (resultcode == 1) {
				out.println("<center><h6>Product Catalog Updated.</h6></center>");
			}else {
				out.println("<center><h6>Mysql is not running.Error occured in the transaction. Please try again later.</h6></center>");
			}

		}
		out.print(contentFooter);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response, String src) throws IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
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
		p.setImage(img);
		p.setManufacturer(manufacturer);
		p.setCondition(condition);
		p.setDiscount(discount);
		p.setQuantity(quantity);
		p.setType(type);

		//SaxParserReader.updateProduct(p);
		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
		int resultcode = msdsq.updateProduct(p);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if(resultcode ==1) {
				out.println("<center><h6>Product details is updated.</h6></center>");
		}else {
				out.println("<center><h6>Mysql is not running. Product details is not updated.</h6></center>");
		}
	}

	private String getProductView(Product p) {
		return "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/AS2/UpdateProduct?UpdateProduct=true' >"
				+ "<center><h2 class='form-signin-heading'>Update Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='productId' class='sr-only'>Product Id</label>"
				+ "<input type='hidden' name='productId' value='" + p.getProductId() + "'>"
				 + "<input name='' value='"+p.getProductId()+"' disabled type='text' id='productId' class='form-control' placeholder='productId' required='' autofocus=''>"
				 + ""
				 + ""
				 + "<label for='model' class='sr-only'>Model Name</label>"
				 + "<input name='model' value='"+p.getModel()+"' type='text' id='model' class='form-control' placeholder='Model Name' required=''>"
				 + ""
				 + ""
				 + "<label for='price' class='sr-only'>Price</label>"
				 + "<input name='price' value='"+p.getPrice()+"' type='text' id='price' class='form-control' placeholder='Price' required=''>"
				 + ""
				 + ""
				 + "<label for='img' class='sr-only'>Image</label>"
				 + "Image:&nbsp <input name='img' value='"+p.getImage()+"' type='text' id='mno' class='form-control' required='' >"
				 + ""
				 + ""
				 + "<label for='manufacturer' class='sr-only'>Manufacturer</label>"
				 + "<input name='manufacturer' value='"+p.getManufacturer()+"' type='text' id='manufacturer' class='form-control' placeholder='Manufactured By' required=''>"
				 + ""
				 + ""
				 + "<label for='condition' class='sr-only'>Condition</label>"
				 + "<input name='condition' value='"+p.getCondition()+"' type='text' id='condition' class='form-control' placeholder='Condition' required=''>"
				 + ""
				 + ""
				 + "<label for='discount' class='sr-only'>Discount</label>"
				 + "<input name='discount' value='"+p.getDiscount()+"' type='text' id='discount' class='form-control'  placeholder='Discount' required=''>"
				 + ""
				 + ""
				 + "<label for='quantity' class='sr-only'>Quantity</label>"
				 + "<input name='quantity' value='"+p.getQuantity()+"' type='text' id='quantity' class='form-control' placeholder='Quantity' required=''>"


				 + "<div class='form-group'>"
				 + "<label for='role' class='sr-only'>Role</label>"
				 + "<div class='col-xs-5 selectContainer'>"
				 + "<select class='form-control' name='type' id='type'>"

				 + "<option value=''>Choose a type</option>"
				 + (p.getType().equals("TV")?"<option value='TV' selected='selected'>TV</option>":"<option value='TV'>TV</option>")
				 + (p.getType().equals("SoundSystems")?"<option value='SoundSystems' selected='selected'>SoundSystems</option>":"<option value='SoundSystems'>SoundSystems</option>")
				 + (p.getType().equals("Phones")?"<option value='Phones' selected='selected'>Phones</option>":"<option value='Phones'>Phones</option>")
				 + (p.getType().equals("Laptops")?"<option value='Laptops' selected='selected'>Laptops</option>":"<option value='Laptops'>Laptops</option>")
				 + (p.getType().equals("VoiceAssistant")?"<option value='VoiceAssistant' selected='selected'>VoiceAssistant</option>":"<option value='VoiceAssistant'>VoiceAssistant</option>")
				 + (p.getType().equals("FitnessWatches")?"<option value='FitnessWatches' selected='selected'>FitnessWatches</option>":"<option value='FitnessWatches'>FitnessWatches</option>")
				 + (p.getType().equals("SmartWatches")?"<option value='SmartWatches' selected='selected'>SmartWatches</option>":"<option value='SmartWatches'>SmartWatches</option>")
				 + (p.getType().equals("Headphones")?"<option value='Headphones' selected='selected'>Headphones</option>":"<option value='Headphones'>Headphones</option>")
				 + (p.getType().equals("WirelessPlan")?"<option value='WirelessPlan' selected='selected'>WirelessPlan</option>":"<option value='WirelessPlan'>WirelessPlan</option>")
				 + "</select>"
				 + "</div>"
				 + "</div>"
				+ ""
				 + ""
				 + "<button class='btn btn-lg btn-outline-dark' type='submit'>Update Product</button>"
				 + "<a class='btn btn-lg btn-outline-dark' role='button' href='/AS2/UpdateProduct?DeleteProduct="+p.getProductId()+"' >Delete Product</a>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>";
	}

	private String getHTMLContent() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/AS2/UpdateProduct?findProduct=true'>"
				+ "<center><h2 class='form-signin-heading'>View Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='productId' class='sr-only'>Product ID</label>"
				 + "<input name='productId' type='text' id='productId' class='form-control'placeholder='Product ID' required='' autofocus=''>"
				 + ""
				 + ""
				+ "<button class='btn btn-lg btn-outline-dark' type='submit'>View</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"

				;
	}

	private String getHTMLFooter() {

		return  "</section>"
				+ "</div>";
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
