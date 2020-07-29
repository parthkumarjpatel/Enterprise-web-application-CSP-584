
public class DailySale {
	String date;
	int quantity;
	double price;

	public DailySale(String date, int quantity, double price) {
		this.date = date;
		this.quantity = quantity;
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
