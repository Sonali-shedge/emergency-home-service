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
import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.service.CategoryServiceImpl;
import com.EventManagementSystem.service.ProviderServiceImpl;
import com.EventManagementSystem.service.RequestServiceImpl;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
	@Autowired
	private RequestServiceImpl requestServiceImpl;


	@PostMapping("/createServiceRequest/{userName}")
	public ResponseEntity<ServiceResponseDTO> createServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO , @PathVariable String userName) {
		return new ResponseEntity<ServiceResponseDTO>(requestServiceImpl.createServiceRequest(serviceRequestDTO , userName),
				HttpStatus.CREATED);
	}
	
	
	@PutMapping("/startWork/{serviceRequestId}")
	@PreAuthorize("hasRole('SERVICE_PROVIDER')")
	public ResponseEntity<ServiceResponseDTO> startWork(@PathVariable Long serviceRequestId)
	{
		return new ResponseEntity<ServiceResponseDTO>(requestServiceImpl.startWork(serviceRequestId) , HttpStatus.OK);
	}
	
	@PutMapping("/completetWork/{serviceRequestId}")
	@PreAuthorize("hasRole('SERVICE_PROVIDER')")
	public ResponseEntity<ServiceResponseDTO> completetWork(@PathVariable Long serviceRequestId)
	{
		return new ResponseEntity<ServiceResponseDTO>(requestServiceImpl.completetWork(serviceRequestId) , HttpStatus.OK);
	}

}
