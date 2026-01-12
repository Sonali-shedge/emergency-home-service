package com.EventManagementSystem.dto;

import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.EmergencyLevel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO {
	private String requestDescription;

	//private ServiceRequestStatus status;
	private EmergencyLevel emergencyLevel;
	private User user;
//	private ServiceProvider serviceProvider;
	private String serviceCategoryName;
	//private Address address;
	private Long addressId;

}
