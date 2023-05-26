package com.jimart.userservice.domain.user.dto;

import com.jimart.userservice.domain.user.User;
import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private String userId;
    private String password;
    private String email;
    private String name;
    private UserAuthorityType authority;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .birthDate(birthDate)
                .sex(sex)
                .build();
    }
}
