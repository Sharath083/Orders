package com.task.orders.controller;

import com.task.orders.constants.ApiEndPoints;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.exception.CommonException;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrderDataInterface;
import com.task.orders.service.dao.OrderDetailsDao;
import com.task.orders.service.dao.OrdersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndPoints.ORDER)

public class OrderController {
    @Autowired
    OrdersDao ordersService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    OrderDetailsDao orderDetailsDao;
    @Autowired
    OrderDataInterface orderDataInterface;
    @Autowired
    RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;


    @PostMapping
    public BaseResponse createOrder(@RequestBody OrderRequest order) throws CommonException {
        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId = UUID.fromString(d);
        return orderDataInterface.createOrder(order, userId);
    }

    @PostMapping(ApiEndPoints.UPDATE)
    public BaseResponse updateOrder(@RequestParam String type, @RequestBody OrderRequest order) throws CommonException {
        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId = UUID.fromString(d);
        return orderDataInterface.updateOrders(order, type, userId);
    }

    @GetMapping(ApiEndPoints.ID)
    public ResponseEntity<OrderResponse> getOrder(@PathVariable(ApiEndPoints.PATH_ID) UUID orderId) throws CommonException {
        return ResponseEntity.ok(orderDataInterface.getOrder(orderId));
    }

    @DeleteMapping(ApiEndPoints.ID)
    public BaseResponse deleteOrder(@PathVariable(ApiEndPoints.PATH_ID) String id) throws CommonException {
        return orderDataInterface.deleteOrderDetails(UUID.fromString(id));
    }

    @DeleteMapping(ApiEndPoints.DELETE_PRODUCTS)
    public BaseResponse deleteProducts(@RequestBody OrderRequest orderRequest) throws CommonException {
        System.out.println(orderRequest);
        return orderDataInterface.deleteProductFromOrder(orderRequest);
    }


    @GetMapping(ApiEndPoints.SUMMARY)
    public ResponseEntity<List<OrderResponse>> getSummary() {
        String d = redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId = UUID.fromString(d);
        return ResponseEntity.ok(orderDataInterface.getAllOrders(userId));
    }

}

