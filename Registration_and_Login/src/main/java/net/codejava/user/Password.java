package net.codejava.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String rawPassword = "132231";
		String encodedPassword = encoder.encode(rawPassword);
		
		System.out.println(encodedPassword);

	}

}
