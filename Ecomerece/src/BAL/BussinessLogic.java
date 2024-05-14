package BAL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBCon.FactoryClass;
import DBCon.StoreDAO;
import Model.BillingDetails;
import Model.Items;

public class BussinessLogic {
	private double total;
	private StoreDAO obj;
	FactoryClass fc;

	public BussinessLogic() throws Exception {
		fc = new FactoryClass();
		obj = fc.getObject();
	}

	private double gettotal(List<Items> list) {
		for (Items i : list) {
			total += i.getPrice() * i.getQuantity();

		}
		return total;
	}

	public ArrayList<BillingDetails> billCalculation(List<Items> list) throws SQLException {
		ArrayList<BillingDetails> bill = new ArrayList<BillingDetails>();
		double priceratio = 0;
		double pricetoquantity = 0;
		double gst = 0;
		double sc_individual_gst = 0;
		double prodwithoutgst = 0;
		double ingst = 0;
		double count = gettotal(list);
		double total_shipping_charges = obj.getshippingcharges(count);
		double sc_individual = 0;
		double total_individual = 0;
		for (Items i : list) {
			gst = obj.getgst(i.getProid());
			// prod inclusive gst calculation
			prodwithoutgst = (i.getPrice() / (1 + (gst / 100)));
			ingst = i.getPrice() - prodwithoutgst;
			// individual shipping charges
			pricetoquantity = i.getPrice() * i.getQuantity();
			priceratio = pricetoquantity / count;
			sc_individual = total_shipping_charges * priceratio;
			sc_individual_gst = sc_individual * (gst / 100);
			total_individual = pricetoquantity + sc_individual + sc_individual_gst;
			bill.add(new BillingDetails(i.getProid(), i.getName(), i.getPrice(), i.getQuantity(),
					Math.round(prodwithoutgst), Math.round(ingst), Math.round(pricetoquantity),
					Math.round(sc_individual), Math.round(sc_individual_gst), Math.round(total_individual)));
		}
		return bill;
	}

}
