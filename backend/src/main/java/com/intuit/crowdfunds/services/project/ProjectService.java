package com.intuit.crowdfunds.services.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.crowdfunds.constants.Constants.STATUS;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.dtos.project.ProjectRequestDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.entites.Project;
import com.intuit.crowdfunds.entites.Role;
import com.intuit.crowdfunds.entites.User;
import com.intuit.crowdfunds.exceptions.ProjectCreationException;
import com.intuit.crowdfunds.exceptions.ResourceNotFoundException;
import com.intuit.crowdfunds.mappers.ProjectMapper;
import com.intuit.crowdfunds.repositories.ProjectRepository;
import com.intuit.crowdfunds.repositories.UserRepository;
import com.intuit.crowdfunds.services.UserService;
import com.intuit.crowdfunds.utility.LoggingUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectService {

	private final Logger logger = LoggingUtil.getLogger(getClass());

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final ProjectMapper projectMapper;

	@Transactional
	public ProjectResponseDTO createProject(ProjectRequestDTO projectRequest) {
		UserDTO userDTO = userService.getUserDetailsFromContext();
		Optional<User> userResponse = userRepository.findByLogin(userDTO.getLogin());

		if (userResponse.isPresent()) {
			User user = userResponse.get();
			if (user.getRole() != Role.Innovator) {
				String errorMessage = "User with login " + userDTO.getLogin()
						+ " does not have privilege to create project";
				logger.error(errorMessage);
				throw new ProjectCreationException(errorMessage);
			}
			try {
				Project project = Project.builder().title(projectRequest.title())
						.description(projectRequest.description()).goalAmount(projectRequest.goalAmount())
						.currentAmount(0.0).status(STATUS.ACTIVE).createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now()).innovator(user).build();
				project = projectRepository.save(project);
				logger.info("Project created successfully with ID: {}", project.getId());
				return projectMapper.toDto(project);
			} catch (DataAccessException e) {
				String errorMessage = "Failed to save project: " + e.getMessage();
				logger.error(errorMessage);
				throw new ProjectCreationException(errorMessage);
			}
		} else {
			String errorMessage = "User not found: " + userDTO.getLogin();
			logger.error(errorMessage);
			throw new ResourceNotFoundException(errorMessage);
		}
	}

	public List<Project> getAllProjects() {
		List<Project> projects = projectRepository.findAll();
		logger.info("Retrieved {} projects", projects.size());
		return projects;
	}

	public Project getProject(Long projectId) {
		Optional<Project> project = projectRepository.findById(projectId);
		if (project.isPresent()) {
			logger.info("Project retrieved successfully with ID: {}", projectId);
			return project.get();
		} else {
			String errorMessage = "Project not found: " + projectId;
			logger.error(errorMessage);
			throw new ResourceNotFoundException(errorMessage);
		}
	}
}
