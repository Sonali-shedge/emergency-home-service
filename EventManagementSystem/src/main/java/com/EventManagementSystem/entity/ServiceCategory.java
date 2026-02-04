package com.EventManagementSystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceCategoryId; 
	@Column(unique = true, nullable = false)
	private String serviceCategoryName;
	private String description;
	private Boolean isActive;
	private LocalDateTime createdAt;

//	@OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
//	@JoinColumn(name="serviceCategoryId")
//	private List<ServiceProvider> provider = new ArrayList<>();

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();

	}

}
