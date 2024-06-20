package com.task.orders.entity;


import com.task.orders.constants.TableConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;

    private UUID userId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = TableConstants.PRODUCT_ID,referencedColumnName = TableConstants.ID)
    private ProductsEntity productId;

    private String orderData;

    private int quantity;
}
