$(document).ready(function() {
				updateCartItemCount();
				var cp=1;
				paginate(1);
				    $('.pagination-link').click(function(e) {
        e.preventDefault(); // Prevent default action of the link
        
        // Get the value of 'data-page' attribute
        var page = $(this).data('page');
        cp=page;
 		console.log(page);
        paginate(page);
   		 });
   		     $('#prevPage').click(function(e) {
        e.preventDefault(); // Prevent default action of the link
        var prevPage = cp - 1;
        cp=prevPage
        console.log(prevPage);
        if (prevPage >= 1) {
            paginate(prevPage);
        }
                else {
                cp=cp+1;
        alert("ERROR");
        }
    });
      $('#nextPage').click(function(e) {
        e.preventDefault(); // Prevent default action of the link
        var nextPage = cp + 1;  
        cp=nextPage;
        console.log(nextPage)  
        if (nextPage <= 3) {
            paginate(nextPage);
        }
        else {
        cp=cp-1;
        alert("ERROR");
        }
    });
	  function paginate(pageNumber) {
		    $.ajax({
		        url: 'http://localhost:8080//Ecomerece/AllProductsServlet',
		        method: 'GET',
		        data: { page: pageNumber },
		        success: function(data) {
		  
		            data = JSON.parse(data);
					console.log(data);
					 $('.row').empty(); 
		            $.each(data.AllProducts, function(index, element) {
		                var div='<div class="col-md-3 col-sm-4">'+
						'<div class="single-new-arrival">'+
						'<div class="single-new-arrival-bg">'+
						'<img src="../images/' + element[3] + '" alt="' + element[3] + '">'+
							'<div class="single-new-arrival-bg-overlay"></div>'+
							'<div class="new-arrival-cart">'+
								'<p>'+
									'<span class="lnr lnr-cart"></span>'+
									'<a href="#">'+element[1]+'</a>'+
								'</p>'+
								'<p class="arrival-review pull-right">'+
									'<span class="lnr lnr-heart"></span>'+
									'<span class="lnr lnr-frame-expand"></span>'+
								'</p>'+
							'</div>'+
						'</div>'+
						'<h4>' + element[1] + '</h4>'+
						'<p class="arrival-product-price">' + element[2] + '</p>'+
						'<div class="counter">'+
					        '<button id="inc" class="count">+</button>'+
					        '<input type="number" min="1" max="10" value=1 readonly>'+
					        '<button id="dec" class="count">-</button>'+
    					'</div>'+
						'<button class="cart" id="'+element[0]+'">Add to Cart</button>'+
					'</div>'+
				'</div>';
				
		                 $('.row').append(div); 
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error('AJAX request failed:', error);
		        }
		    });
		    }
		
		});

$(document).ready(function() {
			var url = 'http://localhost:8080//Ecomerece/CategoriesServlet';
		    $.ajax({
		        url: url,
		        method: 'GET',
		        success: function(data) {
		            //$('#category').empty();
		            data = JSON.parse(data);
		            $.each(data, function(index, element) {
		                
						var options='<option value='+element+'>'+index+'</option>';
		                $('#category').append(options);
		            });
		        },
		        error: function(xhr, status, error) {
		            console.error('AJAX request failed:', error);
		        }
		    });
		
		});
		
$(document).on('change', '#category', function() {
		    		var scv=$("#category").val();
					console.log(scv)
				     $.ajax({
				        url: 'http://localhost:8080//Ecomerece/AllProdByIdServlet',
				        method: 'GET',
						data: {val:scv},
				    success: function(data) {
		            $('.row').empty();
		            data = JSON.parse(data);
					console.log(data);
		            $.each(data.AllProducts, function(index, element) {
		                
		                var div='<div class="col-md-3 col-sm-4">'+
						'<div class="single-new-arrival">'+
						'<div class="single-new-arrival-bg">'+
						'<img src="../images/' + element[3] + '" alt="' + element[3] + '">'+
							'<div class="single-new-arrival-bg-overlay"></div>'+
							'<div class="new-arrival-cart">'+
								'<p>'+
									'<span class="lnr lnr-cart"></span>'+
									'<a href="#">'+element[1]+'</a>'+
								'</p>'+
								'<p class="arrival-review pull-right">'+
									'<span class="lnr lnr-heart"></span>'+
									'<span class="lnr lnr-frame-expand"></span>'+
								'</p>'+
							'</div>'+
						'</div>'+
						'<h4>' + element[1] + '</h4>'+
						'<div class="counter">'+
					        '<button id="inc" class="count">+</button>'+
					        '<input type="number" min="1" max="10" value=1 readonly>'+
					        '<button id="dec" class="count">-</button>'+
    					'</div>'+
						'<p class="arrival-product-price">' + element[2] + '</p>'+
						'<button class="cart" id="'+element[0]+'">Add to Cart</button>'+
					'</div>'+
				'</div>';
		                $('.row').append(div);
		            });
		        },
				        error: function(xhr, status, error) {
				            console.error('AJAX request failed:', error);
				        }
				    });		
				    
				    });
 $(document).on('click', '.single-new-arrival #inc', function() {
  
    var inputElement = $(this).closest('.single-new-arrival').find('input');
    var currentValue = parseInt(inputElement.val());

    
    if (!isNaN(currentValue) && currentValue<=9) {
        var newValue = currentValue + 1;
        inputElement.val(newValue);
    }
});


$(document).on('click', '.single-new-arrival #dec', function() {
   
    var inputElement = $(this).closest('.single-new-arrival').find('input');
    var currentValue = parseInt(inputElement.val());

   
    if (!isNaN(currentValue) && currentValue > 1) {
        var newValue = currentValue - 1;
        inputElement.val(newValue);
    }
});

$(document).on('click', '.single-new-arrival .cart', function() {
							var ii=$(this);
							var itemCard = ii.closest('.single-new-arrival');
							var itemname = itemCard.find('h4').text();
					var itemprice = itemCard.find('.arrival-product-price').text();
					var itemCard2 = itemCard.find('.single-new-arrival-bg');
					var itemimage = itemCard2.find('img').attr('src');
					var itemQuantity=itemCard.find('input').val();					
					var itemData = {
						itemname: itemname,
						itemprice: itemprice,
						itemimage: itemimage,
						itemQuantity: itemQuantity
					};
			var productId=ii.closest('.single-new-arrival').find('.cart').attr('id');		 
					var itemJSON = JSON.stringify(itemData);
	   $.ajax({
        type: 'POST',
        url: 'http://localhost:8080//Ecomerece/GetStockServLet',
        data: {
            productId: productId,
        },
        success: function(stock) {
                       var stockValue = parseInt(stock, 10);
            // Handle success, 'stockValue' contains the stock value as an integer
          if(itemQuantity<=stockValue){
                $.ajax({
        type: 'POST',
        url: 'http://localhost:8080//Ecomerece/UpdateStockServlet',
        data: {
            productId: productId,
            quantity: itemQuantity
        },
        success: function(response) {
            console.log('Stock updated successfully:', response);
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error('Error updating stock:', error);
        }
  	  });
            }
            else{
            	alert("OUT OF STOCK");
            }
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error('Error updating stock:', error);
        }
    });
	

					localStorage.setItem(productId, itemJSON);
					
					updateCartItemCount();

					console.log('Item added to cart:', itemname);
  
});
function updateCartItemCount() {
   
    $('.cart-icon .badge').text(localStorage.length);
}

  $(document).ready(function () { 
            $(".cart").click(function () { 
                alert("This is an alert message!"); 
            }); 
        });
$(document).on('change', '#sort', function() {
					console.log("ook");
		    		var scv=$("#category").val();
		    		var ssv=$("#sort").val();
					console.log(scv+" "+ssv)
				     $.ajax({
				        url: 'http://localhost:8080//Ecomerece/SortProdServlet',
				        method: 'GET',
						data: {'val1':scv,'val2':ssv},
				    success: function(data) {
		            $('.row').empty();
		            data = JSON.parse(data);
					console.log(data);
		            $.each(data.AllProducts, function(index, element) {
		                
		                var div='<div class="col-md-3 col-sm-4">'+
						'<div class="single-new-arrival">'+
						'<div class="single-new-arrival-bg">'+
						'<img src="../images/' + element[3] + '" alt="' + element[3] + '">'+
							'<div class="single-new-arrival-bg-overlay"></div>'+
							'<div class="new-arrival-cart">'+
								'<p>'+
									'<span class="lnr lnr-cart"></span>'+
									'<a href="#">'+element[1]+'</a>'+
								'</p>'+
								'<p class="arrival-review pull-right">'+
									'<span class="lnr lnr-heart"></span>'+
									'<span class="lnr lnr-frame-expand"></span>'+
								'</p>'+
							'</div>'+
						'</div>'+
						'<h4>' + element[1] + '</h4>'+
						'<div class="counter">'+
					        '<button id="inc" class="count">+</button>'+
					        '<input type="number" min="1" max="10" value=1 readonly>'+
					        '<button id="dec" class="count">-</button>'+
    					'</div>'+
						'<p class="arrival-product-price">' + element[2] + '</p>'+
						'<button class="cart" id="'+element[0]+'">Add to Cart</button>'+
					'</div>'+
				'</div>';
		                $('.row').append(div);
		            });
		        },
				        error: function(xhr, status, error) {
				            console.error('AJAX request failed:', error);
				        }
		});		
				    
});