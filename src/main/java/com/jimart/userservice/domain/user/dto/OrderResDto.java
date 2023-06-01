package com.jimart.userservice.domain.user.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResDto {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<Long> productIds;
}
