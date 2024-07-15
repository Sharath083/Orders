package com.task.orders.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionData {
    private String userId;
    private String username;
    private String email;
    private String role;
}