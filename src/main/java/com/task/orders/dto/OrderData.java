package com.task.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.entity.ProductsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Ignore;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData implements Serializable {
    private String productId;
    private String productName;
    private int quantity;

    public OrderData(ProductsEntity productsEntity,int quantity){
        this.productId = productsEntity.getId().toString();
        this.productName = productsEntity.getName();
        this.quantity = quantity;
    }

}
