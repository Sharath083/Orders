package com.task.orders.controller;

import com.task.orders.entity.ProductsEntity;
import com.task.orders.service.dao.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private ProductsDao productService;
    @PostMapping("/add")
    public List<ProductsEntity> addProduct(@RequestBody List<ProductsEntity> products){
        return productService.addProduct(products);
    }
}
