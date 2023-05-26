package com.jimart.userservice.core.security.ajax.service;

import com.jimart.userservice.domain.user.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserContext extends User {

    private UserDto userDto;

    public UserContext(UserDto userDto, Collection<? extends GrantedAuthority> authorities) {
        super(userDto.getUserId(), userDto.getPassword(), authorities);
        this.userDto = userDto;
    }
}
