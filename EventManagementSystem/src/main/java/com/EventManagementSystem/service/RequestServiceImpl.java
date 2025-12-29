package com.EventManagementSystem.service;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.entity.ServiceRequest;
import com.EventManagementSystem.repository.ServiceRequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Override
	public ServiceResponseDTO createServiceRequest(ServiceRequestDTO serviceRequestDTO) {
		ServiceRequest request = modelMapper.map(serviceRequestDTO, ServiceRequest.class);

		ServiceRequest savedRequest = serviceRequestRepository.save(request);
		return modelMapper.map(savedRequest, ServiceResponseDTO.class);
	}

}
