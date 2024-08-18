package com.intuit.crowdfunds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.intuit.crowdfunds.dtos.project.ProjectRequestDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.entites.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "currentAmount", expression = "java(0.0)")
    @Mapping(target = "status", expression = "java(\"active\")")  // Default status value
    Project toEntity(ProjectRequestDTO requestDTO);

    ProjectResponseDTO toDto(Project project);

}
