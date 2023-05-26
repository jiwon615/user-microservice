package com.jimart.userservice.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateReqDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto toUserDto() {
        return UserDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
