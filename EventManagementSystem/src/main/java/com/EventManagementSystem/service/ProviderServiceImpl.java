package com.EventManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderAdminDTO;
import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.Zone;
import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.enumT.BookingStatus;
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

    // 1. Fetch all bookings for this provider
    List<Booking> bookings = bookingRepository.findByServiceProvider_Email(email);

    // 2. Map Booking → DTO (no filtering)
    return bookings.stream()
            .map(booking -> {

                BookingResponseDTO dto = new BookingResponseDTO();

                dto.setBookingId(booking.getBookingId());
                dto.setUserId(booking.getUser().getUserId());
                dto.setServiceName(booking.getService().getServiceName());
                dto.setBookingDateTime(booking.getBookingDateTime());
                dto.setStatus(booking.getStatus().name());
                dto.setPrice(booking.getPrice());

                // Full Address
                dto.setAddress(
                        booking.getAddress().getHouseNumber() + ", " +
                        booking.getAddress().getStreet() + ", " +
                        booking.getAddress().getZone().getZoneName() + ", " +
                        booking.getAddress().getZone().getCity() + " - " +
                        booking.getAddress().getPincode()
                );

                // Null-safe provider
                if (booking.getServiceProvider() != null) {
                    dto.setProviderName(booking.getServiceProvider().getProviderName());
                    dto.setEmail(booking.getServiceProvider().getEmail());
                } else {
                    dto.setProviderName("Not Assigned");
                    dto.setEmail("-");
                }

                return dto;
            })
            .collect(Collectors.toList());
}


	
	

	    // 1️⃣ Get all pending service provider requests
	  public List<ServiceProviderAdminDTO> getPendingProviders() {

    return serviceProviderRepository
            .findByApprovalStatus(ApprovalStatus.PENDING)
            .stream()
            .map(provider -> {
                ServiceProviderAdminDTO dto = new ServiceProviderAdminDTO();

                dto.setProviderId(provider.getProviderId());
                dto.setProviderName(provider.getProviderName());

                // ⚠️ Better source of email
                dto.setEmail(provider.getUser().getEmail());

                dto.setPhone(provider.getUser().getPhone());

                dto.setCategoryName(
                        provider.getServiceCategory().getServiceCategoryName()
                );

                dto.setZoneNames(
                        provider.getServiceZones()
                                .stream()
                                .map(Zone::getZoneName)
                                .toList()
                );

                dto.setExperienceInYears(provider.getExperineceInYears());
                dto.setApprovalStatus(provider.getApprovalStatus());

                return dto; // 🔥 THIS WAS MISSING
            })
            .toList();
}


	    // 2️⃣ Approve or Reject service provider
	    @Transactional
public void approveOrRejectProvider(Long providerId, boolean approve) {

    ServiceProvider provider = serviceProviderRepository.findById(providerId)
        .orElseThrow(() -> new RuntimeException("Service Provider not found"));

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

    serviceProviderRepository.save(provider);
}

	   @Override
public void acceptBooking(Long bookingId, String email) {

    // 1. Fetch booking
    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    // 2. Authorization check
    if (booking.getServiceProvider() == null ||
        !booking.getServiceProvider().getEmail().equals(email)) {
        throw new RuntimeException("You are not authorized to accept this booking");
    }

    // 3. Status validation
    if (booking.getStatus() != BookingStatus.PENDING &&
        booking.getStatus() != BookingStatus.CONFIRMED) {
        throw new RuntimeException("Booking cannot be accepted in current state");
    }

    // 4. Update booking status
    booking.setStatus(BookingStatus.IN_PROGRESS);

    // 5. Update provider status to BUSY
    ServiceProvider provider = booking.getServiceProvider();
    provider.setAvailabilityStatus(AvailabilityStatus.BUSY);

    // 6. Save both
    serviceProviderRepository.save(provider);  // update provider status
    bookingRepository.save(booking);           // update booking status
}


	   @Override
	   public void rejectBooking(Long bookingId, String email) {

	       // 1️⃣ Fetch booking
	       Booking booking = bookingRepository.findById(bookingId)
	               .orElseThrow(() -> new RuntimeException("Booking not found"));

	       // 2️⃣ Authorization check
	       if (booking.getServiceProvider() == null ||
	           !booking.getServiceProvider().getEmail().equals(email)) {
	           throw new RuntimeException("You are not authorized to reject this booking");
	       }

	       // 3️⃣ Status validation: only PENDING or CONFIRMED can be rejected
	       if (booking.getStatus() != BookingStatus.PENDING &&
	           booking.getStatus() != BookingStatus.CONFIRMED) {
	           throw new RuntimeException("Booking cannot be rejected in current state");
	       }

	       // 4️⃣ Update booking status
	       booking.setStatus(BookingStatus.REJECTED);

	       // 5️⃣ Update provider status to AVAILABLE
	       ServiceProvider provider = booking.getServiceProvider();
	       provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

	       // 6️⃣ Save both
	       serviceProviderRepository.save(provider);
	       bookingRepository.save(booking);
	   }

	   @Override
	   public void completeBooking(Long bookingId, String email) {

	       // 1️⃣ Fetch booking
	       Booking booking = bookingRepository.findById(bookingId)
	               .orElseThrow(() -> new RuntimeException("Booking not found"));

	       // 2️⃣ Authorization check
	       if (booking.getServiceProvider() == null ||
	           !booking.getServiceProvider().getEmail().equals(email)) {
	           throw new RuntimeException("You are not authorized to complete this booking");
	       }

	       // 3️⃣ Status validation
	       if (booking.getStatus() != BookingStatus.IN_PROGRESS) {
	           throw new RuntimeException("Only in-progress bookings can be completed");
	       }

	       // 4️⃣ Update booking status
	       booking.setStatus(BookingStatus.COMPLETED);

	       // 5️⃣ Update provider status to AVAILABLE
	       ServiceProvider provider = booking.getServiceProvider();
	       provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

	       // 6️⃣ Save both
	       serviceProviderRepository.save(provider);
	       bookingRepository.save(booking);
	   }


	   


	   



		

	

		
	}

	


	
	



