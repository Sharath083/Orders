package com.task.orders.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class OrderResponse extends BaseResponse {
    private UUID orderId;
    private List<OrderData> products;
    private int totalPrice;
    private String s;

    public OrderResponse(UUID orderId,List<OrderData> value) {
        this.orderId = orderId;
        this.products = value;
    }
    public OrderResponse(UUID orderId,List<OrderData> value,int totalPrice) {
        this.orderId = orderId;
        this.products = value;
        this.totalPrice = totalPrice;

    }

    public OrderResponse(UUID orderId,List<OrderData> value,int totalPrice,String infoId,String infoMsg) {
        this.setInfoId(infoId);
        this.setMessage(infoMsg);
        this.orderId = orderId;
        this.products = value;
        this.totalPrice = totalPrice;

    }

}


