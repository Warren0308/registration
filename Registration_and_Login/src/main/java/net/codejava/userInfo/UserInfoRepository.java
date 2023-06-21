package net.codejava.userInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	@Query("SELECT ui FROM User u join u.userInfo ui WHERE u.id = ?1")
	UserInfo findByID(Long long1);
	
	
}
