<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <!-- meta data -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <!--font-family-->
		<link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
        
        <!-- title of site -->
        <title>ShopAll</title>
        
        <!--style.css-->
        <link rel="stylesheet" href="../css/style.css">
        
        <!--bootstrap cdn link-->
        
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    	<script src="../javascript/cart.js"></script>
    </head>
<body style="background-image: url('https://t4.ftcdn.net/jpg/02/32/16/07/360_F_232160763_FuTBWDd981tvYEJFXpFZtolm8l4ct0Nz.jpg'); background-size: cover; background-position: center;">
    <!--new-arrivals start -->
    <section id="new-arrivals" class="new-arrivals">
        <div class="container">
            <div class="section-header">
                <h2><b>CART</b></h2>
            </div>
        </div>    
        <div class="new-arrivals-content">
            <div class="row"></div>
        </div>
        <div class="pinCode">Enter PinCode :  <input type="text" id="pincode" name="pincode" minlength="6" maxlength="6" pattern="[0-9]{6}" required>
        <button id="checkAV">Check Avilability</button>
        </div>
        <a href="checkout.jsp"><button id="checkout" class="btn btn-success">Checkout</button></a>
    </section>
</body>

    <script></script>
</html>