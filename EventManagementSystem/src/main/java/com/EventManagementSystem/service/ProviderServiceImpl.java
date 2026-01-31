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
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.enumT.BookingStatus;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.repository.BookingRepository;
import com.EventManagementSystem.repository.ServiceCategoryRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;
import com.EventManagementSystem.repository.UserRepository;

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

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO dto) {
        ServiceCategory category = serviceCategoryRepository.findById(dto.getServiceCategoryId())
                .orElseThrow(() -> new ServiceCategoryNotFoundException(
                        "Service category not found: " + dto.getServiceCategoryId()));

        ServiceProvider provider = new ServiceProvider();
        provider.setProviderName(dto.getProviderName());
        provider.setEmail(dto.getEmail());
        provider.setPhone(dto.getPhone());
        provider.setExperineceInYears(dto.getExperineceInYears());
        provider.setAvailabilityStatus(dto.getAvailabilityStatus());
        provider.setApprovalStatus(ApprovalStatus.PENDING);
        provider.setIsVerified(false);
        provider.setRating(dto.getRating());
        provider.setServiceCategory(category);

        ServiceProvider savedProvider = serviceProviderRepository.save(provider);
        return modelMapper.map(savedProvider, ServiceProviderResponseDTO.class);
    }

    @Override
    public List<ServiceProviderResponseDTO> getAllServiceProviders() {
        List<ServiceProvider> providers = serviceProviderRepository.findAll();
        return providers.stream().map(p -> {
            ServiceProviderResponseDTO dto = new ServiceProviderResponseDTO();

            dto.setProviderId(p.getProviderId());
            dto.setProviderName(p.getProviderName());
            dto.setEmail(p.getEmail());
            if (p.getUser() != null) {
                dto.setPhone(p.getUser().getPhone());
                dto.setBlocked(p.getUser().getBlocked());
            }
            dto.setExperienceInYears(p.getExperineceInYears());
            dto.setAvailabilityStatus(p.getAvailabilityStatus());
            dto.setApprovalStatus(p.getApprovalStatus().name());
            dto.setRating(p.getRating());
            dto.setIsVerified(p.getIsVerified());

            if (p.getServiceCategory() != null) {
                dto.setServiceCategoryId(p.getServiceCategory().getServiceCategoryId());
                dto.setCategoryName(p.getServiceCategory().getServiceCategoryName());
            }

            dto.setZoneNames(p.getServiceZones().stream()
                    .map(z -> z.getZoneName())
                    .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getProviderBookings(String email) {
        List<Booking> bookings = bookingRepository.findByServiceProvider_Email(email);

        return bookings.stream().map(b -> {
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(b.getBookingId());
            dto.setUserId(b.getUser().getUserId());
            dto.setServiceName(b.getService().getServiceName());
            dto.setBookingDateTime(b.getBookingDateTime());
            dto.setStatus(b.getStatus().name());
            dto.setPrice(b.getPrice());

            dto.setAddress(
                    b.getAddress().getHouseNumber() + ", " +
                            b.getAddress().getStreet() + ", " +
                            b.getAddress().getZone().getZoneName() + ", " +
                            b.getAddress().getZone().getCity() + " - " +
                            b.getAddress().getPincode()
            );

            if (b.getServiceProvider() != null) {
                dto.setProviderName(b.getServiceProvider().getProviderName());
                dto.setEmail(b.getServiceProvider().getEmail());
            } else {
                dto.setProviderName("Not Assigned");
                dto.setEmail("-");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public List<ServiceProviderResponseDTO> getPendingProviders() {
        return serviceProviderRepository.findByApprovalStatus(ApprovalStatus.PENDING)
                .stream()
                .map(p -> {
                    ServiceProviderResponseDTO dto = new ServiceProviderResponseDTO();
                    dto.setProviderId(p.getProviderId());
                    dto.setProviderName(p.getProviderName());
                    dto.setEmail(p.getEmail());
                    if (p.getUser() != null) dto.setPhone(p.getUser().getPhone());
                    dto.setExperienceInYears(p.getExperineceInYears());
                    dto.setAvailabilityStatus(p.getAvailabilityStatus());
                    dto.setApprovalStatus(p.getApprovalStatus().name());
                    if (p.getServiceCategory() != null) {
                        dto.setServiceCategoryId(p.getServiceCategory().getServiceCategoryId());
                        dto.setCategoryName(p.getServiceCategory().getServiceCategoryName());
                    }
                    dto.setZoneNames(p.getServiceZones().stream()
                            .map(z -> z.getZoneName())
                            .collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void approveOrRejectProvider(Long providerId, boolean approve) {
        ServiceProvider provider = serviceProviderRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Service Provider not found"));

        if (provider.getApprovalStatus() != ApprovalStatus.PENDING)
            throw new RuntimeException("Provider already processed");

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

//    @Override
    @Transactional
    public void toggleBlockProvider(Long providerId) {
        ServiceProvider provider = serviceProviderRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (provider.getApprovalStatus() != ApprovalStatus.APPROVED)
            throw new RuntimeException("Only approved providers can be blocked/unblocked");

        User user = provider.getUser();
        if (user == null)
            throw new RuntimeException("User not found");

        Boolean blocked = user.getBlocked();
        user.setBlocked(blocked != null ? !blocked : true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void acceptBooking(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getServiceProvider() == null || !booking.getServiceProvider().getEmail().equals(email))
            throw new RuntimeException("You are not authorized to accept this booking");

        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED)
            throw new RuntimeException("Booking cannot be accepted in current state");

        booking.setStatus(BookingStatus.IN_PROGRESS);
        ServiceProvider provider = booking.getServiceProvider();
        provider.setAvailabilityStatus(AvailabilityStatus.BUSY);

        serviceProviderRepository.save(provider);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void rejectBooking(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getServiceProvider() == null || !booking.getServiceProvider().getEmail().equals(email))
            throw new RuntimeException("You are not authorized to reject this booking");

        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED)
            throw new RuntimeException("Booking cannot be rejected in current state");

        booking.setStatus(BookingStatus.REJECTED);
        ServiceProvider provider = booking.getServiceProvider();
        provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        serviceProviderRepository.save(provider);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void completeBooking(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getServiceProvider() == null || !booking.getServiceProvider().getEmail().equals(email))
            throw new RuntimeException("You are not authorized to complete this booking");

        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            throw new RuntimeException("Only in-progress bookings can be completed");

        booking.setStatus(BookingStatus.COMPLETED);
        ServiceProvider provider = booking.getServiceProvider();
        provider.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        serviceProviderRepository.save(provider);
        bookingRepository.save(booking);
    }
}
