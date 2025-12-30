package com.EventManagementSystem.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceRequest;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.exception.AddressNotFoundException;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.AddressRepository;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceRequestRepository;
import com.EventManagementSystem.repository.UserRepository;

@Service
public class RequestServiceImpl implements RequestService {

    

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;

	@Override
	public ServiceResponseDTO createServiceRequest(ServiceRequestDTO serviceRequestDTO, String userName) {
		User user = userRepository.findByUserName(userName).orElseThrow(()-> new UserNotFoundException("user with this name not exixts" +userName));
		Address address = addressRepository.findById(serviceRequestDTO.getAddressId()).orElseThrow(()-> new AddressNotFoundException("address not found with this id " +serviceRequestDTO.getAddressId()));
	ServiceCategory category =	serviceCategoryRepository.findByServiceCategoryNameIgnoreCase(serviceRequestDTO.getServiceCategoryName()).orElseThrow(()-> new ServiceCategoryNotFoundException("category not found with this name " +serviceRequestDTO.getServiceCategoryName()));
		//ServiceRequest request = modelMapper.map(serviceRequestDTO, ServiceRequest.class);
		ServiceRequest request2 = new ServiceRequest();
		request2.setRequestDescription(serviceRequestDTO.getRequestDescription());
		request2.setEmergencyLevel(serviceRequestDTO.getEmergencyLevel());
		request2.setUser(user);
		request2.setAddress(address);
		request2.setServiceCategory(category);

		ServiceRequest savedRequest = serviceRequestRepository.save(request2);
		return modelMapper.map(savedRequest, ServiceResponseDTO.class);
	}

}
