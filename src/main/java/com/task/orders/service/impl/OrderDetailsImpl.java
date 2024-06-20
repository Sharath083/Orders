package com.task.orders.service.impl;

import com.task.orders.constants.Constants;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.OrderData;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderItemsRepo;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.service.dao.OrderDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.Messages.*;

@Service
public class OrderDetailsImpl implements OrderDetailsDao {
    @Autowired
    OrderItemsRepo orderItemsRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductsRepo productsRepo;

    @Override
    public List<OrderDetailsEntity> updateOrders(OrderRequest order, String type) throws CommonException {
        var orderId = order.getId();
        try {
            if (Objects.equals(type, Constants.ADD) || Objects.equals(type, Constants.UPDATE)) {
                return order.getOrderDetails()
                        .stream().map(orderData -> {
                                    isValidProduct(UUID.fromString(orderData.getProductId()));
                                    return addOrUpdateHelper(orderData, orderId);
                                }
                        ).toList();
            } else if (type.equals(Constants.REMOVE)) {
                return order.getOrderDetails().stream().map(orderData -> {
                    isValidProduct(UUID.fromString(orderData.getProductId()));
                    return removeOrderHelper(orderData, orderId);
                }).toList();
            } else {
                throw new CommonException(
                        INVALID_INPUT_ID,
                        INVALID_REQUEST,
                        StatusCodes.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            throw new CommonException(INVALID_INPUT_ID,
                    e.getLocalizedMessage(), StatusCodes.BAD_REQUEST);
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
                throw new CommonException(INVALID_INPUT_ID,
                        QUANTITY_IS_OUT_OF_RANGE,
                        StatusCodes.BAD_REQUEST);
            }
            orderItemsRepo.delete(pData);
            return pData;
        } else {
            throw new CommonException(INVALID_INPUT_ID,
                    ORDER_NOT_FOUND,
                    StatusCodes.EMPTY);
        }
    }


    private void isValidProduct(UUID productId) {
        productsRepo.findById(productId).orElseThrow(
                () -> new CommonException(INVALID_INPUT_ID,
                        PRODUCT_NOT_FOUND,
                        StatusCodes.EMPTY));
    }


    private void isLast(UUID orderId) {
        var data = orderItemsRepo.findByOrderUuid(orderId);
        if (data.size() == 1) {
            orderRepo.deleteById(orderId);
        }
    }
}
