
public class Inventory {
	int id;
	String model;
	double price;
	int quantity;
	String type;
	double rebate;

	public Inventory(int id, String model, double price, int quantity, String type) {
		this.id = id;
		this.model = model;
		this.price = price;
		this.quantity = quantity;
		this.type = type;
	}



	public Inventory(int id, String model, double price, int quantity, String type, double rebate) {
		this.id = id;
		this.model = model;
		this.price = price;
		this.quantity = quantity;
		this.type = type;
		this.rebate = rebate;
	}

	
	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

}
