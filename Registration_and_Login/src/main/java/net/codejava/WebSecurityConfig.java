package net.codejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	protected AuthenticationManagerBuilder getAuthenticationFilter(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
		return auth;
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		String text = testResourceFile();
		if(text.equals("1")) {
			http.authorizeHttpRequests().antMatchers("/demo","/cart").authenticated()
			.antMatchers("/users/edit/{id}/edit","/users/edit/{id}/details/form").hasAuthority("Admin")
			.antMatchers("/list_user").hasAnyAuthority("Admin","User")
			.anyRequest().permitAll()
			.and().formLogin().usernameParameter("email").defaultSuccessUrl("/").permitAll()
			.and().logout().logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/403");
		}
		else {
			http.authorizeHttpRequests().anyRequest().permitAll()
			.and().formLogin().usernameParameter("email").defaultSuccessUrl("/").permitAll()
			.and().logout().logoutSuccessUrl("/").permitAll();
		}
		
		return http.build();
	}
	
	public static String testResourceFile(){
		String text = null;
		try {
			File resource = new ClassPathResource("static/Test.txt").getFile();
			text = new String(Files.readAllBytes(resource.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}
