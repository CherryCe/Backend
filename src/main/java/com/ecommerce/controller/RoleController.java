package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.RoleDto;
import com.ecommerce.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/getAll")
	public ResponseEntity<List<RoleDto>> getAll() {
		List<RoleDto> getAll = roleService.getAll();
		return new ResponseEntity<List<RoleDto>>(getAll, HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<RoleDto> getById(@PathVariable int id) {
		RoleDto getById = roleService.getById(id);
		return new ResponseEntity<RoleDto>(getById, HttpStatus.OK);
	}
}
