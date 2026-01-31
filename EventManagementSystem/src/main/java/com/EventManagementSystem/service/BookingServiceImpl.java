package com.EventManagementSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.config.EmailService;
import com.EventManagementSystem.dto.BookingRequestDTO;
import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.BookingStatusUpdateRequestDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.Services;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.entity.Zone;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import com.EventManagementSystem.enumT.BookingStatus;
import com.EventManagementSystem.exception.ServiceCategoryNotFoundException;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.AddressRepository;
import com.EventManagementSystem.repository.BookingRepository;
import com.EventManagementSystem.repository.ServiceProviderRepository;
import com.EventManagementSystem.repository.ServiceRepository;
import com.EventManagementSystem.repository.UserRepository;
import com.EventManagementSystem.repository.ZoneRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ServiceRepository serviceRepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	EmailService emailService;

	@Autowired
	NotificationServiceImpl notificationServiceImpl;

	@Autowired
	ProviderServiceImpl providerServiceImpl;

	@Autowired
	ServiceProviderRepository serviceProviderRepository;


	@Autowired
	ZoneRepository zoneRepository;

	@Override
	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("user not found with this email"));
		Services service = serviceRepository.findById(bookingRequestDTO.getServiceId())
				.orElseThrow(() -> new ServiceCategoryNotFoundException("service not found"));
		Address address = addressRepository.findById(bookingRequestDTO.getAddressId())
				.orElseThrow(() -> new RuntimeException("address not found"));

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setService(service);
		booking.setAddress(address);
		booking.setBookingDateTime(
	            bookingRequestDTO.getBookingDateTime() != null
	                    ? bookingRequestDTO.getBookingDateTime()
	                    : LocalDateTime.now()
	    );
		booking.setStatus(BookingStatus.PENDING);
		booking.setPrice(bookingRequestDTO.getPrice());
		Booking savedBooking = bookingRepository.save(booking);
		savedBooking = assignProviderAutomatically(savedBooking);
		
		Zone zone = address.getZone();

		BookingResponseDTO response = new BookingResponseDTO();
		response.setBookingId(savedBooking.getBookingId());
		response.setUserId(user.getUserId());
		response.setServiceName(service.getServiceName());
		response.setBookingDateTime(savedBooking.getBookingDateTime());
		response.setStatus(savedBooking.getStatus().name());
		response.setPrice(savedBooking.getPrice());
		response.setProviderName(
			    savedBooking.getServiceProvider() != null ? 
			    savedBooking.getServiceProvider().getProviderName() : "Not assigned yet"
			);
		response.setAddress(
		        address.getHouseNumber() + ", " +
		        address.getStreet() + ", " +
		        zone.getZoneName() + ", " +     // ✅ Katraj
		        zone.getCity() + " - " +        // ✅ Pune
		        address.getPincode()
		);
//		notificationServiceImpl.createNotification(user.getUserId(),
//				"Your booking has been created and provider assigned: "
//						+ savedBooking.getServiceProvider().getProviderName());

		return response;
	}

