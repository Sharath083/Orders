package com.task.orders.service.impl;

import com.task.orders.constants.Messages;
import com.task.orders.constants.StatusCodes;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderItemsRepo;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.task.orders.constants.InfoId.INVALID_INPUT_ID;
import static com.task.orders.constants.InfoId.VALID;
import static com.task.orders.constants.Messages.ORDER_NOT_FOUND;

@Service
public class OrdersImpl implements OrdersDao {
    @Autowired
    OrderRepo orderRepository;
    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderItemsRepo orderItemsRepo;

    @Override
    public OrderEntity createOrder(OrderRequest order, UUID userId) throws CommonException {
        UUID orderId = UUID.randomUUID();
        OrderEntity orderEntity = orderHelper(order, userId, orderId);
        orderEntity.setId(orderId);
        orderEntity.setOrderedAt(LocalDateTime.now());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(orderEntity);
    }

    private OrderEntity orderHelper(OrderRequest order, UUID userId, UUID orderId) {

        OrderEntity orderEntity = new OrderEntity();
        List<OrderDetailsEntity> orderDetails = order.getOrderDetails().stream().map(orderData -> {
                    OrderDetailsEntity entity = new OrderDetailsEntity();
                    entity.setProductId(
                            UUID.fromString(orderData.getProductId()));
                    entity.setQuantity(orderData.getQuantity());
                    entity.setOrderUuid(orderId);
                    entity.setOrderData(orderId.toString() + orderData.getProductId());
                    return entity;
                }
        ).toList();

        orderItemsRepo.saveAll(orderDetails);

        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(Messages.USER_NOT_FOUND));
        orderEntity.setUserId(userEntity);
        return orderEntity;
    }

//    @Override
//    public OrderEntity updateOrder(OrderRequest orderRequest, UUID userId) throws CommonException {
//
//        Optional<OrderEntity> existingOrderOpt = orderRepository.findById(UUID.fromString(orderRequest.getId()));
//        if (existingOrderOpt.isPresent()) {
//            OrderEntity existingOrder = existingOrderOpt.get();
//            List<OrderDetailsEntity> existingOrderDetails = existingOrder.getOrderDetails();
//            var existingDetailsMap = existingOrderDetails.stream()
//                    .collect(Collectors.toMap(OrderDetailsEntity::getProductId, details -> details));
//
//            for (OrderData orderData : orderRequest.getOrderDetails()) {
//                UUID productId = UUID.fromString(orderData.getProductId());
//                if (existingDetailsMap.containsKey(productId)) {
//                    OrderDetailsEntity existingDetail = existingDetailsMap.get(productId);
//                    existingDetail.setQuantity(existingDetail.getQuantity() + orderData.getQuantity());
//                } else {
//                    existingOrderDetails.addAll(orderHelper(orderRequest, userId, UUID.fromString(orderRequest.getId())).getOrderDetails());
//                }
//            }
//            existingOrder.setOrderDetails(existingOrderDetails);
//            existingOrder.setUpdatedAt(LocalDateTime.now());
//            return orderRepository.save(existingOrder);
//        } else {
//            throw new CommonException(HttpStatus.BAD_REQUEST.toString(), "Order not found.");
//        }
//    }
//    @Override
//    public BaseResponse updateOrderDetails(UUID orderId, List<OrderDetailsEntity> orderDetails) {
//        return orderRepository.updateOrderDetails(orderId, orderDetails) > 0 ? new BaseResponse("1",
//                orderId.toString() + "is Updated") :
//                new BaseResponse("0", orderId.toString() + "Unable to Update Order");
//    }

    @Override
    public OrderEntity getOrderDetails(UUID orderId) throws CommonException {
        var data = orderRepository.findById(orderId).orElse(null);
        if (data == null) {
            throw new CommonException(INVALID_INPUT_ID,
                    ORDER_NOT_FOUND, StatusCodes.EMPTY);
        }
        return data;
    }

    @Transactional
    @Override
    public BaseResponse deleteOrderDetails(UUID orderId) throws CommonException {
        var data = getOrderDetails(orderId);
        if (data != null) {
            orderItemsRepo.deleteByOrderUuid(orderId);
            orderRepository.deleteById(orderId);
        }
        return new BaseResponse(VALID, orderId.toString() + Messages.ORDER_IS_DELETED);
    }

//    @Override
//    public OrderEntity updateOrder(String type, OrderRequest order) throws CommonException {
//        return switch (type) {
//            case "add", "update" -> sub(order);
//            default -> null;
//        };
//    }

//    private OrderEntity sub(OrderRequest orderData) throws CommonException {
//
//        OrderEntity orderEntity=orderHelper(orderData);
//            return orderRepository.save(orderEntity);
//
//    }


//    @Override
//    public BaseResponse update(OrderRequest order, UUID userId) {
//        var orderId = UUID.fromString(order.getId());
//        var s = orderRepository.findById(orderId).orElse(null);
//        System.out.println(s);
//        var userEntity = userRepo.findById(userId).orElse(null);
//
//        if (s == null) {
//            throw new IllegalArgumentException("Order not found");
//        } else {
//            List<OrderDetailsEntity> orderDetailsEntities = order.getOrderDetails().stream().map(orderData -> {
//                        UUID uuid = UUID.fromString(orderData.getProductId());
//                        OrderDetailsEntity orderDetails = new OrderDetailsEntity();
//                        orderDetails.setProductId(uuid);
//                        orderDetails.setQuantity(orderData.getQuantity());
//                        return orderDetails;
//                    }
//            ).toList();
//            OrderEntity orderEntity = new OrderEntity();
//            orderEntity.setUserId(userEntity);
//            orderEntity.setOrderDetails(orderDetailsEntities);
//            orderEntity.setUpdatedAt(LocalDateTime.now());
////            System.out.println(s.getOrderDetails());
//            s.getOrderDetails().addAll(orderDetailsEntities);
//            s.setUpdatedAt(LocalDateTime.now());
////            System.out.println(s.getOrderDetails());
//            orderRepository.save(s);
//            return new BaseResponse("1",
//                    "Order is Updated");
//
//        }
//    }
////
//    @Override
//    public List<OrderDetailsEntity> updateOrders(OrderRequest order, String type) throws CommonException {
//        var orderId = order.getId();
//        if (Objects.equals(type, "add") || Objects.equals(type, "update")) {
//            return order.getOrderDetails().stream().map(orderData -> {
//                        var pData = orderItemsRepo.findByOrderData(orderId + orderData.getProductId());
//                        if (pData != null) {
//                            pData.setQuantity(pData.getQuantity() + orderData.getQuantity());
//                            return orderItemsRepo.save(pData);
//                        } else {
//                            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
//                            orderDetails.setOrderId(UUID.fromString(orderId));
//                            orderDetails.setProductId(UUID.fromString(orderData.getProductId()));
//                            orderDetails.setQuantity(orderData.getQuantity());
//                            orderDetails.setOrderData(orderId + orderData.getProductId());
//                            return orderItemsRepo.save(orderDetails);
//                        }
//                    }
//            ).toList();
//        } else if (type.equals("remove")) {
//            return order.getOrderDetails().stream().map(orderData -> {
//                        var pData = orderItemsRepo.findByOrderData(orderId + orderData.getProductId());
//                        if (pData != null) {
//                            orderItemsRepo.delete(pData);
//                            return pData;
//                        } else {
//                            throw new IllegalArgumentException("Order not found");
//                        }
//                    }
//            ).toList();
//
//        } else {
//            throw new CommonException(HttpStatus.BAD_REQUEST.toString(), "Invalid request");
//        }
//
//    }
}
