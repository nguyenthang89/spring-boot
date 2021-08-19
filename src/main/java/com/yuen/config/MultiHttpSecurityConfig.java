package com.yuen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yuen.jwt.JwtAuthenticationFilter;
import com.yuen.service.MyUserDetailsService;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

	// Web Security
	@Configuration
	public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		MyUserDetailsService myUserDetailsService;

		/*
		 * @Bean public PasswordEncoder passwordEncoder() { return new
		 * BCryptPasswordEncoder(); }
		 */
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(myUserDetailsService);
		}

		// Authentication and Authorization
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();

			http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')").and().formLogin()
					.loginPage("/login").usernameParameter("email").passwordParameter("password")
					.defaultSuccessUrl("/admin").failureUrl("/login?error");

		}
	}

	// Rest Security
	@Configuration
	@Order(1)
	public class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		MyUserDetailsService myUserDetailsService;

		/*
		 * @Bean public PasswordEncoder passwordEncoder() { return new
		 * BCryptPasswordEncoder(); }
		 */

		@Bean
		public JwtAuthenticationFilter jwtAuthenticationFilter() {
			return new JwtAuthenticationFilter();
		}

		@Bean(BeanIds.AUTHENTICATION_MANAGER)
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			// Get AuthenticationManager bean
			return super.authenticationManagerBean();
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(myUserDetailsService);
		}

		// Authorization
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.antMatcher("/api/**") // <= Security only available for /api/**
					.authorizeRequests()
					.antMatchers("/api/register").permitAll()
					.antMatchers("/api/login").permitAll()
					.antMatchers("/api/public").permitAll()
					.antMatchers("/api/lost").permitAll()
					.antMatchers("/api/v1/**").authenticated().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		}
	}

}
