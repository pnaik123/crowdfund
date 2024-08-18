package com.intuit.crowdfunds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.crowdfunds.entites.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long>{
	Optional<Donation> findById(Long projectId);
}
