package com.jimart.userservice.domain.user.dto;

import com.jimart.userservice.domain.user.entity.User;
import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String email;
    @Setter
    private UserAuthorityType authority;

    @Builder
    private UserDto(String userId, String password, String name, String email, UserAuthorityType authority) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.authority = authority;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .authority(authority)
                .build();
    }
}
