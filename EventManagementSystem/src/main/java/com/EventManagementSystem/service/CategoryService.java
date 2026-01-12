package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.ServiceCategoryRequestDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;

public interface CategoryService {

	public ServiceCategoryResponseDTO createServiceCategory(ServiceCategoryRequestDTO serviceCategoryRequestDTO);
	
	public List<ServiceCategoryResponseDTO> getAllServiceCategory();

}
