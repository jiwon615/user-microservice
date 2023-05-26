package com.jimart.userservice.domain.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAuthorityType {

    ADMIN("ADMIN"),
    USER("USER")
    ;

    private final String text;
}
