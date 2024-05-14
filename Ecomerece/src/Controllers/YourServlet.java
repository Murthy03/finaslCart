package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Model.Items;

@WebServlet("/YourServlet")
public class YourServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Read the JSON string from the request body
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String jsonString = sb.toString();
		System.out.println("Received JSON data: " + jsonString);
		String pincode = null;
		String itemsJson = null;
		try {
			String[] parts = jsonString.split("&");
			for (String part : parts) {
				String[] keyValue = part.split("=");
				if ("pin".equals(keyValue[0])) {
					pincode = URLDecoder.decode(keyValue[1], "UTF-8");
				} else if ("items".equals(keyValue[0])) {
					itemsJson = URLDecoder.decode(keyValue[1], "UTF-8");
				}
			}
			Gson gson = new Gson();
			Items[] itemsArray = gson.fromJson(itemsJson, Items[].class);
			for (Items item : itemsArray) {

				System.out.println("Item Name: " + item.getName());
				System.out.println("Item Price: " + item.getPrice());
				System.out.println("Item Quantity: " + item.getQuantity());
			}
			System.out.println("Pin Code: " + pincode);
			request.setAttribute("pin", pincode);
			List<Integer> proidList = new ArrayList<>();
			for (Items item : itemsArray) {
				proidList.add(item.getProid());
				System.out.println(item.getProid());
			}
			request.setAttribute("proidList", proidList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/CheckPincodeServlet");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// Handle any potential exceptions
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error processing request");
		}
	}

}
