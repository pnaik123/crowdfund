package com.intuit.crowdfunds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.crowdfunds.entites.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	Optional<Project> findById(Long projectId);
	
	
}
