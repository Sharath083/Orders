package com.task.orders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String password;
    private int age;
    private String email;
    private String phone;
    private String gender;

//    @ElementCollection
//    private List<UserD> userDS;
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Embeddable
//    public static class UserD{
//        private String name;
//        private String password;
//    }
}
