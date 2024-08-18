package com.intuit.crowdfunds.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "projects")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseModel{

	@Column(name = "title")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "goal_amount")
	private Double goalAmount;

	@Column(name = "current_amount")
	private Double currentAmount;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "innovator_id")
	private User innovator;
	
	@Enumerated(EnumType.STRING)
	private ProjectCategory category;
	
	@Enumerated(EnumType.STRING)
	private ProjectSubCategory categorySubCategory;
	
	@Column(name = "launch_date")
	private LocalDateTime launchDate;
	
	@Column(name = "bank_details")
	private String bankDetails;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	

	@OneToMany(mappedBy = "project" ,cascade = CascadeType.ALL)
	private Set<Donation> donations;
}
