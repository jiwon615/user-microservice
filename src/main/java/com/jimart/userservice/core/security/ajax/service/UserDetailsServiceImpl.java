package com.jimart.userservice.core.security.ajax.service;

import com.jimart.userservice.core.exception.CustomException;
import com.jimart.userservice.domain.user.entity.User;
import com.jimart.userservice.domain.user.dto.UserDto;
import com.jimart.userservice.domain.user.dto.UserResDto;
import com.jimart.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jimart.userservice.core.exception.ErrorMsgType.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUserOpt = userRepository.findByUserId(username);

        findUserOpt.map(UserResDto::of)
                .orElseThrow(() -> new InsufficientAuthenticationException(USER_NOT_FOUND.getMessage()));

        List<GrantedAuthority> roles = new ArrayList<>();
        User findUser = findUserOpt.get();
        roles.add(new SimpleGrantedAuthority(findUser.getAuthority().getText()));

        // 사용자에게 허가된 모든 권한을 포함한 사용자의 세부 정보를 반환
        UserDto userDto = UserDto.builder()
                .userId(findUser.getUserId())
                .password(findUser.getPassword())
                .name(findUser.getName())
                .email(findUser.getEmail())
                .authority(findUser.getAuthority())
                .build();
        return new UserContext(userDto, roles);
    }
}
