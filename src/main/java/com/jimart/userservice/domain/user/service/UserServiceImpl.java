package com.jimart.userservice.domain.user.service;

import com.jimart.userservice.domain.user.User;
import com.jimart.userservice.domain.user.dto.UserDto;
import com.jimart.userservice.domain.user.dto.UserResDto;
import com.jimart.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResDto saveUser(UserDto request) {
        User user = userRepository.save(request.toEntity());
        return UserResDto.of(user);
    }

    @Override
    public List<UserResDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserResDto findByUserId(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        return userOpt.map(UserResDto::of)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Override
    public UserResDto updateUser(UserDto request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return UserResDto.of(user);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        userOpt.ifPresent(userRepository::delete);
    }
}
