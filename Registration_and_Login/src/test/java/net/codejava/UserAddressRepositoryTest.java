package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.user.User;
import net.codejava.user.UserRepository;
import net.codejava.userAddress.UserAddress;
import net.codejava.userAddress.UserAddressRepository;
import net.codejava.userInfo.UserInfo;
import net.codejava.userInfo.UserInfoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserAddressRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private UserInfoRepository UIrepo;
	
	@Autowired
	private UserAddressRepository UArepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("black0311@gmail.com");
		user.setPassword("$2a$10$pNLeUA.ztKJA6l481e1E3.rhEhHPDqwWjhHsOqnzJbQTT8w5sXWlu");
		user.setFirstName("Ling Ling");
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
	public void findByID() {
		Long userInfoID =(long) 82;
		UserAddress userAddress = UArepo.findByID(userInfoID);
		System.out.println(userAddress.getStreet());
		assertThat(userAddress).isNotNull();
	}
	
	@Test
	public void testAddAddressToFirstExistingUser() {
		//find user by ID
		String email ="black0319@gmail.com";
		User user = repo.findByEmail(email);
		
//		//find user Info by user ID
//		UserInfo userInfo = UIrepo.findByID(user.getId());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setGender("Male");
		userInfo.setBirth(LocalDate.of(2000,03,8));
		userInfo.setUrl("HIHI");
		
		//first option to add role
		UserAddress userAddress = new UserAddress();
		userAddress.setStreet("1702,Jalan Teratai 36/22");
		userAddress.setApartment("Indahpure");
		userAddress.setCity("Kulai");
		userAddress.setPostcode(81000);
		userAddress.setState("Johor");
		
		userInfo.setUsername(user);
		userAddress.setUserInfo(userInfo);
	
		UserInfo savedUserInfo = UIrepo.save(userInfo);
		UserAddress  savedUserAddress = UArepo.save(userAddress);
		
		UserInfo existUserInfo = entityManager.find(UserInfo.class, savedUserInfo.getID());
		UserAddress existUserAdd = entityManager.find(UserAddress.class, savedUserAddress.getID()); 
		
		assertThat(existUserInfo.getUsername().getEmail()).isEqualTo(user.getEmail());
		assertThat(existUserAdd.getUserInfo().getUsername().getEmail()).isEqualTo(user.getEmail());
	}
}
