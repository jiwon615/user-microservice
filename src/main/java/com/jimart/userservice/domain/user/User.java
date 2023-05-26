package com.jimart.userservice.domain.user;

import com.jimart.userservice.domain.common.BaseEntity;
import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "jimart_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserAuthorityType authority;

    @Builder
    private User(String userId, String password, String name, String email, UserAuthorityType authority) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.authority = authority;
    }
}
