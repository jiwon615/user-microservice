package com.jimart.userservice.domain.user.service;

import com.jimart.userservice.core.common.ApiResponse;
import com.jimart.userservice.core.exception.CustomException;
import com.jimart.userservice.domain.user.client.UserOrderServiceClient;
import com.jimart.userservice.domain.user.constant.UserAuthorityType;
import com.jimart.userservice.domain.user.dto.OrderResDto;
import com.jimart.userservice.domain.user.dto.UserDto;
import com.jimart.userservice.domain.user.dto.UserOrderResDto;
import com.jimart.userservice.domain.user.dto.UserResDto;
import com.jimart.userservice.domain.user.entity.User;
import com.jimart.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jimart.userservice.core.exception.ErrorMsgType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final Environment env;
    private final UserOrderServiceClient userOrderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserResDto saveUser(UserDto request) {
        checkIfUserIdIsDuplicated(request.getUserId());

        request.setAuthority(UserAuthorityType.USER);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userRepository.save(request.toEntity());
        return UserResDto.of(user);
    }

    private void checkIfUserIdIsDuplicated(String userId) {
        Optional<User> findUserId = userRepository.findByUserId(userId);
        findUserId.ifPresent(u -> {
            throw new CustomException(USER_DUPLICATED);
        });
    }

    @Override
    public List<UserResDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserResDto findByUserId(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        return userOpt.map(UserResDto::of)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    @Override
    public UserResDto updateUser(UserDto request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return UserResDto.of(user);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        userOpt.ifPresent(userRepository::delete);
    }

    @Override
    public UserOrderResDto findUserWithOrders(String userId) {
        UserResDto findUser = findByUserId(userId);
//        List<OrderResDto> orders = getOrdersByRestTemplate(userId); // orders By RestTemplate
//        List<OrderResDto> orders = userOrderServiceClient.getUserOrders(userId).getData(); // orders By FeignClient + ErrorDecoder

        log.info("=== Order Microservice 호출 전 ===");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        List<OrderResDto> orders = circuitbreaker.run(() -> userOrderServiceClient.getUserOrders(userId).getData(),
                throwable -> new ArrayList<>());
        log.info("=== Order Microservice 호출 후 ===");

        return UserOrderResDto.builder()
                .userId(findUser.getUserId())
                .name(findUser.getName())
                .email(findUser.getEmail())
                .authority(findUser.getAuthority())
                .orders(orders)
                .build();
    }

    private List<OrderResDto> getOrdersByRestTemplate(String userId) {
        String rawOrderServiceUrl = env.getProperty("order_service.url");
        if (!StringUtils.hasText(rawOrderServiceUrl)) {
            throw new CustomException(PROPERTY_NOT_FOUND);
        }
        String orderUrl = String.format(rawOrderServiceUrl, userId);
        ResponseEntity<ApiResponse<List<OrderResDto>>> orderResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return orderResponse.getBody().getData();
    }
}
