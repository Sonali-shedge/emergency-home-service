package com.EventManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.ServiceCategoryRequestDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.ServiceRequest;
import com.EventManagementSystem.service.CategoryService;
import com.EventManagementSystem.service.CategoryServiceImpl;
import com.EventManagementSystem.service.ProviderServiceImpl;
import com.EventManagementSystem.service.RequestServiceImpl;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
	@Autowired
	private RequestServiceImpl requestServiceImpl;

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@Autowired
	private ProviderServiceImpl providerServiceImpl;

	@PostMapping("/createServiceCategory")
	public ResponseEntity<ServiceCategoryResponseDTO> createServiceCategory(
			@RequestBody ServiceCategoryRequestDTO serviceCategoryRequestDTO) {
		return new ResponseEntity<ServiceCategoryResponseDTO>(
				categoryServiceImpl.createServiceCategory(serviceCategoryRequestDTO), HttpStatus.CREATED);
	}

	@PostMapping("/createServiceProvider")
	public ResponseEntity<ServiceProviderResponseDTO> createServiceProvider(
			@RequestBody ServiceProviderRequestDTO serviceProviderRequestDTO) {
		return new ResponseEntity<ServiceProviderResponseDTO>(
				providerServiceImpl.createServiceProvider(serviceProviderRequestDTO), HttpStatus.CREATED);
	}

	@PostMapping("/createServiceRequest")
	public ResponseEntity<ServiceResponseDTO> createServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO) {
		return new ResponseEntity<ServiceResponseDTO>(requestServiceImpl.createServiceRequest(serviceRequestDTO),
				HttpStatus.CREATED);
	}

}
