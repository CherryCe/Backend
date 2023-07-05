package com.ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.security.JwtAuthenticationEntryPoint;
import com.ecommerce.security.JwtAuthenticationFilter;
import com.ecommerce.service.CustomUserDetailsService;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;

	@Autowired
	private JwtAuthenticationFilter filter;

	public static String[] PUBLIC_URL = { "/auth/login" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().antMatchers(PUBLIC_URL).permitAll().anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(entryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public FilterRegistrationBean cosFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Context-Type");
		configuration.addAllowedHeader("Accept");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("OPTIONS");
		configuration.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", configuration);
		FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
		bean.setOrder(-110);
		return bean;
	}
}
