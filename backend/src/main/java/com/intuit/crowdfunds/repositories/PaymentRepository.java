package com.intuit.crowdfunds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.crowdfunds.entites.Transaction;

@Repository
public interface PaymentRepository extends JpaRepository<Transaction,Long> {
	Optional<Transaction> findByOrderId(String orderId);
}
