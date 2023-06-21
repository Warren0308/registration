package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.role.Role;
import net.codejava.role.RoleRepository;
import net.codejava.user.User;
import net.codejava.user.UserRepository;
import net.codejava.userAddress.UserAddress;
import net.codejava.userAddress.UserAddressRepository;
import net.codejava.userInfo.UserInfo;
import net.codejava.userInfo.UserInfoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private UserInfoRepository UIrepo;
	
	@Autowired
	private RoleRepository Rrepo;
	
	@Autowired
	private UserAddressRepository UArepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("black0309@gmail.com");
		user.setPassword("$2a$10$pNLeUA.ztKJA6l481e1E3.rhEhHPDqwWjhHsOqnzJbQTT8w5sXWlu");
		user.setFirstName("Ling");
		user.setLastName("They");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setGender("female");
		userInfo.setBirth(LocalDate.of(2000,03,24));
		userInfo.setUrl("H");
		
		UserAddress userAddress = new UserAddress();
		userAddress.setStreet("1702,Jalan Teratai 36/22");
		userAddress.setApartment("Indahpura");
		userAddress.setPostcode(81000);
		userAddress.setCity("Kulai");
		userAddress.setState("Johor");
		
		userAddress.setUserInfo(userInfo);
		userInfo.setUsername(user);
		user.setUserInfo(userInfo);
		
		User savedUser = repo.save(user);
		UserInfo savedUserInfo = UIrepo.save(userInfo);
		UserAddress savedUserAddress = UArepo.save(userAddress);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		UserInfo existUserInfo = entityManager.find(UserInfo.class, savedUserInfo.getID());
		UserAddress existUserAdd = entityManager.find(UserAddress.class, savedUserAddress.getID()); 
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(existUserInfo.getUsername().getEmail()).isEqualTo(user.getEmail());
		assertThat(existUserAdd.getUserInfo().getUsername().getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	public void findByEmail() {
		String email ="black0309@gmail.com";
		User user = repo.findByEmail(email);
		UserInfo userInfo = UIrepo.findByID(user.getId());
		UserAddress userAddress = UArepo.findByID(userInfo.getID());
		System.out.println(userAddress.getStreet());
		assertThat(userAddress).isNotNull();
	}
	
	@Test
	public void checkByEmail() {
		String email ="black0308@gmail.com";
		
		Optional<User> user = repo.checkByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testAddRoleToNewUser() {
		String name = "User";
		User user = new User();
		user.setEmail("black0319@gmail.com");
		user.setPassword("$2a$10$pNLeUA.ztKJA6l481e1E3.rhEhHPDqwWjhHsOqnzJbQTT8w5sXWlu");
		user.setFirstName("Ling");
		user.setLastName("They");

		Role role = Rrepo.findByName(name);
		user.addRole(role);
		
		User savedUser = repo.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
	
	@Test
	public void testAddRolesToFirstExistingUser() {
		//find user by ID
		User user = repo.findById(81L).get();
		
		//first option to add role
		Role roleUser = Rrepo.findByName("User");
		user.addRole(roleUser);
		
		//second option to add role
		Role roleAdmin = new Role(102L);
		user.addRole(roleAdmin);

		User savedUser = repo.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(2);
	}
	
	@Test
	public void testAddRolesToSecondExistingUser() {
		//find user by ID
		User user = repo.findById(84L).get();
		
		//first option to add role
		Role roleUser = Rrepo.findByName("User");
		user.addRole(roleUser);
		
//		//second option to add role
//		Role roleAdmin = new Role(102L);
//		user.addRole(roleAdmin);

		User savedUser = repo.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
	
}
