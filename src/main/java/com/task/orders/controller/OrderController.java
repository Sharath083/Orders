package com.task.orders.controller;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrderDataInterface;
import com.task.orders.service.dao.OrderDetailsDao;
import com.task.orders.service.dao.OrdersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersDao ordersService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private OrderDetailsDao orderDetailsDao;
    @Autowired
    private OrderDataInterface orderDataInterface;
    @Autowired
    private RedisSessionAuthenticationFilter redisSessionAuthenticationFilter;



    @PostMapping
    public BaseResponse createOrder(@RequestBody OrderRequest order) throws CommonException {
        String d=redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId= UUID.fromString(d);
        return orderDataInterface.createOrder(order,userId);
//        return ordersService.createOrder(order,userId);
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order created");
    }
    @PostMapping("/update")
    public BaseResponse updateOrder(@RequestParam String type,@RequestBody OrderRequest order) throws CommonException {
//        return orderDetailsDao.updateOrders(order,type);
        String d=redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId= UUID.fromString(d);
        return orderDataInterface.updateOrders(order,type,userId);
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order updated");
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") UUID orderId) throws CommonException {
        return ResponseEntity.ok(orderDataInterface.getOrder(orderId));
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteOrder(@PathVariable("id") String id) throws CommonException {
//        ordersService.deleteOrderDetails(UUID.fromString(id));
        return orderDataInterface.deleteOrderDetails(UUID.fromString(id));
//        return new BaseResponse(HttpStatus.OK.toString(),
//                "Order deleted "+id);

    }
    @DeleteMapping("/delete/products")
    public BaseResponse deleteProducts(@RequestBody OrderRequest orderRequest) throws CommonException {
        System.out.println(orderRequest);
        return orderDataInterface.deleteProductFromOrder(orderRequest);
    }


    @GetMapping("/summary")
    public ResponseEntity<List<OrderResponse>> getSummary() {
        String d=redisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId= UUID.fromString(d);
        return ResponseEntity.ok(orderDataInterface.getAllOrders(userId));
    }

}

