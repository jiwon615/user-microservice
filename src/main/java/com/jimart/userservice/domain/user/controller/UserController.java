package com.jimart.userservice.domain.user.controller;

import com.jimart.userservice.core.common.ApiResponse;
import com.jimart.userservice.domain.user.dto.UserCreateReqDto;
import com.jimart.userservice.domain.user.dto.UserResDto;
import com.jimart.userservice.domain.user.dto.UserUpdateReqDto;
import com.jimart.userservice.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<UserResDto> saveUser(@Valid @RequestBody UserCreateReqDto request) {
        return ApiResponse.created(userService.saveUser(request.toUserDto()));
    }

    // TODO: paging 적용
    @GetMapping("")
    public ApiResponse<List<UserResDto>> getAllUsers() {
        return ApiResponse.ok(userService.getAllUsers());
    }

    @GetMapping("{userId}")
    public ApiResponse<UserResDto> findByUserId(@PathVariable(name = "userId") String userId) {
        return ApiResponse.ok(userService.findByUserId(userId));
    }

    @PutMapping("")
    public ApiResponse<UserResDto> updateUser(@RequestBody UserUpdateReqDto request) {
        return ApiResponse.ok(userService.updateUser(request.toUserDto()));
    }

    @DeleteMapping("{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable(name = "userId") String userId) {
        userService.deleteUser(userId);
        return ApiResponse.ok();
    }
}