//	@Override
//	public List<BookingResponseDTO> getUserBookings(String email) {
//		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));
//
//		List<Booking> bookings = bookingRepository.findByUserId(user.getUserId());
//
//		return bookings.stream().map(booking -> {
//
//			Services service = serviceRepository.findById(booking.getService().getServiceId())
//					.orElseThrow(() -> new RuntimeException("Service not found"));
//
//			Address address = addressRepository.findById(booking.getAddress().getAddressId())
//					.orElseThrow(() -> new RuntimeException("Address not found"));
//
//			AddressRequestDTO addressDTO = new AddressRequestDTO(address.getHouseNumber(), address.getStreet(),
//					address.getArea(), address.getCity(), address.getPincode(), address.getState());
//			String fullAddress = nominatimGeocodingService.buildFullAddress(addressDTO);
//
//			return new BookingResponseDTO(booking.getBookingId(), booking.getUserId(),
//					booking.getService().getServiceName(), booking.getBookingDateTime(), booking.getStatus().name(),
//					booking.getPrice(), fullAddress);
//
//		}).collect(Collectors.toList());
//
//	}

	private Booking assignProviderAutomatically(Booking savedBooking) {

	    Zone zone = savedBooking.getAddress().getZone();
	    if (zone == null) {
	        throw new RuntimeException("Booking address does not have a zone assigned!");
	    }

	    Long categoryId =
	        savedBooking.getService()
	            .getServiceCategory()
	            .getServiceCategoryId();

	    List<ServiceProvider> availableProviders =
	        serviceProviderRepository.findAvailableProviders(
	            zone.getZoneId(),
	            categoryId,
	            AvailabilityStatus.AVAILABLE
	        );

	    // 🚫 No provider available
	    if (availableProviders == null || availableProviders.isEmpty()) {
	        savedBooking.setServiceProvider(null);
	        return bookingRepository.save(savedBooking);
	    }

	    // ✅ Pick provider (basic logic: first one)
	    ServiceProvider provider = availableProviders.get(0);

	    // Optional: mark provider unavailable
	    provider.setAvailabilityStatus(AvailabilityStatus.UNAVAILABLE);
	    serviceProviderRepository.save(provider);

	    // ✅ Assign provider to booking
	    savedBooking.setServiceProvider(provider);

	    return bookingRepository.save(savedBooking); // 🔥 REQUIRED
	}

		



	@Override
	public String cancelBooking(Long bookingId, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));

		Booking booking = bookingRepository.findByBookingIdAndUser_UserId(bookingId, user.getUserId())
				.orElseThrow(() -> new RuntimeException("bookingId not found"));
		if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus()!= BookingStatus.CONFIRMED) {
			throw new RuntimeException("Only PENDING  and confirm bookings can be canceled");
		}
		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepository.save(booking);

		notificationServiceImpl.createNotification(bookingId, "Your booking is cancelled successfully....");

		String emaill = user.getEmail();
		String subject = "Booking Cancelled";
		String body = "Hi " + user.getUserName() + ",\n\n" + "Your booking for " + booking.getService().getServiceName()
				+ " has been cancelled successfully.\n\nThank you!";
		emailService.sendEmail(emaill, subject, body);

		return "Booking canceled successfully";
	}

	@Override
	public List<BookingResponseDTO> getAllBookings() {

		List<Booking> bookings = bookingRepository.findAll();

		return bookings.stream().map(booking -> {

			BookingResponseDTO dto = new BookingResponseDTO();
			dto.setBookingId(booking.getBookingId());
			dto.setBookingDateTime(booking.getBookingDateTime());
			dto.setStatus(booking.getStatus().name());
			dto.setPrice(booking.getPrice());
			dto.setProviderName(
					booking.getServiceProvider() != null ? 
							booking.getServiceProvider().getProviderName() : "Not assigned yet"
				);

			// USER
		dto.setUserId(booking.getUser().getUserId());
//		        dto.setUserName(booking.get);

			// SERVICE
			dto.setServiceName(booking.getService().getServiceName());

			// ADDRESS
			dto.setAddress(booking.getAddress().getHouseNumber());

			return dto;

		}).collect(Collectors.toList());
	}

	@Override
	public String updateBookingStatus(Long bookingId, BookingStatusUpdateRequestDTO requestDTO) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("booking id not found"));
		User user = userRepository.findById(booking.getUser().getUserId())
				.orElseThrow(() -> new UserNotFoundException("user not found"));
		BookingStatus newStatus = requestDTO.getStatus();
		if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
			throw new RuntimeException("Cannot update status of cancelled or completed booking");
		}

		booking.setStatus(newStatus);
		bookingRepository.save(booking);

		String email = user.getEmail();
		String subject = "Booking status updated";
		String body = "Hi " + user.getUserName() + ",\n\n" + "Your booking for " + booking.getService().getServiceName()
				+ " has been updated to: " + newStatus + ".\n\n" + "Thank you for using our service!";

		emailService.sendEmail(email, subject, body);

		return "booking status updated to " + requestDTO.getStatus();
	}

	@Override
	public List<BookingResponseDTO> getUserBookings(String email) {

	    List<Booking> bookings = bookingRepository.findByUserEmailOrderByBookingDateTimeDesc(email);

	    if (bookings.isEmpty()) {
	        throw new RuntimeException("No bookings found for this user");
	    }

	    return bookings.stream().map(booking -> {

			BookingResponseDTO dto = new BookingResponseDTO();
			dto.setBookingId(booking.getBookingId());
			dto.setBookingDateTime(booking.getBookingDateTime());
			dto.setStatus(booking.getStatus().name());
			dto.setPrice(booking.getPrice());
			dto.setProviderName(
					booking.getServiceProvider() != null ? 
							booking.getServiceProvider().getProviderName() : "Not assigned yet"
				);
			// USER
		dto.setUserId(booking.getUser().getUserId());
//		        dto.setUserName(booking.get);

			// SERVICE
			dto.setServiceName(booking.getService().getServiceName());

			// ADDRESS
			dto.setAddress(booking.getAddress().getHouseNumber());

			return dto;

		}).collect(Collectors.toList());
	}

	}



