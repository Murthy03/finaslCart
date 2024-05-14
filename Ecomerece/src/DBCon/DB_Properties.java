package DBCon;

import java.io.FileInputStream;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Model.Items;
import Model.Products;

public class DB_Properties implements StoreDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<String> categories;
	private ArrayList<Products> products;
	private CallableStatement cs;
	Properties p;
	int pgeNo;
	int pgSize;

	public DB_Properties() throws Exception {
		try {
			FileInputStream fi = new FileInputStream("E:\\New folder\\eclipse\\Ecomerece\\src\\DBCon\\DB.properties");
			p = new Properties();
			p.load(fi);
			String d = p.getProperty("driver");
			Class.forName(d);
			String url = p.getProperty("url");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			con = DriverManager.getConnection(url, username, password);
			categories = new ArrayList<>();
			products = new ArrayList<>();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public DB_Properties(int pgno, int pgsize) throws Exception {
		this();
		pgeNo = pgno;
		pgSize = pgsize;

	}

	public ArrayList<String> getAllCategories() {
		try {
			cs = con.prepareCall("{call getAllCategories()}");
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				categories.add(rs.getInt(1) + "");
				categories.add(rs.getString(2));
			}
			rs.close();
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public ArrayList<Products> getAllProducts() throws SQLException {

		cs = con.prepareCall("{call getAllProd(?,?)}");
		cs.setInt(1, pgeNo);
		cs.setInt(2, pgSize);
		ResultSet rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductsId(String id) throws SQLException {

		cs = con.prepareCall("{call getProdByCat(?)}");
		cs.setInt(1, Integer.parseInt(id));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		return products;
	}

	public ArrayList<Products> getProductsFromResultSet(ResultSet rs) {
		try {
			while (rs.next()) {
				Products p = new Products();
				p.setProduct_id(rs.getInt("proid"));
				p.setProduct_name(rs.getString("name"));
				p.setProduct_price(rs.getInt("price"));
				p.setProduct_image(rs.getString("imgpath"));
				p.setProduct_catid(rs.getInt("catid"));
				products.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductSort(String sortid) throws SQLException {
		cs = con.prepareCall("{call getAllProdSort(?)}");
		cs.setInt(1, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		return products;
	}

	@Override
	public ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException {
		cs = con.prepareCall("{call getCatProdSort(?,?)}");
		cs.setInt(1, Integer.parseInt(catid));
		cs.setInt(2, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		return products;
	}

	private long calculateTotalPrice(List<Items> list) throws SQLException {
		long totalPrice = 0;
		cs = con.prepareCall("{?=call getgst(?)}");

		for (Items i : list) {
			cs.registerOutParameter(1, Types.DECIMAL);
			cs.setInt(2, i.getProid());
			cs.execute();
			double gst = cs.getBigDecimal(1).doubleValue();
			double gst_price = i.getPrice() - (i.getPrice() * (gst / 100));
			totalPrice += gst_price * i.getQuantity();
		}
		return totalPrice;
	}

	public List<Double> calculateGST(List<Items> list) throws SQLException {
		ArrayList<Double> gsts = new ArrayList<>();
		cs = con.prepareCall("{?=call getgst(?)}");
		for (Items i : list) {
			cs.registerOutParameter(1, Types.DECIMAL);
			cs.setInt(2, i.getProid());
			cs.execute();
			double gst = cs.getBigDecimal(1).doubleValue();
			double gst_price = i.getPrice() - (i.getPrice() * (gst / 100));
			gsts.add(gst_price);
		}
		return gsts;
	}

	public void createOrder(int cust, List<Items> list) throws SQLException {
		String insertOrderQuery = "INSERT INTO orders225 (order_date, price, custid) VALUES (CURRENT_DATE, ?, ?)RETURNING orderid";
		ps = con.prepareStatement(insertOrderQuery);
		ps.setLong(1, calculateTotalPrice(list));
		ps.setInt(2, cust);
		rs = ps.executeQuery();

		int orderId = -1;
		if (rs.next()) {
			orderId = rs.getInt(1);
		}
		String insertOrderProductQuery = "INSERT INTO orderproducts225 (orderid, prodid, quantity, price) VALUES (?, ?,?, ?)";
		ps = con.prepareStatement(insertOrderProductQuery);
		for (Items i : list) {
			ps.setInt(1, orderId);
			ps.setInt(2, i.getProid());
			ps.setInt(3, i.getQuantity()); // quantity
			ps.setFloat(4, i.getPrice()); // price
			ps.addBatch();
		}
		ps.executeBatch();
	}

	public double getgst(int proid) throws SQLException {
		cs = con.prepareCall("{?=call getgst(?)}");
		cs.registerOutParameter(1, Types.DECIMAL);
		cs.setInt(2, proid);
		cs.execute();
		double gst = cs.getBigDecimal(1).doubleValue();
		return gst;
	}

	public double getshippingcharges(double count) throws SQLException {
		double sc = 0;
		cs = con.prepareCall("{?=call getshipping(?)}");
		cs.registerOutParameter(1, Types.NUMERIC);
		cs.setInt(2, (int) count);
		cs.execute();
		sc = cs.getBigDecimal(1).doubleValue();
		return sc;
	}

	public double discountcoupon(double amount, double couponcode) throws SQLException {
		cs = con.prepareCall("{?=call getdiscount(?,?)}");
		cs.registerOutParameter(1, Types.NUMERIC);
		cs.setDouble(2, couponcode);
		cs.setDouble(3, amount);
		cs.execute();
		double discount = cs.getBigDecimal(1).doubleValue();
		return discount;

	}

	public boolean[] checkpincode(int pincode, List<Integer> plist) throws SQLException {
		boolean[] serviceabilityList = null;
		String query = "{ ? = call check_product_serviceability(?, ?) }";
		cs = con.prepareCall(query);
		cs.registerOutParameter(1, Types.ARRAY);
		Integer[] proidsArray = plist.toArray(new Integer[0]);
		Object[] proidsObjArray = (Object[]) proidsArray;
		cs.setInt(2, pincode);
		Array proidsSqlArray = con.createArrayOf("INTEGER", proidsObjArray);
		cs.setArray(3, proidsSqlArray); // Use proidsSqlArray here instead of proidsArray
		cs.execute();
		Array resultArray = cs.getArray(1);
		Boolean[] objectArray = (Boolean[]) resultArray.getArray();
		boolean[] primitiveArray = new boolean[objectArray.length];
		for (int i = 0; i < objectArray.length; i++) {
			primitiveArray[i] = objectArray[i].booleanValue();
		}
		System.out.println();
		return primitiveArray;
	}

	public int getStock(int proid) throws SQLException {
		int stock;
		cs = con.prepareCall("{? = call get_stock_by_proid(?)}");
		cs.registerOutParameter(1, Types.INTEGER);
		cs.setInt(2, proid);
		cs.execute();
		stock = cs.getInt(1);
		return stock;
	}

	public void updateStock(int proid, int quantity) throws SQLException {
		cs = con.prepareCall("{call update_stock225(?,?)}");
		cs.setInt(1, proid);
		cs.setInt(2, quantity);
		cs.execute();
	}
}