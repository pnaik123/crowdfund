package com.intuit.crowdfunds.controllers.project;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.dtos.project.ProjectRequestDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.services.project.ProjectService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final Logger logger = LoggingUtil.getLogger(getClass());
	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
		logger.info("Received request to create project with title: {}", projectRequestDTO.title());
		ProjectResponseDTO response = projectService.createProject(projectRequestDTO);
		logger.info("Project created successfully with ID: {}", response.id());
		return ResponseEntity.ok(response);
	}

}
