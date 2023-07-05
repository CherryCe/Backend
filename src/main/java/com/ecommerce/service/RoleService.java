package com.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.RoleDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper mapper;

	public List<RoleDto> getAll() {
		List<Role> findAll = roleRepository.findAll();
		return findAll.stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
	}

	public RoleDto getById(int id) {
		Role findById = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role Not Found!!!"));
		return mapper.map(findById, RoleDto.class);
	}
}
