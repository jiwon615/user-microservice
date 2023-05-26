package com.jimart.userservice.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateReqDto {

    @NotBlank(message = "아이디 입력은 필수입니다.")
    @Size(min = 4, max = 15, message = "아이디는 4~15자 사이로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 4, max = 15, message = "비밀번호는 4~15자 사이로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름 입력은 필수입니다.")
    @Size(min = 2, max = 8, message = "이름은 2~8자 사이로 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
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
