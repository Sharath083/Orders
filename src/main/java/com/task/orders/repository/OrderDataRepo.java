package com.task.orders.repository;

import com.task.orders.dto.OrderData;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDataRepo extends JpaRepository<OrderDataEntity, UUID> {
    OrderDataEntity findByOrderData(String id);

    OrderDataEntity findByOrderId(UUID orderId);

    boolean deleteByOrderId(UUID orderId);


    List<OrderDataEntity> findAllByUserId(UUID userId);

    List<OrderDataEntity> findAllByOrderId(UUID orderId);

    void deleteByOrderData(String s);
}


