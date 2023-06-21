package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.codejava.cart.CartItem;
import net.codejava.cart.CartItemRepository;
import net.codejava.menu.Food;
import net.codejava.menu.FoodRepository;
import net.codejava.user.User;

@Service
public class CartItemService {

		@Autowired
		private CartItemRepository CIrepo;
		
		@Autowired
		private FoodRepository Frepo;
		
		public List<CartItem> listCartItems(User user){
			return CIrepo.findByUser(user);
		}
		
		public Integer addProduct(Long id, Integer quantity, User user) {
			Integer addedQuantity = quantity;
			
			Food food = Frepo.findByID(id);
			
			CartItem cartItem = CIrepo.findByUserAndFood(user, food);
			
			if(cartItem!= null) {
				addedQuantity = cartItem.getQuantity()+quantity;
				cartItem.setQuantity(addedQuantity);
			}else {
				cartItem = new CartItem();
				cartItem.setQuantity(quantity);
				cartItem.setUser(user);
				cartItem.setFood(food);
			}
			
			CIrepo.save(cartItem);
			
			return addedQuantity;
		}
}
