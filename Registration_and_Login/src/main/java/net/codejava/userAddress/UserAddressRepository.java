package net.codejava.userAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	@Query("SELECT ui FROM UserInfo u join u.address ui WHERE u.id = ?1")
	UserAddress findByID(Long long1);
	
	
}
