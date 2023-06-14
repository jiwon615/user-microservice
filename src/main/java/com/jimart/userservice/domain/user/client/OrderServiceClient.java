package com.jimart.userservice.domain.user.client;

import com.jimart.userservice.core.common.ApiResponse;
import com.jimart.userservice.domain.user.dto.OrderResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service") // 호출하려는 마이크로서비스 이름
public interface OrderServiceClient {

    @GetMapping("/order-service/v1/order/{userId}")
    ApiResponse<List<OrderResDto>> getUserOrders(@PathVariable String userId);
}
