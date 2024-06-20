package com.task.orders.service.impl;

import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderData;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.entity.ProductsEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.constants.Messages;
import com.task.orders.repository.OrderDataRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrderDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.task.orders.constants.Constants.*;
import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.InfoId.VALID;

@Service
public class OrderDataService implements OrderDataInterface {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    OrderDataRepo orderDataRepo;

    @Override
    public BaseResponse createOrder(OrderRequest orderData, UUID userId) {
        UUID orderId = UUID.randomUUID();
        List<OrderDataEntity> orders = orderData.getOrderDetails()
                .stream().map(orderDetails -> orderHelper(orderDetails, orderId, userId)
                ).toList();
        return response(orderDataRepo.saveAll(orders),
                Messages.ORDER_PLACED_SUCCESSFULLY + orderId);

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
            if (Objects.equals(type, ADD) || Objects.equals(type, UPDATE)) {
                var data = order.getOrderDetails()
                        .stream().map(orderData -> {
                                    isValidProduct(UUID.fromString(orderData.getProductId()));
                                    return addOrUpdateHelper(orderData, orderId, userId);
                                }
                        ).toList();

                return response(data, Messages.ORDER_UPDATED_SUCCESSFULLY);
            } else if (type.equals(REMOVE)) {
                var data = order.getOrderDetails().stream().map(orderData -> {
                    isValidProduct(UUID.fromString(orderData.getProductId()));
                    return removeOrderHelper(orderData, orderId);
                }).toList();
                return response(data, Messages.ORDER_UPDATED_SUCCESSFULLY);
            } else {
                throw new CommonException(INVALID_INPUT_ID, Messages.INVALID_REQUEST + " : " + type,StatusCodes.BAD_REQUEST);

            }
        } catch (IllegalArgumentException e) {
            throw new CommonException(INVALID_INPUT_ID, e.getMessage(),StatusCodes.UNKNOWN);
        }
    }

    @Override
    public BaseResponse deleteOrderDetails(UUID orderId) throws CommonException {
        if (orderDataRepo.deleteByOrderId(orderId)) {
            return new BaseResponse(VALID,
                    orderId.toString() + Messages.ORDER_HAS_DELETED_SUCCESSFULLY);
        } else {
            return new BaseResponse(VALID, Messages.ORDER_NOT_FOUND);
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
                        throw new CommonException(VALID,
                                orderDetails.getProductId() + Messages.PRODUCT_ID_NOT_FOUND_FOR_ORDER,
                                StatusCodes.SUCCESS);
                    }
                }
        );
        return new BaseResponse(VALID, Messages.REMOVED_PRODUCTS_FROM_ORDER);
    }

    @Override
    public List<OrderResponse> getAllOrders(UUID userId) throws CommonException {
        List<OrderResponse> orders = new ArrayList<>();
        Map<UUID, Pair<List<OrderData>, AtomicInteger>> orderMaps = new HashMap<>();
        var data = orderDataRepo.findAllByUserId(userId);
        if (data.isEmpty()) {
            throw new CommonException(VALID, Messages.ORDER_NOT_FOUND,StatusCodes.EMPTY);
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
            throw new CommonException(VALID, Messages.ORDER_NOT_FOUND, StatusCodes.SUCCESS);
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
                throw new CommonException(VALID, Messages.QUANTITY_IS_OUT_OF_RANGE, StatusCodes.SUCCESS);
            }
            orderDataRepo.delete(pData);
            return pData;
        } else {
            throw new CommonException(INVALID_INPUT_ID, Messages.ORDER_NOT_FOUND, StatusCodes.BAD_REQUEST);

        }
    }


    private ProductsEntity isValidProduct(UUID productId) {
        return productsRepo.findById(productId).orElseThrow(
                () -> new CommonException(INVALID_INPUT_ID, Messages.PRODUCT_NOT_FOUND, StatusCodes.BAD_REQUEST));
    }

    private <T> BaseResponse response(List<T> data, String message) {
        return data.isEmpty() ?
                new BaseResponse(INVALID_INPUT_ID, Messages.UNABLE_TO_PROCESS_REQUEST)
                : new BaseResponse(VALID, message);
    }
}
