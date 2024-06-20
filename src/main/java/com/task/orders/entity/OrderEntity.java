package com.task.orders.entity;

import com.task.orders.constants.TableConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEntity {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = TableConstants.USER_ID,referencedColumnName = TableConstants.ID)
    private UserEntity userId;

    private LocalDateTime orderedAt;

    private LocalDateTime updatedAt;

}

