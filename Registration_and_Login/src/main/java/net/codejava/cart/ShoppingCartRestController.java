package net.codejava.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.codejava.CartItemService;
import net.codejava.CustomeUserDetails;
import net.codejava.user.User;
import net.codejava.user.UserRepository;

@RestController
public class ShoppingCartRestController {
	
	@Autowired
	private CartItemService cartService;
	
	@Autowired
	private UserRepository repo;
	
	@PostMapping("/cart/add/{id}/{quantity}")
	public String addProductToCart(@PathVariable("id") Long id,@PathVariable("quantity") Integer quantity,
			@AuthenticationPrincipal Authentication authentication, @AuthenticationPrincipal CustomeUserDetails loggedUser) {
		if(authentication == null || loggedUser == null) {
			return "You must login to add this item to your shopping cart.";
		}
		
		User user = repo.findByEmail(loggedUser.getUsername());
		
		Integer addedQuantity = cartService.addProduct(id, quantity, user);
		
		return addedQuantity + " item(s) of this item were added to your shopping cart.";
	}
}
