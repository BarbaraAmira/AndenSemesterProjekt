package controller;

import java.util.List; 

import db.DataAccessException;
import db.CageDB;
import db.CageDBIF;
import model.Cage;
import model.FoodProduct;
import model.NonFoodProduct;

// En masse metode kald
public class CageCtr {
	private CageDBIF cageDB;
	
	public CageCtr() throws DataAccessException {
		this.cageDB = new CageDB();
	}
	
	public Cage findCageById(int cageId) throws DataAccessException {
		return cageDB.findCageById(cageId);
	}
	
	public void updateCage (Cage c) throws DataAccessException {
		cageDB.updateCage(c);
	}
	
	public void addFoodProduct(Cage c, FoodProduct fp) throws DataAccessException{
		cageDB.addFoodProduct(c, fp);
	}
	
	public void addNonFoodProduct(Cage c, NonFoodProduct nfp) throws DataAccessException{
		cageDB.addNonFoodProduct(c, nfp);
	}
	
	public List<String> getAllCageIds() throws DataAccessException {
		return cageDB.getAllCageIds();
	}
	
	public String getProductNameById(int productId) throws DataAccessException {
	    return cageDB.getProductNameById(productId);
	}
	
	public void removeProductFromCage(int cageId, String productType) throws DataAccessException{
		cageDB.removeProductFromCage(cageId, productType);
	}
	
	public void moveProductToAnotherCage(int fromCageId, int toCageId, String productType) throws DataAccessException{
		cageDB.moveProductToAnotherCage(fromCageId, toCageId, productType);
	}
	
	public List<FoodProduct> getFoodProductsInCage(int cageId) throws DataAccessException {
	    return cageDB.getFoodProductsInCage(cageId);
	}

	public List<NonFoodProduct> getNonFoodProductsInCage(int cageId) throws DataAccessException {
	    return cageDB.getNonFoodProductsInCage(cageId);
	}
	
}
