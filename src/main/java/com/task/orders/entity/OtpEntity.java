package com.task.orders.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtpEntity {
    @Id
    private String mail;
    private String otp;
}
