package com.task.orders.service.impl;

import com.task.orders.dto.OrderData;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderItemsRepo;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
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
    @Autowired
    private ProductsRepo productsRepo;

    @Override
    public List<OrderDetailsEntity> updateOrders(OrderRequest order, String type) throws CommonException {
        var orderId = order.getId();
        try {
            if (Objects.equals(type, "add") || Objects.equals(type, "update")) {
                return order.getOrderDetails()
                        .stream().map(orderData -> {
                                    isValidProduct(UUID.fromString(orderData.getProductId()));
                                    return addOrUpdateHelper(orderData, orderId);
                                }
                        ).toList();
            } else if (type.equals("remove")) {
                return order.getOrderDetails().stream().map(orderData -> {
                    isValidProduct(UUID.fromString(orderData.getProductId()));
                    return removeOrderHelper(orderData, orderId);
                }).toList();
            } else {
                throw new CommonException("00","Invalid request");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() +"  "+e.getLocalizedMessage());
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage());
        }
    }

    private OrderDetailsEntity addOrUpdateHelper(OrderData orderData, String orderId) {
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

    private OrderDetailsEntity removeOrderHelper(OrderData orderData, String orderId) {

        var pData = orderItemsRepo.findByOrderData(orderId + orderData.getProductId());
        if (pData != null) {
            isLast(UUID.fromString(orderId));
            if (pData.getQuantity() > orderData.getQuantity()) {
                pData.setQuantity(pData.getQuantity() - orderData.getQuantity());
                return orderItemsRepo.save(pData);
            } else if (pData.getQuantity() < orderData.getQuantity()) {
                throw new IllegalArgumentException("Quantity is out of range");
            }
            orderItemsRepo.delete(pData);
            return pData;
        } else {
            throw new CommonException("00","Order not found");
        }
    }


private void isValidProduct(UUID productId) {
    productsRepo.findById(productId).orElseThrow(
            () -> new CommonException("00","Product not found"));
}


private void isLast(UUID orderId) {
    var data = orderItemsRepo.findByOrderUuid(orderId);
    if (data.size() == 1) {
        orderRepo.deleteById(orderId);
    }
}
}
