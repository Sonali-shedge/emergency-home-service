package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.enumT.AvailabilityStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderResponseDTO {

	private String providerName;
	private String email;
	private Integer experineceInYears;
	private AvailabilityStatus availabilityStatus;
	private Integer serviceRadiusinKm;
	private Double rating;
	private Boolean isVerified;

	private Long serviceCategoryId;

}
