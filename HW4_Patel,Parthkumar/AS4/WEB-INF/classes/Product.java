import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable{
	private int productId;
	private String model;
	private double price;
	private String image;
	private String manufacturer;
	private String condition;
	private double discount;
	private int quantity;
	private String type;
	private ArrayList<Integer> accessories=null;

	public Product() {
		this.accessories = new ArrayList<Integer>();
	}

	public Product(int productId, String model, double price, String image, String manufacturer, String condition,
			double discount, int quantity, String type, ArrayList<Integer> accessories) {
		this.productId = productId;
		this.model = model;
		this.price = price;
		this.image = image;
		this.manufacturer = manufacturer;
		this.condition = condition;
		this.discount = discount;
		this.quantity = quantity;
		this.type = type;
		this.accessories = accessories;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Integer> getAccessories() {
		return accessories;
	}

	public void setAccessories(ArrayList<Integer> accessories) {
		this.accessories = accessories;
	}

	public void updateProduct(Product updateProduct) {
		this.model = updateProduct.model;
		this.price = updateProduct.price;
		this.image = updateProduct.image;
		this.manufacturer = updateProduct.manufacturer;
		this.condition = updateProduct.condition;
		this.discount = updateProduct.discount;
		this.quantity = updateProduct.quantity;
		this.type = updateProduct.type;


	}


}
