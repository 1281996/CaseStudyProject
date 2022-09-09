package com.cog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cog.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	List<Payment> findByEmail(String email);
}
