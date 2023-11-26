package com.BikkadIT.electronic.store.services;

import com.BikkadIT.electronic.store.dtos.UserDto;
import com.BikkadIT.electronic.store.entities.User;
import com.BikkadIT.electronic.store.payload.PageableResponse;

import java.util.List;

public interface UserService {

    public UserDto saveUser(UserDto userDto);

    public UserDto updateUser(UserDto userDto, String userId);

    public void deleteUser(String userId);

    public PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy , String sortDirection);

    public UserDto getUserById(String userId);

    public UserDto getuserByEmail(String Email);

    public List<UserDto> searchUser(String keyword);



}
