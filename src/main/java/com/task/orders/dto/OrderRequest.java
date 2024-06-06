package com.task.orders.dto;

import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String id;
    private List<OrderData> orderDetails;
}
