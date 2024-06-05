package com.task.orders.repository;

import com.task.orders.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, UUID> {
}
