package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.repository.BookingRepository;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;

	@Override
	public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO) {
	ServiceCategory category=	serviceCategoryRepository.findById(serviceProviderRequestDTO.getServiceCategoryId()).orElseThrow(()-> new ServiceCategoryNotFoundException("service category with this id not found" +serviceProviderRequestDTO.getServiceCategoryId()));
	ServiceProvider provider = new ServiceProvider();
	provider.setProviderName(serviceProviderRequestDTO.getProviderName());
	provider.setExperineceInYears(serviceProviderRequestDTO.getExperineceInYears());
	provider.setAvailabilityStatus(serviceProviderRequestDTO.getAvailabilityStatus());
	//provider.setServiceRadiusinKm(serviceProviderRequestDTO.getServiceRadiusinKm());
	provider.setRating(serviceProviderRequestDTO.getRating());
	provider.setServiceCategory(category);
	//ServiceProvider provider = modelMapper.map(serviceProviderRequestDTO, ServiceProvider.class);
		ServiceProvider savedProvider = serviceProviderRepository.save(provider);
		return modelMapper.map(savedProvider, ServiceProviderResponseDTO.class);
	}

	@Override
	public List<ServiceProviderResponseDTO> getAllServiceProviders() {
		List<ServiceProvider> serviceProviders = serviceProviderRepository.findAll();
		return serviceProviders.stream().map(dto-> modelMapper.map(dto, ServiceProviderResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<BookingResponseDTO> getProviderBookings(String email) {
	    // 1. Fetch all bookings assigned to this provider
	    List<Booking> bookings = bookingRepository.findByServiceProvider_Email(email);

	    // 2. Map each Booking to BookingResponseDTO
	    return bookings.stream().map(booking -> {
	        BookingResponseDTO dto = new BookingResponseDTO();

	        // Booking ID
	        dto.setBookingId(booking.getBookingId());

	        // User ID
	        dto.setUserId(booking.getUser().getUserId());

	        // Service Name
	        dto.setServiceName(booking.getService().getServiceName());

	        // Booking Date/Time
	        dto.setBookingDateTime(booking.getBookingDateTime());

	        // Status
	        dto.setStatus(booking.getStatus().name());

	        // Price
	        dto.setPrice(booking.getPrice());

	        // Full Address
	        dto.setAddress(booking.getAddress().getHouseNumber());

	        // Provider Name
	        dto.setProviderName(booking.getServiceProvider().getProviderName());

	        // Provider Email
	        dto.setEmail(booking.getServiceProvider().getEmail());

	        return dto;
	    }).collect(Collectors.toList());
	}

	
	

	    // 1️⃣ Get all pending service provider requests
	    public List<ServiceProvider> getPendingProviders() {
	        return serviceProviderRepository
	                .findByApprovalStatus(ApprovalStatus.PENDING);
	    }

	    // 2️⃣ Approve or Reject service provider
	    @Transactional
	    public void approveOrRejectProvider(Long providerId, boolean approve) {

	        ServiceProvider provider = serviceProviderRepository.findById(providerId)
	            .orElseThrow(() -> new RuntimeException("Service Provider not found"));

	        // Prevent re-approval
	        if (provider.getApprovalStatus() != ApprovalStatus.PENDING) {
	            throw new RuntimeException("Provider already processed");
	        }

	        if (approve) {
	            provider.setApprovalStatus(ApprovalStatus.APPROVED);
	            provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
	            provider.setIsVerified(true);
	        } else {
	            provider.setApprovalStatus(ApprovalStatus.REJECTED);
	            provider.setAvailabilityStatus(AvailabilityStatus.UNAVAILABLE);
	            provider.setIsVerified(false);
	        }
	    }
	}

	


	
	



