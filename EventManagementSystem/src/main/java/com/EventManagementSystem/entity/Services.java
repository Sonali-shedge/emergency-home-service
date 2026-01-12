package com.EventManagementSystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Services {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceId;
	
	private String serviceName;
	
	private String serviceDescription;
	
	private Double startingPrice;
	
	private Double rating;
	
	private int totalReviews;
	
	private String imageUrl;
	
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinColumn(name = "serviceCategoryId")
	private ServiceCategory serviceCategory;

	
	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();

	}

}
