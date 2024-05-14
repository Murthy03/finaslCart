package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBCon.DB_Properties;
import DBCon.StoreDAO;
import Model.BillingDetails;
import Model.Items;

@WebServlet("/InsertCartServlet")

public class InsertCartServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<BillingDetails> d = new ArrayList<>();

		StoreDAO gap = null;
		HttpSession session = request.getSession(false);
		try {
			gap = new DB_Properties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Items> al = (ArrayList<Items>) session.getAttribute("Items");
		String proid = request.getParameter("proid");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		float floatValue = Float.parseFloat(price);
		String name = request.getParameter("name");
		if (proid != null && (quantity != null && price != null)) {
			Items it = new Items(Integer.parseInt(proid), Integer.parseInt(quantity), floatValue, name);
			al.add(it);
		}

		try {
			d = gap.shippingcharges(al);
			session.setAttribute("Items", al);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("hi" + d);
		session.setAttribute("bill", d);
	}
}