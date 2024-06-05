package com.task.orders.service.impl;

import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.service.dao.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersImpl implements OrdersDao {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductsRepo productsRepo;

    @Override
    public BaseResponse createOrder(OrderEntity orderEntity) throws CommonException {
        orderRepo.save(orderEntity);
        return new BaseResponse("1",
                "Order is Created");
    }

    @Override
    public BaseResponse updateOrderDetails(UUID orderId, List<OrderDetailsEntity> orderDetails) {
        return orderRepo.updateOrderDetails(orderId, orderDetails)>0 ? new BaseResponse("1",
                orderId.toString()+"is Updated"):
                new BaseResponse("0", orderId.toString()+"Unable to Update Order");
    }

    @Override
    public OrderEntity getOrderDetails(UUID orderId) throws CommonException {
        var data=orderRepo.findById(orderId).orElse(null);
        if(data==null){
            throw new CommonException("0","Unable to find Order");
        }
        return data;
    }

    @Override
    public BaseResponse deleteOrderDetails(UUID orderId) throws CommonException {
        var data = getOrderDetails(orderId);
        if(data!=null){
            orderRepo.deleteById(orderId);
        }
        return new BaseResponse("1",
                orderId.toString()+"is Deleted");
    }

    @Override
    public OrderEntity updateOrder(String type, OrderRequest order) throws CommonException {
        return switch (type) {
            case "add", "update" -> sub(order);
            default -> null;
        };
    }
    private OrderEntity sub(OrderRequest orderData) throws CommonException{
        UUID orderId = UUID.fromString(orderData.getId());
        var order = orderRepo.findById(orderId).orElse(null);
        if(order!=null){
            List<Pair<UUID,Integer>> list=orderData
                    .getOrderDetails().stream()
                    .map(da-> Pair.of(UUID.fromString(da.getProductId()), da.getQuantity())
            ).toList();
            List<OrderDetailsEntity> orderEntities = new ArrayList<>();
            for (Pair<UUID,Integer> pair : list){
            var d=productsRepo.findById(pair.getFirst()).orElse(null);
            orderEntities.add(new OrderDetailsEntity(
                            orderId,d, pair.getSecond())
                    );
            }
            order.getOrderDetails().addAll(orderEntities);
            return orderRepo.save(order);
        }
        throw new CommonException(HttpStatus.BAD_REQUEST.toString(),"Invalid order Id");
    }

}
