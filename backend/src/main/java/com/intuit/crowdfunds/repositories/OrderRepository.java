package com.intuit.crowdfunds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.crowdfunds.entites.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public Optional<Order> findByOrderId(String orderId);
}
