package com.intuit.crowdfunds.mappers;

import com.intuit.crowdfunds.dtos.project.ProjectRequestDTO;
import com.intuit.crowdfunds.dtos.project.ProjectResponseDTO;
import com.intuit.crowdfunds.entites.Project;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T19:46:58+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toEntity(ProjectRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.title( requestDTO.title() );
        project.description( requestDTO.description() );
        project.goalAmount( requestDTO.goalAmount() );

        project.currentAmount( 0.0 );
        project.status( "active" );

        return project.build();
    }

    @Override
    public ProjectResponseDTO toDto(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String description = null;
        int goalAmount = 0;
        int currentAmount = 0;
        String status = null;
        String createdAt = null;
        String updatedAt = null;

        id = project.getId();
        title = project.getTitle();
        description = project.getDescription();
        if ( project.getGoalAmount() != null ) {
            goalAmount = project.getGoalAmount().intValue();
        }
        if ( project.getCurrentAmount() != null ) {
            currentAmount = project.getCurrentAmount().intValue();
        }
        status = project.getStatus();
        if ( project.getCreatedAt() != null ) {
            createdAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( project.getCreatedAt() );
        }
        if ( project.getUpdatedAt() != null ) {
            updatedAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( project.getUpdatedAt() );
        }

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO( id, title, description, goalAmount, currentAmount, status, createdAt, updatedAt );

        return projectResponseDTO;
    }
}
