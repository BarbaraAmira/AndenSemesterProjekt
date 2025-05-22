package model;

public class NonFoodProduct extends Product {
	private String category;
	
	public NonFoodProduct(String name, int productId, int quantity, String category) {
		super (name, productId, quantity);
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
}
