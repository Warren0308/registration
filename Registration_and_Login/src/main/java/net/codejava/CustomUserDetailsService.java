package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.codejava.user.User;
import net.codejava.user.UserRepository;
import net.codejava.userInfo.UserInfo;
import net.codejava.userInfo.UserInfoRepository;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserInfoRepository UIrepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		if(user!=null) {
			UserInfo userInfo = UIrepo.findByID(user.getId());
			return new CustomeUserDetails(user, userInfo);
			
		}
		// TODO Auto-generated method stub
		throw new UsernameNotFoundException("Could not find user with email:" + email);
	}

}
