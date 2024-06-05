package com.task.orders.repository;

import com.task.orders.entity.OrderDetailsEntity;
import com.task.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
    @Query("Update OrderEntity u SET u.orderDetails=:details where u.id=:orderId")
    int updateOrderDetails(UUID orderId, List<OrderDetailsEntity> details);

}
