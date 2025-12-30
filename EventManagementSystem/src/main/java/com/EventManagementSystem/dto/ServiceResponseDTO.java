package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.EmergencyLevel;
import com.EventManagementSystem.enumT.ServiceRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDTO {
	private String requestDescription;

	private ServiceRequestStatus status;
	private EmergencyLevel emergencyLevel;
	private String userName;
//	private ServiceProviderResponseDTO serviceProvider;
	//private ServiceCategoryResponseDTO serviceCategory;
	private String serviceCategoryName;
	private AddressResponseDTO address;

}
