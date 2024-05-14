$(document).ready(function() {
	allitems();	        
});
$(document).on('click', '.single-new-arrival .remove', function() {	
	var rem=$(this).closest('button').attr('id');
	console.log(rem)
	localStorage.removeItem(rem);
	$('.row').empty()
	allitems();

});

function allitems()
{
		var items = [];
		
	   for (var i = 0; i < localStorage.length; i++) {
	    
        var key = localStorage.key(i);
        if (key >= 1 && key <= 100) {
        		var value = localStorage.getItem(key);
				var itemData = JSON.parse(value);
        var item = {
            key: key,
            data: itemData
        };

        	items.push(item);
       }
        }
 		$.each(items, function(index, element) {
    var div = '<div class="col-md-3 col-sm-4">' +
                '<div class="single-new-arrival">' +
                  '<div class="single-new-arrival-bg">' +
                    '<img src="../images/' + element.data.itemimage + '" alt="' + element.data.itemname + '">' +
                    '<div class="single-new-arrival-bg-overlay"></div>' +
                    '<div class="new-arrival-cart">' +
                      '<p>' +
                        '<span class="lnr lnr-cart"></span>' +
                        '<a href="#">' + element.data.itemname + '</a>' +
                      '</p>' +
                      '<p class="arrival-review pull-right">' +
                        '<span class="lnr lnr-heart"></span>' +
                        '<span class="lnr lnr-frame-expand"></span>' +
                      '</p>' +
                    '</div>' +
                  '</div>' +
                  '<h4>' + element.data.itemname + '</h4>' +
                  '<p class="arrival-product-price">' + element.data.itemprice + '</p>' +
                  '<div class="counter">' +
                   '<h4 style="margin-left:50px;">Quantity  selected : </h4> <p>'+element.data.itemQuantity +'<p>' +
                  '</div>' +
                  '<button class="remove" id="'+element.key+'">Remove from Cart</button>'+
                '</div>' +
              '</div>';
	
    $('.row').append(div);
    console.log(element.data.itemQuantity)
    $('.single-new-arrival').find('input').text(element.data.itemQuantity)
});
}


 $(document).ready(function() {
$("#checkAV").on("click", function() {
                // Get the value of the PIN code input field
                var pincode = $("#pincode").val();
			var localStorageValues = [];
var keys=[];
for (var i = 0; i < localStorage.length; i++) {
	keys.push(localStorage.key(i));
    var value = localStorage.getItem(localStorage.key(i));
    localStorageValues.push(JSON.parse(value));
}				
 var newLocalStorageValues = [];

    // Iterate over localStorageValues and create new objects with specific keys
    localStorageValues.forEach(function(item, index) {
        var newItem = {
            proid: localStorage.key(index),
            name: item.itemname,
            price: parseFloat(item.itemprice),
            quantity: parseInt(item.itemQuantity)
        };
        newLocalStorageValues.push(newItem);
    });

    // Now newLocalStorageValues contains objects with keys proid, name, price, and quantity
    console.log(newLocalStorageValues);
var dataToSend = JSON.stringify(newLocalStorageValues);
// AJAX call to send data to the servlet
$.ajax({
	url: 'http://localhost:8080/Ecomerece/YourServlet',
    type: 'POST',
    contentType: 'application/json',
    data: {pin:pincode,
   items: dataToSend},
    success: function(response) {
    var jsonRes=JSON.parse(response);
    	$.each(jsonRes,function(index,flag){
    		var key=keys[index];
    		if(flag===false){
    		var Item= JSON.parse(localStorage.getItem(key));
    			alert(Item.itemname+" is NOT Servicible to this Pincode \n Change the PinCode (or) Remove the Item");
    		
    		}
    		console.log(key)
    		console.log(flag)
    	})
    },
    error: function(xhr, status, error) {
        // Handle error response from servlet
        console.error('Error sending data to servlet:', error);
    }
});

        });
	});

$(document).on('click','#checkout',function(){

    for (var i = 0; i < localStorage.length; i++) {
      var key = localStorage.key(i);
     if (key >= 1 && key <= 100) {  
      var value = localStorage.getItem(key);
      var itemData = JSON.parse(value);
	  var v=itemData.itemQuantity;  
    var p= itemData.itemprice;   
    var n=itemData.itemname;
	console.log(key+" "+v);
$.ajax({
		method:'POST',
		url: 'http://localhost:8080/Ecomerece/InsertCartServlet',
		data:{'proid':key,'quantity':v,'price':p,'length':'1','name':n},
		 success: function(data) {
		      console.log("items sent to insert")
        },
        error: function(xhr, status, error) {
            console.error('AJAX request failed:', error);
        }
});
}
}
});