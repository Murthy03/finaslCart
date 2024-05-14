package Controllers;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import DBCon.FactoryClass;
import DBCon.StoreDAO;
import Model.Items;
import Model.Products;

@WebServlet("/AllProductsServlet")
public class AllProductsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		StoreDAO gap;
		FactoryClass fc = new FactoryClass();
		try {
			ArrayList<Items> itemList = new ArrayList();
			HttpSession hs = req.getSession();
			hs.setAttribute("Items", itemList);
			int pgno = Integer.parseInt(req.getParameter("page"));
			int pgsize = 6;
			gap = fc.getObject(pgno, pgsize);
			ArrayList<Products> products = null;
			ArrayList<ArrayList<String>> ars = new ArrayList<>();
			JSONObject ob = new JSONObject();
			products = gap.getAllProducts();
			for (Products it : products) {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(it.getProduct_id() + "");
				temp.add(it.getProduct_name());
				temp.add(it.getProduct_price() + "");
				temp.add(it.getProduct_image());
				temp.add(it.getProduct_catid() + "");
				ars.add(temp);
			}
			ob.put("AllProducts", ars);
			res.getWriter().write(ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
