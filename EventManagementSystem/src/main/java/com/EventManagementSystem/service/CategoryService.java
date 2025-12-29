package com.EventManagementSystem.service;

import com.EventManagementSystem.dto.ServiceCategoryRequestDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;

public interface CategoryService {

	public ServiceCategoryResponseDTO createServiceCategory(ServiceCategoryRequestDTO serviceCategoryRequestDTO);

}
