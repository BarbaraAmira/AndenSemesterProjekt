package db;

import java.util.List;

import model.Cage;
import model.FoodProduct;
import model.NonFoodProduct;

public interface CageDBIF {

	//Metode kald fra CageDB
	Cage findCageById(int cageId) throws DataAccessException;

	void updateCage(Cage c) throws DataAccessException;

	void addFoodProduct(Cage c, FoodProduct fp) throws DataAccessException;

	void addNonFoodProduct(Cage c, NonFoodProduct nfp) throws DataAccessException;

	List<String> getAllCageIds() throws DataAccessException;

	String getProductNameById(int productId) throws DataAccessException;

	void removeProductFromCage(int cageId, String productType) throws DataAccessException;

	void moveProductToAnotherCage(int fromCageId, int toCageId, String productType) throws DataAccessException;

	List<FoodProduct> getFoodProductsInCage(int cageId) throws DataAccessException;

	List<NonFoodProduct> getNonFoodProductsInCage(int cageId) throws DataAccessException;

}
