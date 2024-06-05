package com.task.orders.service.dao;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import com.task.orders.exception.CommonException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrdersDao {
    BaseResponse createOrder(OrderEntity orderEntity) throws CommonException;
    BaseResponse updateOrderDetails(UUID orderId, List<OrderDetailsEntity> orderDetails);
    OrderEntity getOrderDetails(UUID orderId) throws CommonException;
    BaseResponse deleteOrderDetails(UUID orderId) throws CommonException;
    OrderEntity updateOrder(String type, OrderRequest order) throws CommonException;
}
