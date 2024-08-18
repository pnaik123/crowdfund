package com.intuit.crowdfunds.entites;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User extends BaseModel{

    @Column(name = "first_name", nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 100)
    private String lastName;
    
    @Column(nullable = false)
    @Size(max = 100)
    private String login;

    @Column(nullable = false)
    @Size(max = 100)
    private String email;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Size(max = 100)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "innovator", cascade = CascadeType.ALL)
    private Set<Project> projects;

    @OneToMany(mappedBy = "donor",cascade = CascadeType.ALL)
    private Set<Donation> donations;
}
