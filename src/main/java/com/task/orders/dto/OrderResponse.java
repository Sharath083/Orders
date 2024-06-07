package com.task.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private List<OrderData> products;
    private int totalPrice;

    public OrderResponse(UUID orderId,List<OrderData> value) {
        this.orderId = orderId;
        this.products = value;
    }
}


