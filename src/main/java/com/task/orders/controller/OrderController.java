package com.task.orders.controller;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import com.task.orders.entity.UserEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.repository.UserRepo;
import com.task.orders.service.dao.OrderDetailsDao;
import com.task.orders.service.dao.OrdersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
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




    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderRequest order) throws CommonException {
        String d=RedisSessionAuthenticationFilter.getUserData().getUserId();
        final UUID userId= UUID.fromString(d);
        return ordersService.createOrder(order,userId);
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order created");
    }
    @PostMapping("/update")
    public List<OrderDetailsEntity> updateOrder(@RequestParam String type,@RequestBody OrderRequest order) throws CommonException {
        return orderDetailsDao.updateOrders(order,type);
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order updated");

    }

}

