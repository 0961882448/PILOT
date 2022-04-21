package com.training.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security Configuration
 * 
 * @author Red Devil
 * @since 2018-07-27
 */
@Configuration
@Order(value = 102)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// User Permission 
		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**","/plugins/**", "/*", "/**/*", "/**/**/*").permitAll()
				.and() // Disable CRSF check
						.csrf().disable();
	}
}