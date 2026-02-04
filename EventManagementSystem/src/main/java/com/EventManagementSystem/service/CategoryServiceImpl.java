package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

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

	 @Override
	    public List<ServiceCategoryResponseDTO> getAllServiceCategory() {
	        List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();

	        // Map manually to ensure serviceCategoryId is populated
	        return serviceCategories.stream()
	                .map(cat -> new ServiceCategoryResponseDTO(
	                        cat.getServiceCategoryId(),
	                        cat.getServiceCategoryName(),
	                        cat.getDescription(),
	                        cat.getIsActive()
	                ))
	                .collect(Collectors.toList());
	    }

}
