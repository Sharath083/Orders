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
    @PostMapping
    public BaseResponse createOrder(@RequestBody OrderRequest order) throws CommonException {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity userEntity = userRepo.findById(UUID.fromString(RedisSessionAuthenticationFilter.getUserData().getUserId())).orElse(null);
        orderEntity.setUserId(userEntity);
        orderEntity.setOrderedAt(order.getOrderedAt());
        List<Pair<UUID,Integer>> list=order.getOrderDetails().stream().map(da-> Pair.of(UUID.fromString(da.getProductId()), da.getQuantity())
        ).toList();
        List<OrderDetailsEntity> orderDetailsEntities = new ArrayList<>();
        for (Pair<UUID, Integer> uuid : list) {
            var d=productsRepo.findById(uuid.getFirst()).orElse(null);
            orderDetailsEntities.add(new OrderDetailsEntity(
                    null,d, uuid.getSecond()
                    )
                    );
        }
        orderEntity.setOrderDetails(orderDetailsEntities);
        ordersService.createOrder(orderEntity);
        return new BaseResponse(HttpStatus.CREATED.toString(),"Order created");
    }
    @PostMapping("/update")
    public OrderEntity updateOrder(@RequestParam String type,@RequestBody OrderRequest order) throws CommonException {
        return ordersService.updateOrder(type,order);
//        return new BaseResponse(HttpStatus.CREATED.toString(),"Order updated");

    }

}
