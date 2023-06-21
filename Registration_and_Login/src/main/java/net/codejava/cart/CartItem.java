package net.codejava.cart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.codejava.menu.Food;
import net.codejava.user.User;

@Entity
@Table(name="cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="food_id")
	private Food food;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
