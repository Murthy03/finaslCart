package DBCon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Items;
import Model.Products;

public interface StoreDAO {

	ArrayList<String> getAllCategories();

	ArrayList<Products> getAllProducts() throws SQLException;

	ArrayList<Products> getAllProductsId(String id) throws SQLException;

	ArrayList<Products> getAllProductSort(String catid) throws SQLException;

	ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException;

	void createOrder(int i, List<Items> i2) throws SQLException;

	public List<Double> calculateGST(List<Items> list) throws SQLException;

	boolean[] checkpincode(int pincode, List<Integer> plist) throws SQLException;

	double getgst(int proid) throws SQLException;

	double getshippingcharges(double count) throws SQLException;

	double discountcoupon(double amount, double couponno) throws SQLException;

	int getStock(int proid) throws SQLException;

	void updateStock(int proid, int quantity) throws SQLException;
}
