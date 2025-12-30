package com.EventManagementSystem.entity;

import java.time.LocalDateTime;

import com.EventManagementSystem.enumT.EmergencyLevel;
import com.EventManagementSystem.enumT.ServiceRequestStatus;

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
public class ServiceRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceRequestId;

	private String requestDescription;

	@Enumerated(EnumType.STRING)
	private ServiceRequestStatus status;

	@Enumerated(EnumType.STRING)
	private EmergencyLevel emergencyLevel;

	private LocalDateTime requestedAt;

	private LocalDateTime acceptedAt;

	private LocalDateTime completedAt;

	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY ,  cascade = CascadeType.ALL)
	@JoinColumn(name = "serviceProviderId")
	private ServiceProvider serviceProvider;

	@ManyToOne(fetch = FetchType.LAZY ,  cascade = CascadeType.ALL)
	@JoinColumn(name = "serviceCategoryId")
	private ServiceCategory serviceCategory;

	@ManyToOne(fetch = FetchType.LAZY ,  cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId")
	private Address address;

	@PrePersist
	public void onCreate() {
		this.requestedAt = LocalDateTime.now();
		this.status = ServiceRequestStatus.PENDING;
	}

}
