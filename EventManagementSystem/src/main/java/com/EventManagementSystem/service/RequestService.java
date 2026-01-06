package com.EventManagementSystem.service;

import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.entity.ServiceRequest;

public interface RequestService {

	public ServiceResponseDTO createServiceRequest(ServiceRequestDTO serviceRequestDTO , String userName);
	
	public ServiceResponseDTO assignProvider(Long serviceRequestId);
	
	public  ServiceResponseDTO startWork(Long serviceRequestId);
	
	public ServiceResponseDTO completetWork(Long serviceRequestId);
	
	

}
