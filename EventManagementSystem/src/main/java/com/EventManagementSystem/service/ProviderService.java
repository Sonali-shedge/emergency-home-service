package com.EventManagementSystem.service;

import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.ServiceProvider;

public interface ProviderService {

	public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO);

}
