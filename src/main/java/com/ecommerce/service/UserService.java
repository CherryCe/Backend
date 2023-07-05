package com.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDto create(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDate(new Date());
		User save = userRepository.save(user);
		return mapper.map(save, UserDto.class);
	}

	public void remove(int id) {
		User findById = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		userRepository.delete(findById);
	}

	public UserDto update(UserDto newUser, int id) {
		User oldUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		oldUser.setName(newUser.getName());
		oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		oldUser.setEmail(newUser.getEmail());
		oldUser.setAddress(newUser.getAddress());
		oldUser.setAbout(newUser.getAbout());
		oldUser.setGender(newUser.getGender());
		oldUser.setPhone(newUser.getPhone());
		oldUser.setDate(newUser.getDate());
		oldUser.setActive(newUser.isActive());
		User save = userRepository.save(oldUser);
		return mapper.map(save, UserDto.class);
	}

	public List<UserDto> getAll() {
		List<User> findAll = userRepository.findAll();
		return findAll.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
	}

	public UserDto getById(int id) {
		User findById = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		return mapper.map(findById, UserDto.class);
	}
}
