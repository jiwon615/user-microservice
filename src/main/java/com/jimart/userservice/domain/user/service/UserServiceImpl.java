package com.jimart.userservice.domain.user.service;

import com.jimart.userservice.domain.user.dto.UserDto;
import com.jimart.userservice.domain.user.dto.UserResDto;
import com.jimart.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResDto saveUser(UserDto request) {
        return null;
    }

    @Override
    public List<UserResDto> getAllUsers() {
        return null;
    }

    @Override
    public UserResDto findByUserId(String userId) {
        return null;
    }

    @Override
    public UserResDto updateUser(UserDto request) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }
}
