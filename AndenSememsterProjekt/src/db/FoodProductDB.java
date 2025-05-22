package db;

import java.sql.PreparedStatement;      
import java.sql.ResultSet;
import java.sql.SQLException;

import model.FoodProduct;

//Implementation af preparedStatements, med SQL scripts
public class FoodProductDB implements FoodProductDBIF {
	private static final String findFoodProductByIdQ =
			"select productId, name, quanity, arrivalDate from foodProducts";
	private static final String updateFoodProductQ =
			"update foodProduct set name = ?, quantity = ?, arravialDate = ? where productId = ?";
	private PreparedStatement findFoodProductById, updateFoodProduct;
	
	public FoodProductDB() throws DataAccessException {
		try {
			findFoodProductById = DBConnection.getInstance().getConnection()
					.prepareStatement(findFoodProductByIdQ);
			updateFoodProduct = DBConnection.getInstance().getConnection()
					.prepareStatement(updateFoodProductQ);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}
	
	//Metode til at finde et mad produkt
	@Override
	public FoodProduct findFoodProductById(int productId) throws DataAccessException {
		try {
			findFoodProductById.setInt(1, productId);
			ResultSet rs = findFoodProductById.executeQuery();
			FoodProduct fp = null;
			if(rs.next()) {
				fp = buildObject(rs);
			}
			return fp;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find product by id = " + productId);
		}
	}
	
	//Metode til at updatere et mad produkt
	@Override 
	public void updateFoodProduct(FoodProduct fp) throws DataAccessException {
		final String name = fp.getName();
		final int productId = fp.getProductId();
		final int quantity = fp.getQuantity();
		final int arrivalDate = fp.getArrivalDate();
		try {
			updateFoodProduct.setString(1, name);
			updateFoodProduct.setInt(2, productId);
			updateFoodProduct.setInt(3, quantity);
			updateFoodProduct.setInt(4, arrivalDate);
			updateFoodProduct.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not update product for productId" + productId);
		}
	}
	
	//buildObject
	private FoodProduct buildObject(ResultSet rs) throws SQLException {
		FoodProduct fp = new FoodProduct(
				rs.getString("name"),
				rs.getInt("foodProductId"),
				rs.getInt("quantity"),
				rs.getInt("arrivalDate")
				);
		return fp;
	}

	
	
	
	
	
}
