package com.EventManagementSystem.service;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceCategoryRequestDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.repository.ServiceCategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;

	@Override
	public ServiceCategoryResponseDTO createServiceCategory(ServiceCategoryRequestDTO serviceCategoryRequestDTO) {

		ServiceCategory category = modelMapper.map(serviceCategoryRequestDTO, ServiceCategory.class);
		ServiceCategory savedCategory = serviceCategoryRepository.save(category);
		return modelMapper.map(savedCategory, ServiceCategoryResponseDTO.class);
	}

}
