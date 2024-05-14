package Controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBCon.FactoryClass;
import DBCon.StoreDAO;

@WebServlet("/DiscountServlet")
public class DiscountServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StoreDAO gap;
		try {
			gap = new FactoryClass().getObject();
			double discount = 0;
			double amount = Double.parseDouble(req.getParameter("amount"));
			double couponno = Double.parseDouble(req.getParameter("coupon"));
			discount = gap.discountcoupon(amount, couponno);
			resp.setContentType("text/plain");
			PrintWriter out = resp.getWriter();

			out.print(discount);

			out.close();
		} catch (Exception e) {
		}
	}
}