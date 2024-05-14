<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.BillingDetails" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
    <link rel="stylesheet" href="../css/checkout.css">
    <!-- Latest compiled and minified CSS -->
    <!-- Bootstrap CDN link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body >

  <div class="container">
        <h2 class="text-center">Checkout</h2>
        <div class="text-center">
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>SNo</th>
                            <th>Item Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total Base Price</th>
                            <th>Shipping Charge</th>
                            <th>GST</th>
                            <th>Total Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- This section will be populated with data from the server -->
                        <% 
                        HttpSession sessionObj = request.getSession(false);
                        List<BillingDetails> bill = (List<BillingDetails>) sessionObj.getAttribute("bill");
                        System.out.println(bill);
                        if (bill != null) {
                            int sno = 1;
                            for (BillingDetails detail : bill) {
                        %>
                        <tr>
                            <td><%= sno++ %></td>
                            <td><%= detail.getProdname() %></td>
                            <td><%= detail.getQuantity() %></td>
                            <td><%= detail.getPrice() %></td>
                            <td><%= detail.getTotalbaseprice() %></td>
                            <td><%= detail.getShipchg() %></td>
                            <td><%= detail.getGst() %></td>
                            <td><%= detail.getFinalprice() %></td>
                        </tr>
                        <% 
                            }
                        }
                        %>
                    </tbody>
                </table>
            </div>
            <button id="rzp-button1" class="btn btn-primary">Pay Now</button>
        </div>
    </div>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script>

var options = {
    "key": "rzp_test_igiSdOA5LIjesg", // Enter the Key ID generated from the Dashboard
    "amount": "4734100", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "INR",
    "name": "Acme Corp", //your business name
    "description": "Test Transaction",
    "image": "https://example.com/your_logo",
    "order_id": "order_O7zVoDze8xSNf1", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    "callback_url": "https://eneqd3r9zrjok.x.pipedream.net/",
    "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
        "name": "Gaurav Kumar", //your customer's name
        "email": "gaurav.kumar@example.com",
        "contact": "9000090000" //Provide the customer's phone number for better conversion rates 
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
var rzp1 = new Razorpay(options);
document.getElementById('rzp-button1').onclick = function(e){
    rzp1.open();
    e.preventDefault();
}
document.addEventListener('DOMContentLoaded', function() {
    localStorage.removeItem('rzp_checkout_anon_id');
    localStorage.removeItem('rzp_device_id');
});
</script>
</body>
</html>