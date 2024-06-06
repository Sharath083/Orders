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

    OrderEntity createOrder(OrderRequest order, UUID userId) throws CommonException;


    OrderEntity getOrderDetails(UUID orderId) throws CommonException;

    BaseResponse deleteOrderDetails(UUID orderId) throws CommonException;

}
