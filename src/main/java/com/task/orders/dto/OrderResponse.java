package com.task.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseResponse {
    private UUID orderId;
    private List<OrderData> products;
    private int totalPrice;

    public OrderResponse(UUID orderId,List<OrderData> value) {
        this.orderId = orderId;
        this.products = value;
    }
}


