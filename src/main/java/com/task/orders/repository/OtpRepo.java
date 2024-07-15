package com.task.orders.repository;

import com.task.orders.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepo extends JpaRepository<OtpEntity,String> {
    Optional<OtpEntity> findByMail(String mail);
}
