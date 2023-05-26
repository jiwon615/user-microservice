package com.jimart.userservice.domain.user.dto;

import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import lombok.Getter;

@Getter
public class UserResDto {

    private String userId;
    private String name;
    private String email;
    private UserAuthorityType authority;
}
