package com.task.orders.service.dao;

import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.exception.CommonException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderDetailsDao{
    List<OrderDetailsEntity> updateOrders(OrderRequest order, String type) throws CommonException;

}
