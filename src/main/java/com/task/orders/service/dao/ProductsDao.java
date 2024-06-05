package com.task.orders.service.dao;

import com.task.orders.entity.ProductsEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductsDao {
    List<ProductsEntity> addProduct(List<ProductsEntity> product);
}
