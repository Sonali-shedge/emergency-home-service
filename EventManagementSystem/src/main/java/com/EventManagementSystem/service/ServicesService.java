package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.ServicesRequestDTO;
import com.EventManagementSystem.dto.ServicesResponseDTO;


public interface ServicesService {
	
	public ServicesResponseDTO createServices(ServicesRequestDTO servicesRequestDTO , String serviceCategoryName);
	
	public  List<ServicesResponseDTO> getAllServices(String serviceCategoryName);

}
