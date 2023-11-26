package com.BikkadIT.electronic.store.services.impl;

import com.BikkadIT.electronic.store.dtos.UserDto;
import com.BikkadIT.electronic.store.entities.User;
import com.BikkadIT.electronic.store.payload.Helper;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.repositories.UserRepository;
import com.BikkadIT.electronic.store.services.UserService;


import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;
	@Override
	public UserDto saveUser(UserDto userDto) {
		logger.info("Entering dao call for save the user data");
		User user = this.mapper.map(userDto, User.class);
		String str = UUID.randomUUID().toString();
		user.setUserId(str);
		User saveUser = this.userRepository.save(user);
		logger.info("Completed dao call for save the user data");
		return this.mapper.map(saveUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		logger.info("Entering dao call for update the user data with userId : {} ",userId);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User Not found with given id"));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setImage(userDto.getImage());
		
		User updatedUser =userRepository.save(user);
		logger.info("Completed dao call for update the user data with userId : {} ",userId);
		return mapper.map(updatedUser, UserDto.class);


	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User Not found with given id"));
		userRepository.delete(user);
	}


	@Override
	public UserDto getUserById(String userId) {
		logger.info("Entering dao call for get the user data with userId : {} ",userId);
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not found with given id"));
		logger.info("Completed dao call for get the user data with userId : {} ",userId);
		return entityToDto(user);
	}

	@Override
	public UserDto getuserByEmail(String email) {
		User user =userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email"));
		return entityToDto(user);
	}

	public List<UserDto> searchUser(String keyword){
		List<User> users =  userRepository.findByNameContaining(keyword); 
		List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

		return dtoList;}
	
	private UserDto entityToDto(User savedUser) {
		return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
		return mapper.map(userDto, User.class);
	}


	public PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		logger.info("Entering dao call for get all users data");
		Sort sort;

		if (sortDir.equalsIgnoreCase("desc")) {

			sort = Sort.by(sortBy).descending();
		} else {
			sort = Sort.by(sortBy).ascending();
		}

		PageRequest page = PageRequest.of(pageNumber, pageSize, sort);

		Page<User> userPages = this.userRepository.findAll(page);

		PageableResponse<UserDto> response = Helper.getPageableResponse(userPages, UserDto.class);
		logger.info("Completed dao call for get all users data");
		return response;



	}
}
