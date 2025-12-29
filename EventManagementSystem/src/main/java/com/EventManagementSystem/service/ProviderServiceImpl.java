package com.EventManagementSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.repository.ServiceProviderRepository;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ServiceProviderRepository serviceProviderRepository;

	@Override
	public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO) {
		ServiceProvider provider = modelMapper.map(serviceProviderRequestDTO, ServiceProvider.class);
		ServiceProvider savedProvider = serviceProviderRepository.save(provider);
		return modelMapper.map(savedProvider, ServiceProviderResponseDTO.class);
	}

}
