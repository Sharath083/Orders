package com.task.orders.service.impl;


import com.task.orders.entity.ProductsEntity;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.service.dao.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductsImpl implements ProductsDao {
    @Autowired
    private ProductsRepo productsRepo;
    @Override
    public List<ProductsEntity> addProduct(List<ProductsEntity> product) {
        return productsRepo.saveAll(product);
    }
}
