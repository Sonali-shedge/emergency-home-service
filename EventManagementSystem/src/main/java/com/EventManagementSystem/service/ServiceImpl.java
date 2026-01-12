package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServicesRequestDTO;
import com.EventManagementSystem.dto.ServicesResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.Services;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceRepository;


@Service
public class ServiceImpl  implements ServicesService{
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ServicesResponseDTO createServices(ServicesRequestDTO servicesRequestDTO, String serviceCategoryName) {
//	Services services=	modelMapper.map(servicesRequestDTO, Services.class);
	ServiceCategory serviceCategory=serviceCategoryRepository.findByServiceCategoryNameIgnoreCase(serviceCategoryName).orElseThrow(()->  new ServiceCategoryNotFoundException("category not found"));
		
		
		Services services = new Services();
		services.setServiceName(servicesRequestDTO.getServiceName());
		services.setServiceDescription(servicesRequestDTO.getServiceDescription());
		services.setStartingPrice(servicesRequestDTO.getStartingPrice());
		services.setRating(servicesRequestDTO.getRating());
		services.setTotalReviews(servicesRequestDTO.getTotalReviews());
		services.setImageUrl(servicesRequestDTO.getImageUrl());
		services.setServiceCategory(serviceCategory);
		serviceRepository.save(services);
		
		return modelMapper.map(services, ServicesResponseDTO.class) ;
	}

	@Override
	public List<ServicesResponseDTO> getAllServices(String serviceCategoryName) {
	ServiceCategory serviceCategory =	serviceCategoryRepository.findByServiceCategoryNameIgnoreCase(serviceCategoryName).orElseThrow(()-> new ServiceCategoryNotFoundException("category not found"));
	
	List<Services> services = serviceRepository.findByServiceCategory(serviceCategory);
	return services.stream().map(dto-> modelMapper.map(dto, ServicesResponseDTO.class)).collect(Collectors.toList());
	}

	public  ServicesResponseDTO getServicesById(Long serviceId) {
		Services services = serviceRepository.findById(serviceId).orElseThrow(()-> new RuntimeException("services not found"));
		return modelMapper.map(services, ServicesResponseDTO.class);
	}

	
	

	
}
