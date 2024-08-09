package com.educationCommittee.SecurityConfigration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Myconfig {
	
	CustomSuccessHandler customSuccessHandler = new CustomSuccessHandler()	;


	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailServiceimpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeHttpRequests()
//		.requestMatchers("/user/dashboard").authenticated().and().authorizeHttpRequests()
//		.requestMatchers("/**").permitAll().and().formLogin().loginPage("/login");
		
		http.csrf(c->c.disable())
		.authorizeHttpRequests(request->request.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/user/**").hasAuthority("USER").requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
		.formLogin(form->form.loginPage("/login").loginProcessingUrl("/login")
				.successHandler(customSuccessHandler).permitAll());
		
		return http.build();
	}


	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}
}
