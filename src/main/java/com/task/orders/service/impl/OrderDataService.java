package com.task.orders.service.impl;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderData;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.entity.ProductsEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderDataRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrderDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderDataService implements OrderDataInterface {
    public static final String ORDER_PLACED_SUCCESSFULLY = "Order Placed Successfully.Order Id: ";
    public static final String ORDER_UPDATED_SUCCESSFULLY = "Order Updated Successfully";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String QUANTITY_IS_OUT_OF_RANGE = "Quantity is out of range";
    public static final String ORDER_NOT_FOUND = "Orders not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String UNABLE_TO_PROCESS_REQUEST = "Unable to Process Request";
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private OrderDataRepo orderDataRepo;

    @Override
    public BaseResponse createOrder(OrderRequest orderData, UUID userId) {
        UUID orderId = UUID.randomUUID();
        List<OrderDataEntity> orders = orderData.getOrderDetails()
                .stream().map(orderDetails -> orderHelper(orderDetails, orderId, userId)
                ).toList();
        return response(orderDataRepo.saveAll(orders),
                ORDER_PLACED_SUCCESSFULLY + orderId);

    }

    private OrderDataEntity orderHelper(OrderData orderDetails, UUID orderId, UUID userEntity) {
        OrderDataEntity entity = new OrderDataEntity();
        entity.setOrderId(orderId);
        entity.setOrderData(orderId + orderDetails.getProductId());
        entity.setUserId(userEntity);
        entity.setProductId(
                isValidProduct(UUID.fromString(orderDetails.getProductId())));
        entity.setQuantity(orderDetails.getQuantity());
        return entity;
    }

    @Override
    public BaseResponse updateOrders(OrderRequest order, String type, UUID userId) throws CommonException {
        var orderId = order.getId();
        try {
            if (Objects.equals(type, "add") || Objects.equals(type, "update")) {
                var data = order.getOrderDetails()
                        .stream().map(orderData -> {
                                    isValidProduct(UUID.fromString(orderData.getProductId()));
                                    return addOrUpdateHelper(orderData, orderId, userId);
                                }
                        ).toList();

                return response(data, ORDER_UPDATED_SUCCESSFULLY);
            } else if (type.equals("remove")) {
                var data = order.getOrderDetails().stream().map(orderData -> {
                    isValidProduct(UUID.fromString(orderData.getProductId()));
                    return removeOrderHelper(orderData, orderId);
                }).toList();
                return response(data, ORDER_UPDATED_SUCCESSFULLY);
            } else {
                throw new CommonException(HttpStatus.BAD_REQUEST.toString(),INVALID_REQUEST +" : "+type);
            }
        } catch (IllegalArgumentException e) {
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        }
    }

    @Override
    public BaseResponse deleteOrderDetails(UUID orderId) throws CommonException {
        if (orderDataRepo.deleteByOrderId(orderId)) {
            return new BaseResponse(HttpStatus.OK.toString(),
                    orderId.toString() + " has Deleted Successfully");
        } else {
            return new BaseResponse(HttpStatus.BAD_REQUEST.toString(), ORDER_NOT_FOUND);
        }

    }

    @Transactional
    @Override
    public BaseResponse deleteProductFromOrder(OrderRequest orderData) {
        var orderId = orderData.getId();
        orderData.getOrderDetails().forEach(orderDetails -> {
                    var pData = orderDataRepo.findByOrderData(orderId + orderDetails.getProductId());
                    if (pData != null) {
                        orderDataRepo.delete(pData);
                    } else {
                        throw new CommonException(HttpStatus.BAD_REQUEST.toString(),
                                "Product Id " + orderDetails.getProductId() + " not found for order");
                    }
                }
        );
        return new BaseResponse(HttpStatus.OK.toString(), "Removed products from Order");
    }

    @Override
    public List<OrderResponse> getAllOrders(UUID userId) throws CommonException {
        List<OrderResponse> orders = new ArrayList<>();
        Map<UUID, Pair<List<OrderData>, AtomicInteger>> orderMaps = new HashMap<>();
        var data = orderDataRepo.findAllByUserId(userId);
        if (data.isEmpty()) {
            throw new CommonException(HttpStatus.OK.toString(), ORDER_NOT_FOUND);
        }
        for (OrderDataEntity orderData : data) {
            UUID orderId = orderData.getOrderId();
            OrderData orderDataDto = new OrderData(
                    orderData.getProductId().getId().toString(),
                    orderData.getProductId().getName(),
                    orderData.getQuantity()
            );
            orderMaps.compute(orderId, (key, value) -> {
                if (value == null) {
                    List<OrderData> orderDataList = new ArrayList<>();
                    orderDataList.add(orderDataDto);
                    AtomicInteger price = new AtomicInteger(orderData.getQuantity() * orderData.getProductId().getPrice());
                    return Pair.of(orderDataList, price);
                } else {
                    value.getFirst().add(orderDataDto);
                    value.getSecond().addAndGet(orderData.getQuantity() * orderData.getProductId().getPrice());
                    return value;
                }
            });
        }

        for (Map.Entry<UUID, Pair<List<OrderData>, AtomicInteger>> entry : orderMaps.entrySet()) {
            orders.add(new OrderResponse(entry.getKey(),
                    entry.getValue().getFirst(),
                    entry.getValue().getSecond().intValue()));
        }
//        Map<UUID, List<OrderData>> orderMap = data.stream()
//                .collect(Collectors.groupingBy(
//                        OrderDataEntity::getOrderId,
//                        Collectors.mapping(orderData -> new OrderData(
//                                orderData.getProductId().getId().toString(),
//                                orderData.getProductId().getName(),
//                                orderData.getQuantity()
//                        ), Collectors.toList())
//                ));
//        var s=data.stream().collect(Collectors.toMap(OrderDataEntity::getOrderId, orderData -> {
//            price.addAndGet(orderData.getQuantity() * orderData.getProductId().getPrice());
//            return new OrderData(
//                    orderData.getProductId().getId().toString(),
//                    orderData.getProductId().getName(),
//                    orderData.getQuantity());
//        }));
//        for (Map.Entry<UUID, List<OrderData>> entry : orderMap.entrySet()){
//            orders.add(new OrderResponse(entry.getKey(),entry.getValue()));
//        }
//        var pData=data.stream().map(orderData -> {
//            price.addAndGet(orderData.getQuantity() * orderData.getProductId().getPrice());
//                    return new OrderData(
//                            orderData.getProductId().getId().toString(),
//                            orderData.getProductId().getName(),
//                            orderData.getQuantity());
//                }
//        ).toList();
//        return new OrderResponse(pData,price.intValue());
        return orders;
    }

    @Override
    public OrderResponse getOrder(UUID orderId) {
        AtomicInteger price = new AtomicInteger();
        var data = orderDataRepo.findAllByOrderId(orderId);
        if (data.isEmpty()) {
            throw new CommonException(HttpStatus.OK.toString(), ORDER_NOT_FOUND);
        }
        var pData = data.stream().map(orderData -> {
                    price.addAndGet(orderData.getQuantity() * orderData.getProductId().getPrice());
                    return new OrderData(
                            orderData.getProductId().getId().toString(),
                            orderData.getProductId().getName(),
                            orderData.getQuantity());
                }
        ).toList();
        return new OrderResponse(orderId, pData, price.intValue());
    }

    private OrderDataEntity addOrUpdateHelper(OrderData orderData, String orderId, UUID userId) {
        var pData = orderDataRepo.findByOrderData(orderId + orderData.getProductId());
        if (pData != null) {
            pData.setQuantity(pData.getQuantity() + orderData.getQuantity());
            return orderDataRepo.save(pData);
        } else {
            return orderDataRepo.save(orderHelper(orderData, UUID.fromString(orderId), userId));
        }
    }

    private OrderDataEntity removeOrderHelper(OrderData orderData, String orderId) {

        var pData = orderDataRepo.findByOrderData(orderId + orderData.getProductId());
        if (pData != null) {
            if (pData.getQuantity() > orderData.getQuantity()) {
                pData.setQuantity(pData.getQuantity() - orderData.getQuantity());
                return orderDataRepo.save(pData);
            } else if (pData.getQuantity() < orderData.getQuantity()) {
                throw new CommonException(HttpStatus.BAD_REQUEST.toString(),QUANTITY_IS_OUT_OF_RANGE);
            }
            orderDataRepo.delete(pData);
            return pData;
        } else {
            throw new CommonException(HttpStatus.BAD_REQUEST.toString(),ORDER_NOT_FOUND);
        }
    }


    private ProductsEntity isValidProduct(UUID productId) {
        return productsRepo.findById(productId).orElseThrow(
                () -> new CommonException(HttpStatus.BAD_REQUEST.toString(),PRODUCT_NOT_FOUND));
    }

    private <T> BaseResponse response(List<T> data, String message) {
        return data.isEmpty() ?
                new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), UNABLE_TO_PROCESS_REQUEST)
                : new BaseResponse(HttpStatus.OK.toString(), message);
    }
}
