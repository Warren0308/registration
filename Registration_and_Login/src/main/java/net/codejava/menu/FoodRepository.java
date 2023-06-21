package net.codejava.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long> {
	
	@Query("SELECT u FROM Food u WHERE u.name=?1")
	Food findByFoodName(String name);
	
	@Query("SELECT u FROM Food u WHERE u.id=?1")
	Food findByID(Long id);
}
