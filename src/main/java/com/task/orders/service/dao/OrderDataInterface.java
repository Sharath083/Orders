package com.task.orders.service.dao;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.exception.CommonException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderDataInterface {
    BaseResponse createOrder(OrderRequest orderData, UUID userId);

    BaseResponse updateOrders(OrderRequest order, String type, UUID userId) throws CommonException;

    @Transactional
    BaseResponse deleteOrderDetails(UUID orderId) throws CommonException;

    BaseResponse deleteProductFromOrder(OrderRequest orderData);

    List<OrderResponse> getAllOrders(UUID userId);

    OrderResponse getOrder(UUID orderId);
}
