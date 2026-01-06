package com.EventManagementSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.ServiceRequestDTO;
import com.EventManagementSystem.dto.ServiceResponseDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.ServiceRequest;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.enumT.ServiceRequestStatus;
import com.EventManagementSystem.exception.AddressNotFoundException;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.exception.ServiceProviderNotFoundException;
import com.EventManagementSystem.exception.ServiceRequestNotFoundException;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.AddressRepository;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;
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

	@Autowired
	private ServiceProviderRepository serviceProviderRepository;

	@Override
	public ServiceResponseDTO createServiceRequest(ServiceRequestDTO serviceRequestDTO, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("user with this name not exixts" + userName));
		Address address = addressRepository.findById(serviceRequestDTO.getAddressId())
				.orElseThrow(() -> new AddressNotFoundException(
						"address not found with this id " + serviceRequestDTO.getAddressId()));
		ServiceCategory category = serviceCategoryRepository
				.findByServiceCategoryNameIgnoreCase(serviceRequestDTO.getServiceCategoryName())
				.orElseThrow(() -> new ServiceCategoryNotFoundException(
						"category not found with this name " + serviceRequestDTO.getServiceCategoryName()));
		// ServiceRequest request = modelMapper.map(serviceRequestDTO,
		// ServiceRequest.class);
		ServiceRequest request2 = new ServiceRequest();
		request2.setRequestDescription(serviceRequestDTO.getRequestDescription());
		request2.setEmergencyLevel(serviceRequestDTO.getEmergencyLevel());
		request2.setUser(user);
		request2.setAddress(address);
		request2.setServiceCategory(category);

		ServiceRequest savedRequest = serviceRequestRepository.save(request2);
		return modelMapper.map(savedRequest, ServiceResponseDTO.class);
	}

	@Override
	public ServiceResponseDTO assignProvider(Long serviceRequestId) {
		ServiceRequest serviceRequest = serviceRequestRepository.findById(serviceRequestId)
				.orElseThrow(() -> new ServiceRequestNotFoundException("service request not foumd with tih sis " + serviceRequestId));
		Long categoryId = serviceRequest.getServiceCategory().getServiceCategoryId();

	    List<ServiceProvider> providers = serviceProviderRepository
	            .findByServiceCategory_ServiceCategoryIdAndAvailabilityStatus(
	                    categoryId,
	                    AvailabilityStatus.AVAILABLE
	            );
		if (providers.isEmpty()) {
			throw new ServiceProviderNotFoundException("Service provider is not availbale for this category.");
		}
		ServiceProvider serviceProvider = providers.get(0);

		serviceRequest.setServiceProvider(serviceProvider);
		serviceRequest.setStatus(ServiceRequestStatus.ACCEPTED);
		serviceRequest.setAcceptedAt(LocalDateTime.now());
		serviceProvider.setAvailabilityStatus(AvailabilityStatus.BUSY);
		serviceProviderRepository.save(serviceProvider);
		serviceRequestRepository.save(serviceRequest);
		return modelMapper.map(serviceRequest, ServiceResponseDTO.class);
	}

	@Override
	public ServiceResponseDTO startWork(Long serviceRequestId) {
		ServiceRequest serviceRequest = serviceRequestRepository.findById(serviceRequestId).orElseThrow(()-> new ServiceRequestNotFoundException("service request not found with this id " +serviceRequestId));
		ServiceProvider provider = serviceRequest.getServiceProvider();
		if(provider==null)
		{
			throw new ServiceProviderNotFoundException("no provider is assigned to this request id");
		}
		serviceRequest.setStatus(ServiceRequestStatus.IN_PROGRESS);
		serviceRequestRepository.save(serviceRequest);
		return modelMapper.map(serviceRequest, ServiceResponseDTO.class);
	}

	@Override
	public ServiceResponseDTO completetWork(Long serviceRequestId) {
		ServiceRequest serviceRequest = serviceRequestRepository.findById(serviceRequestId).orElseThrow(()-> new ServiceRequestNotFoundException("service request not found with this id " +serviceRequestId));
		ServiceProvider provider = serviceRequest.getServiceProvider();
		if(provider == null)
		{
			throw new ServiceProviderNotFoundException("serfvice provider is not assigned to this request id");
		}
		serviceRequest.setStatus(ServiceRequestStatus.COMPLETED);
		serviceRequest.setCompletedAt(LocalDateTime.now());
		provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
		serviceProviderRepository.save(provider);
		serviceRequestRepository.save(serviceRequest);
		return modelMapper.map(serviceRequest, ServiceResponseDTO.class);
	}

	
	

	
}
