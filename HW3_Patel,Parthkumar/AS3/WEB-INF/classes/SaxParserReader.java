import java.io.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserReader extends DefaultHandler {
	public static HashMap<String, ArrayList<Product>> productData;
	private static ArrayList<Product> products;
	private Product product;
	private String productXML;
	private String elementValue;
	private Accessory accessory;

	public SaxParserReader(String productXML) {
		this.productXML = productXML;
		products = new ArrayList<Product>();
		productData = new HashMap<String, ArrayList<Product>>();
	}

	public void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(productXML, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement(String s1, String s2, String element, Attributes attr) throws SAXException {
		if (element.equalsIgnoreCase("product")) {
			product = null;
			product = new Product();
			product.setProductId(Integer.parseInt(attr.getValue("productId")));
		} else if (element.equalsIgnoreCase("accessory")) {
			accessory = null;
			accessory = new Accessory();
			accessory.setProductId(Integer.parseInt(attr.getValue("accessoryId")));
		}
	}

	@Override
	public void endElement(String s1, String s2, String element) throws SAXException {
		if (element.equalsIgnoreCase("product")) {
			products.add(product);
			product = null;
		} else if (element.equalsIgnoreCase("model")) {
			product.setModel(elementValue);
		} else if (element.equalsIgnoreCase("price")) {
			product.setPrice(Double.parseDouble(elementValue));
		} else if (element.equalsIgnoreCase("image")) {
			product.setImage(elementValue);
		} else if (element.equalsIgnoreCase("manufacturer")) {
			product.setManufacturer(elementValue);
		} else if (element.equalsIgnoreCase("condition")) {
			product.setCondition(elementValue);
		} else if (element.equalsIgnoreCase("discount")) {
			product.setDiscount(Double.parseDouble(elementValue));
		} else if (element.equalsIgnoreCase("quantity")) {
			product.setQuantity(Integer.parseInt(elementValue));
		} else if (element.equalsIgnoreCase("type")) {
			product.setType(elementValue);
		} else if (element.equalsIgnoreCase("accessoryid")) {
			product.getAccessories().add(Integer.parseInt(elementValue));
		} else if (element.equalsIgnoreCase("accessory")) {
			products.add(accessory);
		} else if (element.equalsIgnoreCase("amodel")) {
			accessory.setModel(elementValue);
		} else if (element.equalsIgnoreCase("aprice")) {
			accessory.setPrice(Double.parseDouble(elementValue));
		} else if (element.equalsIgnoreCase("aimage")) {
			accessory.setImage(elementValue);
		} else if (element.equalsIgnoreCase("amanufacturer")) {
			accessory.setManufacturer(elementValue);
		} else if (element.equalsIgnoreCase("atype")) {
			accessory.setType(elementValue);
		}

	}

	@Override
	public void characters(char[] data, int start, int length) throws SAXException {
		elementValue = new String(data, start, length);
	}

	public static HashMap<String, ArrayList<Product>> retriveAllProducts() {
		final String key = "allProducts";
		productData.put(key, products);
		return productData;
	}

	public static void addProduct(Product p) {
		products.add(p);
	}

	public static HashMap<String, ArrayList<Product>> filterByType(String type) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getType().equalsIgnoreCase(type)) {
				filterProduct.add(p);
			}
		}
		resultData.put(type, filterProduct);

		return resultData;
	}

	public static Product fetchProductById(int id) {
		Product resultProdut = null;
		for (Product p : products) {
			if (p.getProductId() == id) {
				resultProdut = p;
				break;
			}
		}
		return resultProdut;
	}

	public static HashMap<String, ArrayList<Product>> filterByManufacturer(String type, String manufacturer) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getManufacturer().equalsIgnoreCase(manufacturer) && p.getType().equalsIgnoreCase(type)) {
				filterProduct.add(p);
			}
		}
		resultData.put(manufacturer, filterProduct);
		return resultData;
	}

	public static HashMap<String, Product> filterByID(String productId) {
		HashMap<String, Product> result = new HashMap<String, Product>();
		Product p = SaxParserReader.findProductById(Integer.parseInt(productId));
		result.put(p.getType(), p);
		return result;
	}

	public static Product findProductById(int parseInt) {
		for (Product p : products) {
			if (p.getProductId() == parseInt) {
				return p;
			}
		}
		return null;
	}

	public static void updateProduct(Product updateProduct) {
		for (Product p : products) {
			if (p.getProductId() == updateProduct.getProductId()) {
				p.updateProduct(updateProduct);
				break;
			}
		}
	}

	public static void deleteProduct(int id) {
		ArrayList<Product> updatedCatalog = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getProductId() != id) {
				updatedCatalog.add(p);
			}
		}
		products = updatedCatalog;
	}

	public static void main(String[] args) {
		SaxParserReader spr = new SaxParserReader(
				"C:/apache-tomcat-7.0.34/webapps/AS3/webdata/ProductCatalogNew.xml");
		spr.parseDocument();

		HashMap<String, ArrayList<Product>> prod = SaxParserReader.filterByManufacturer("Phone", "Samsung");

		for (ArrayList<Product> e : prod.values()) {
			for (Product p : e) {
				// System.out.println(p.toString());
			}
		}
	}

	public static HashMap<Integer, ArrayList<Accessory>> findAccessoryByProductID(String productId) {
		HashMap<Integer, ArrayList<Accessory>> accessoryList = new HashMap<Integer, ArrayList<Accessory>>();

		Product p = SaxParserReader.findProductById(Integer.parseInt(productId));
		if (p != null) {
			ArrayList<Integer> accessoryListIds =p.getAccessories();

			HashMap<String, ArrayList<Product>> allAccessories = SaxParserReader.filterByType("accessory");
			ArrayList<Accessory> filterAccessory = new ArrayList<Accessory>();

			for(Map.Entry<String, ArrayList<Product>> entry:allAccessories.entrySet()) {
				for(Product p1:entry.getValue()) {
					if(p1 instanceof Accessory && accessoryListIds.contains(p1.getProductId())) {
						filterAccessory.add((Accessory) p1);
					}
				}
			}
			accessoryList.put(filterAccessory.size(), filterAccessory);
		}
		return accessoryList;
	}

}
