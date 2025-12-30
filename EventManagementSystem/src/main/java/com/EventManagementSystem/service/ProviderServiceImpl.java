package com.EventManagementSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;

	@Override
	public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO) {
	ServiceCategory category=	serviceCategoryRepository.findById(serviceProviderRequestDTO.getServiceCategoryId()).orElseThrow(()-> new ServiceCategoryNotFoundException("service category with this id not found" +serviceProviderRequestDTO.getServiceCategoryId()));
	ServiceProvider provider = new ServiceProvider();
	provider.setProviderName(serviceProviderRequestDTO.getProviderName());
	provider.setExperineceInYears(serviceProviderRequestDTO.getExperineceInYears());
	provider.setAvailabilityStatus(serviceProviderRequestDTO.getAvailabilityStatus());
	provider.setServiceRadiusinKm(serviceProviderRequestDTO.getServiceRadiusinKm());
	provider.setRating(serviceProviderRequestDTO.getRating());
	provider.setCategory(category);
	//ServiceProvider provider = modelMapper.map(serviceProviderRequestDTO, ServiceProvider.class);
		ServiceProvider savedProvider = serviceProviderRepository.save(provider);
		return modelMapper.map(savedProvider, ServiceProviderResponseDTO.class);
	}

}
