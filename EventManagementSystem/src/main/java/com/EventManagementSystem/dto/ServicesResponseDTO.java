package com.EventManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ServicesResponseDTO {
	
	private Long serviceId;
	
	private String serviceName;
	
	private String serviceDescription;
	
	private Double startingPrice;
	
	private Double rating;
	
	private int totalReviews;
	
	private String imageUrl;
	
	
	private String serviceCategoryName;

	
	
}

