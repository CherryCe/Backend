package com.ecommerce.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = null;
		String jwtToken = null;
		String requestToken = request.getHeader("Authorization");
		logger.info("Message {}", requestToken);

		if (requestToken != null && requestToken.trim().startsWith("Bearer")) {
			jwtToken = requestToken.substring(7);
			try {
				username = jwtHelper.getUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				logger.info("Invaild Token Message", "Jwt Token Expire!!!");
			} catch (MalformedJwtException e) {
				logger.info("Invaild Token Message", "Invaild Jwt Token!!!");
			} catch (IllegalArgumentException e) {
				logger.info("Invaild Token Message", "Unable To Get Token!!!");
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtHelper.vaildateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					logger.info("Not Vaildate Message", "invaild Jwt Token!!!");
				}
			} else {
				logger.info("User Message", "Username Is Null Or Auth Is Already There!!!");
			}
		} else {
			logger.info("Token Message {}", "Token Does Not Start With Bearer!!!");
		}
		filterChain.doFilter(request, response);
	}
}