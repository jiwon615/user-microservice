package com.jimart.userservice.domain.user.dto;

import com.jimart.userservice.domain.user.entity.User;
import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResDto {

    private String userId;
    private String name;
    private String email;
    private UserAuthorityType authority;

    @Builder
    private UserResDto(String userId, String name, String email, UserAuthorityType authority) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authority = authority;
    }

    public static UserResDto of(User user) {
        return UserResDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .authority(user.getAuthority())
                .build();
    }
}
