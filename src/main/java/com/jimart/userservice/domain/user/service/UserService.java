package com.jimart.userservice.domain.user.service;

import com.jimart.userservice.domain.user.dto.UserDto;
import com.jimart.userservice.domain.user.dto.UserResDto;

import java.util.List;

public interface UserService {

    UserResDto saveUser(UserDto userDto);
    List<UserResDto> getAllUsers();
    UserResDto findByUserId(String userId);
    UserResDto updateUser(UserDto userDto);
    void deleteUser(String userId);

}
