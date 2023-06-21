package net.codejava.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.menu.Food;
import net.codejava.user.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	public List<CartItem> findByUser(User user);
	
	public CartItem findByUserAndFood(User user,Food food);
}
