package com.task.orders.service.impl;

import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderItemsRepo;
import com.task.orders.repository.OrderRepo;
import com.task.orders.service.dao.OrderDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Service
public class OrderDetailsImpl implements OrderDetailsDao {
    @Autowired
    private OrderItemsRepo orderItemsRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Override
    public List<OrderDetailsEntity> updateOrders(OrderRequest order, String type) throws CommonException {
        var orderId = order.getId();
        var orderD=orderRepo.findById(UUID.fromString(orderId));
        if (Objects.equals(type, "add") || Objects.equals(type, "update")) {
            return order.getOrderDetails().stream().map(orderData -> {
                        var pData = orderItemsRepo.findByOrderData(orderId + orderData.getProductId());
                        if (pData != null) {
                            pData.setQuantity(pData.getQuantity() + orderData.getQuantity());
                            return orderItemsRepo.save(pData);
                        } else {
                            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
                            orderDetails.setOrderUuid(UUID.fromString(orderId));
                            orderDetails.setProductId(UUID.fromString(orderData.getProductId()));
                            orderDetails.setQuantity(orderData.getQuantity());

                            orderDetails.setOrderData(orderId + orderData.getProductId());
                            return orderItemsRepo.save(orderDetails);
                        }
                    }
            ).toList();
        } else if (type.equals("remove")) {
            return order.getOrderDetails().stream().map(orderData -> {
                        var pData = orderItemsRepo.findByOrderData(orderId + orderData.getProductId());
                        if (pData != null) {
                            orderItemsRepo.delete(pData);
                            return pData;
                        } else {
                            throw new IllegalArgumentException("Order not found");
                        }
                    }
            ).toList();

        } else {
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(), "Invalid request");
        }

    }
}
