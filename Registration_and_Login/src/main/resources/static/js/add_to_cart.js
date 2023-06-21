$(document).ready(function(){
	$("#buttonAdd2Cart").on("click",function(e){
		alert("Add to cart");
/*		addToCart()；*/
	});
});

/*$(document).ready(function addToCart(){
			quantity = $("#quantity"+productId).val();
				
				url = contextPath + "cart/add/"+productId+"/"+quantity;
				
				$.ajax({
					type:"POST",
					url:url,
					beforeSend:function(xhr){
						xhr.setRequestHeader(crsfHeaderName,csrfValue);
					}
				}).done(function(response){
					$("modalTitle").text("Shopping Cart");
					$("modalBody").text(response)
					$("#modalBody").modal();
				}).fail(function(){
					$("modalTitle").text("Shopping Cart");
					$("modalBody").text("Error while adding product to shopping cart.")
					$("#modalBody").modal();
				});
			*/