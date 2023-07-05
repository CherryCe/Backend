package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
		UserDto create = userService.create(userDto);
		return new ResponseEntity<UserDto>(create, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<ApiResponse> remove(@PathVariable int id) {
		userService.remove(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Removed!!!", true), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable int id) {
		UserDto update = userService.update(userDto, id);
		return new ResponseEntity<UserDto>(update, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> getAll = userService.getAll();
		return new ResponseEntity<List<UserDto>>(getAll, HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<UserDto> getById(@PathVariable int id) {
		UserDto getById = userService.getById(id);
		return new ResponseEntity<UserDto>(getById, HttpStatus.OK);
	}
}
