package com.training.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@Order(value = 101)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customAuthenticationProvider")
	private CustomAuthenticationProvider authProvider;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean("authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
					.antMatchers("/login","/home", "/index","/","/css/**", "/js/**", "/images/**","/plugins/**","/api/search/{pageNumber}", "/product/api/brandList").permitAll()
					.antMatchers("/brand","/product").access("hasRole('ROLE_ADMIN')")
					.anyRequest().authenticated()
			.and().formLogin().loginPage("/login")
					.loginProcessingUrl("/loginAction")
					.defaultSuccessUrl("/product")
					.usernameParameter("username")
					.passwordParameter("password")
					.failureUrl("/login?error")
			.and().logout().logoutSuccessUrl("/login")
			.and().exceptionHandling().accessDeniedPage("/login")
			.and() // Remember Me Configuration
			.rememberMe().key("uniqueAndSecret").rememberMeParameter("remember-me")
			.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(2592000);
			
		}
	

	 @Bean
	    public PersistentTokenRepository persistentTokenRepository() {
	       JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
	       tokenRepositoryImpl.setDataSource(dataSource);
	       
	       return tokenRepositoryImpl;
		}
}