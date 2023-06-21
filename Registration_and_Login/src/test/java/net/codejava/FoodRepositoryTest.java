package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.menu.Food;
import net.codejava.menu.FoodRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class FoodRepositoryTest {

	@Autowired
	private FoodRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Food food = new Food();
		food.setName("Bread Barrel");
		food.setType("Spexiality");
		food.setPrice("RM6.05");
		food.setUrl("assets/img/menu/bread-barrel.jpg");
		food.setIngredients("Lorem, deren, trataro, filede, nerada");
		
		Food savedFood = repo.save(food);
		
		Food existFood = entityManager.find(Food.class, savedFood.getId());
		
		assertThat(existFood.getName()).isEqualTo(food.getName());
	}
	
	@Test
	public void findByFoodName() {
		String name ="Bread Barrel";
		
		Food food = repo.findByFoodName(name);
		System.out.println(food.getType());
		assertThat(food).isNotNull();
	}
	
	@Test
	public void listAllFood() {
		String name ="Bread Barrel";
		List<Food> listFoods= repo.findAll();
		Food food = repo.findByFoodName(name);
		
		for (Food i: listFoods) {
			System.out.println(i.getType());}
		
		//list of type
		HashSet<String> listFoodType = new HashSet<>();
		for (Food i : listFoods) {
			listFoodType.add(i.getType());
		}
		
		//put in dictionary
		Map<String,List<Food>> listFoodTypes = new HashMap<String,List<Food>>();
		for (String i : listFoodType) {
			//create new array list
			List<Food> values = new ArrayList<Food>();
			//based on type to put the food
			for(Food x:listFoods) {
				if(x.getType().equals(i)) {
					values.add(x);
				}
			}
			listFoodTypes.put(i, values);
		}

		for (String i: listFoodTypes.keySet()) {
			System.out.println(listFoodTypes.get(i));
			System.out.println(i);}
		System.out.println(listFoodTypes);
		assertThat(food).isNotNull();
	}
}
