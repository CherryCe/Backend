package com.ecommerce.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.security.JwtHelper;
import com.ecommerce.security.JwtRequest;
import com.ecommerce.security.JwtResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager manger;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		autheticateUser(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = helper.generateToken(userDetails);
		JwtResponse response = new JwtResponse();
		response.setToken(token);
		response.setUser(mapper.map(userDetails, UserDto.class));
		return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
	}

	private void autheticateUser(String username, String password) {
		try {
			manger.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new ResourceNotFoundException("Invaild Username Or Password!!!");
		} catch (DisabledException e) {
			throw new ResourceNotFoundException("User Is Not Active!!!");
		}
	}
}