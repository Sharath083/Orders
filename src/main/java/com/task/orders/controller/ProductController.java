package com.task.orders.controller;

import com.task.orders.entity.ProductsEntity;
import com.task.orders.service.dao.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
@CrossOrigin(origins = "https://calm-kringle-dbfd63.netlify.app")

public class ProductController {
    @Autowired
    private ProductsDao productService;
    @PostMapping("/add")
    public List<ProductsEntity> addProduct(@RequestBody List<ProductsEntity> products){
        return productService.addProduct(products);
    }
}
