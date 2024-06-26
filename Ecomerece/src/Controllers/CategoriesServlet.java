package Controllers;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DBCon.FactoryClass;
import DBCon.StoreDAO;

@WebServlet("/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		StoreDAO gpbci;
		FactoryClass fc = new FactoryClass();
		try {
			gpbci = fc.getObject();
			ArrayList<String> arrc = gpbci.getAllCategories();
			JSONObject ob = new JSONObject();
			for (int i = 0; i < arrc.size(); i += 2) {
				int j = Integer.parseInt(arrc.get(i));
				ob.put(arrc.get(i + 1), j);
			}
			res.getWriter().write(ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
