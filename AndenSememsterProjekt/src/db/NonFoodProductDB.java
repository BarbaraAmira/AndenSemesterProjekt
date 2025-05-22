package db;

import java.sql.PreparedStatement;      
import java.sql.ResultSet;
import java.sql.SQLException;

import model.NonFoodProduct;


public class NonFoodProductDB implements NonFoodProductDBIF {
	private static final String findNonFoodProductByIdQ =
			"select productId, name, quanity, arrivalDate from foodProducts";
	private static final String updateNonFoodProductQ =
			"update foodProduct set name = ?, quantity = ?, arravialDate = ? where productId = ?";
	private PreparedStatement findFoodProductById, updateFoodProduct;
	
	public NonFoodProductDB() throws DataAccessException {
		try {
			findFoodProductById = DBConnection.getInstance().getConnection()
					.prepareStatement(findNonFoodProductByIdQ);
			updateFoodProduct = DBConnection.getInstance().getConnection()
					.prepareStatement(updateNonFoodProductQ);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}
	
	//Metode til at finde et nonfood produkt
	@Override
	public NonFoodProduct findNonFoodProductById(int productId) throws DataAccessException {
		try {
			findFoodProductById.setInt(1, productId);
			ResultSet rs = findFoodProductById.executeQuery();
			NonFoodProduct nfp = null;
			if(rs.next()) {
				nfp = buildObject(rs);
			}
			return nfp;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find product by id = " + productId);
		}
	}
	
	//Metode til at updatere et nonFood produkt
	@Override 
	public void updateNonFoodProduct(NonFoodProduct nfp) throws DataAccessException {
		final String name = nfp.getName();
		final int productId = nfp.getProductId();
		final int quantity = nfp.getQuantity();
		final String category = nfp.getCategory();
		try {
			updateFoodProduct.setString(1, name);
			updateFoodProduct.setInt(2, productId);
			updateFoodProduct.setInt(3, quantity);
			updateFoodProduct.setString(4, category);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not update product for productId" + productId);
		}
	}
	
	
	//buildObject
	private NonFoodProduct buildObject(ResultSet rs) throws SQLException {
		NonFoodProduct fp = new NonFoodProduct(
				rs.getString("name"),
				rs.getInt("foodProductId"),
				rs.getInt("quantity"),
				rs.getString("category")
				);
		return fp;
	}

	
	
	
	
	
}
