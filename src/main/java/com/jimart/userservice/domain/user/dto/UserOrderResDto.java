package com.jimart.userservice.domain.user.dto;

import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrderResDto {

    private String userId;
    private String name;
    private String email;
    private UserAuthorityType authority;
    private List<OrderResDto> orders;

    @Builder
    private UserOrderResDto(String userId, String name, String email, UserAuthorityType authority, List<OrderResDto> orders) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authority = authority;
        this.orders = orders;
    }
}
