package com.EventManagementSystem.dto;

import java.time.LocalDateTime;

import com.EventManagementSystem.enumT.AvailabilityStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


public class ServicesRequestDTO {
	
	
	
	private String serviceName;
	
	private String serviceDescription;
	
	private Double startingPrice;
	
	private Double rating;
	
	private int totalReviews;
	
	private String imageUrl;
	
	
	private String serviceCategoryName;

	
	
}
