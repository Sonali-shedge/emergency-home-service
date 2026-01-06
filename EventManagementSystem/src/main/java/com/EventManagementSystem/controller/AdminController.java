package com.EventManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.ServiceCategoryRequestDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.service.CategoryServiceImpl;
import com.EventManagementSystem.service.ProviderServiceImpl;
import com.EventManagementSystem.service.RequestServiceImpl;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@Autowired
	private ProviderServiceImpl providerServiceImpl;
	
	@Autowired
	private RequestServiceImpl requestServiceImpl;
	
	@PostMapping("/createServiceCategory")
	public ResponseEntity<ServiceCategoryResponseDTO> createServiceCategory(
			@RequestBody ServiceCategoryRequestDTO serviceCategoryRequestDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    System.out.println("AUTH NAME       : " + auth.getName());
	    System.out.println("AUTH AUTHORITIES: " + auth.getAuthorities());
	    System.out.println("AUTH AUTHENTICATED: " + auth.isAuthenticated());
		return new ResponseEntity<ServiceCategoryResponseDTO>(
				categoryServiceImpl.createServiceCategory(serviceCategoryRequestDTO), HttpStatus.CREATED);
	}
	
	@PostMapping("/createServiceProvider")
	public ResponseEntity<ServiceProviderResponseDTO> createServiceProvider(
			@RequestBody ServiceProviderRequestDTO serviceProviderRequestDTO) {
		return new ResponseEntity<ServiceProviderResponseDTO>(
				providerServiceImpl.createServiceProvider(serviceProviderRequestDTO), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getAllServiceProviders")
	public ResponseEntity <List<ServiceProviderResponseDTO>> getAllServiceProviders()
	{
		return new ResponseEntity<List<ServiceProviderResponseDTO>>(providerServiceImpl.getAllServiceProviders() ,HttpStatus.OK );
	}
	

	@PutMapping("/assignProvider/{serviceRequestId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ServiceResponseDTO> assignProvider(@PathVariable Long serviceRequestId)
	{
		return new ResponseEntity<ServiceResponseDTO>(requestServiceImpl.assignProvider(serviceRequestId) , HttpStatus.OK);
	}

}
