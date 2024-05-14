package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DBCon.FactoryClass;
import DBCon.StoreDAO;

@WebServlet("/CheckPincodeServlet")
public class CheckPincodeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StoreDAO gap;
		int piCode = Integer.parseInt((String) request.getAttribute("pin"));
		List<Integer> plist = (List<Integer>) request.getAttribute("proidList");
		System.out.println(piCode);
		System.out.println(plist);
		boolean[] res;
		try {
			gap = new FactoryClass().getObject();
			res = gap.checkpincode(piCode, plist);
			Gson gson = new Gson();
			String json = gson.toJson(res);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} catch (Exception e) {
			System.out.println(e);
		}
		response.setContentType("text/plain");
		// Create a PrintWriter object to write response
		PrintWriter out = response.getWriter();
		// out.print(state);
		out.close();
	}
}