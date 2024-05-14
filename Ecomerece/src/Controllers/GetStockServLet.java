package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBCon.FactoryClass;
import DBCon.StoreDAO;

/**
 * Servlet implementation class GetStockServLet
 */
@WebServlet("/GetStockServLet")
public class GetStockServLet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StoreDAO obj;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int proid = Integer.parseInt(request.getParameter("productId"));
		try {

			obj = new FactoryClass().getObject();
			int stock = obj.getStock(proid);
			response.setContentType("text/plain");
			// Pass the stock value to the response
			response.getWriter().write(Integer.toString(stock));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
