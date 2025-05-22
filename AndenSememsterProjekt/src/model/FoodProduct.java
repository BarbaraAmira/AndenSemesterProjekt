package model;

public class FoodProduct extends Product{
	private int arrivalDate;
	
	public FoodProduct(String name, int productId, int quantity, int arrivalDate) {
		super (name, productId, quantity);
		this.arrivalDate = arrivalDate;
		
	}
	
	public int getArrivalDate() {
		return arrivalDate;
	}
	
	public void setArrivalDate(int arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
}
