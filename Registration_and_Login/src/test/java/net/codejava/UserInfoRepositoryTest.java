package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import net.codejava.user.User;
import net.codejava.user.UserRepository;
import net.codejava.userInfo.UserInfo;
import net.codejava.userInfo.UserInfoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserInfoRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private UserInfoRepository UIrepo;
	
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
		
		userInfo.setUsername(user);
		user.setUserInfo(userInfo);
		
		User savedUser = repo.save(user);
		UserInfo savedUserInfo = UIrepo.save(userInfo);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		UserInfo existUserInfo = entityManager.find(UserInfo.class, savedUserInfo.getID());
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(existUserInfo.getUsername().getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	public void findById() {
		long Id = 3;
		
		UserInfo  userInfo = UIrepo.findByID(Id);
		System.out.println(userInfo.getID());
		assertThat(userInfo).isNotNull();
	}
	
	@BeforeEach
    public void setUp(){
		User user = new User();
		user.setEmail("black0310@gmail.com");
		user.setPassword("132231");
		user.setFirstName("Ling");
		user.setLastName("They");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setGender("male");
		userInfo.setBirth(LocalDate.of(2000, 3, 8));
		userInfo.setUrl("Hi");
		
		user.setUserInfo(userInfo);
		userInfo.setUsername(user);

    }
}
