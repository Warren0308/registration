package net.codejava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.cart.CartItem;
import net.codejava.cart.CartItemRepository;
import net.codejava.menu.Food;
import net.codejava.user.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTest {
	
	@Autowired
	private CartItemRepository CIrepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddOneCartItem() {
		Food food = entityManager.find(Food.class,(long)2);
		User user = entityManager.find(User.class,(long)81);
		
		CartItem newItem = new CartItem();
		newItem.setUser(user);
		newItem.setFood(food);
		newItem.setQuantity(4);
		
		CartItem savedCartItem = CIrepo.save(newItem);
		
		assertTrue(savedCartItem.getId()>0);
	}
	
	@Test 
	public void testGetCartItemByUser() {
		User user = new User();
		user.setId((long)81);
		
		List<CartItem> cartItems = CIrepo.findByUser(user);
		
		assertEquals(1,cartItems.size());
	}
}
