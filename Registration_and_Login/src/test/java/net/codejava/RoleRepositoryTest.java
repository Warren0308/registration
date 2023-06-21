package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import net.codejava.role.Role;
import net.codejava.role.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository Rrepo;
	
	@Test
	public void testCreateRoles() {
		Role user = new Role("User");
		Role admin = new Role("Admin");
		Role customer = new Role("Customer");
		Rrepo.save(user);
		Rrepo.save(admin);
		Rrepo.save(customer);
		
		List<Role> listRoles = Rrepo.findAll();
		assertThat(listRoles.size()).isEqualTo(3);
	}

	@Test
	public void testNewCreateRoles() {
		Role user = new Role();
		user.setName("Manager");
		
		Rrepo.save(user);
	}
}
