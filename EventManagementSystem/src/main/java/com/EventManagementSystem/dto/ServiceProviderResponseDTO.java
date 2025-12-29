package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.ServiceCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderResponseDTO {

	private String providerName;
	private Integer experineceInYears;
	private String availabilityStatus;
	private Integer serviceRadiusinKm;
	private Double rating;
	private Boolean isVerified;

	private ServiceCategory category;

}
