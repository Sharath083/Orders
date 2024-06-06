package com.task.orders.repository;

import com.task.orders.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderItemsRepo extends JpaRepository<OrderDetailsEntity, UUID> {
    OrderDetailsEntity findByOrderData(String orderData);
}
