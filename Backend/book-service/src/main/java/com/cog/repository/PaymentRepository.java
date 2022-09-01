package com.cog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cog.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
