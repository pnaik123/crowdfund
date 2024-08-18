package com.intuit.crowdfunds.entites;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Setter
@Getter
@Builder
public class Donation extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	
}

