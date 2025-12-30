package com.EventManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategoryRequestDTO {

	private String serviceCategoryName;
	private String description;
	private Boolean isActive;

}
