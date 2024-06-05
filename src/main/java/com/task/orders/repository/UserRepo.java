package com.task.orders.repository;

import com.task.orders.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, UUID> {
//    @Query("select s from UserEntity s where email=:email, password=:password")
//    UserEntity userLogin(@Param("email") String email, @Param("password") String password);
    UserEntity findByEmail(String email);
}
