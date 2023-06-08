package com.jimart.userservice.domain.user.controller;

import com.jimart.userservice.domain.user.dto.Greeting;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTestController {

    private final Environment env;
    private final Greeting greeting;

    @GetMapping("/health_check")
    @Timed(value="users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service : " +
                        "localServerPort => %s, " +
                        "serverPort=> %s, " +
                        "jwt secret=> %s, " +
                        "jwt expiration=> %s",
                        "test message=> %s",
                env.getProperty("local.server.port"),
                env.getProperty("server.port"),
                env.getProperty("jwt.secret"),
                env.getProperty("test.message"));
    }

    @GetMapping("/welcome")
    @Timed(value="users.welcome", longTask = true)
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return greeting.getMessage();
    }
}
