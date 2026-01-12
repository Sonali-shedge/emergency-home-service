package com.EventManagementSystem.entity;

import java.time.LocalDateTime;

import com.EventManagementSystem.enumT.AvailabilityStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceProvider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long providerId;
	private String providerName;
	private Integer experineceInYears;
	@Enumerated(EnumType.STRING)
	private AvailabilityStatus availabilityStatus;
	private Integer serviceRadiusinKm;
	private Double rating;
	private Boolean isVerified;
	private LocalDateTime createdAt;
	private Double latitude;
	private Double longitude;

	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinColumn(name = "serviceCategoryId")
	private ServiceCategory serviceCategory;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();

	}

}
