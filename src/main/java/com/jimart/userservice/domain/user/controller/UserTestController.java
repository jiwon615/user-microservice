package com.jimart.userservice.domain.user.controller;

import com.jimart.userservice.domain.user.dto.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserTestController {

    private final Environment env;
    private final Greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return String.format("User Service is Working on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return greeting.getMessage();
    }
}
