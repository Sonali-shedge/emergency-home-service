package com.EventManagementSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.dto.AddressRequestDTO;
import com.EventManagementSystem.dto.BookingRequestDTO;
import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.BookingStatusUpdateRequestDTO;
import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.Services;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.BookingStatus;
import com.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.repository.AddressRepository;
import com.EventManagementSystem.repository.BookingRepository;
import com.EventManagementSystem.repository.ServiceRepository;
import com.EventManagementSystem.repository.UserRepository;

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

	@Override
	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, String email) {

		User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Services service = serviceRepository.findById(bookingRequestDTO.getServiceId())
	            .orElseThrow(() -> new RuntimeException("Service not found"));

	    Address address = addressRepository.findById(bookingRequestDTO.getAddressId())
	            .orElseThrow(() -> new RuntimeException("Address not found"));
	    Booking booking = new Booking();
	    booking.setUserId(user.getUserId());
	    booking.setService(service);
	    booking.setAddress(address);
	    booking.setPrice(bookingRequestDTO.getPrice());
	    booking.setStatus(BookingStatus.PENDING);

	    booking.setBookingDateTime(
	            bookingRequestDTO.getBookingDateTime() != null
	                    ? bookingRequestDTO.getBookingDateTime()
	                    : LocalDateTime.now()
	    );

	    bookingRepository.save(booking);

	    BookingResponseDTO response = new BookingResponseDTO();
	    response.setBookingId(booking.getBookingId());
	    response.setUserId(booking.getUserId());
	    response.setServiceName(service.getServiceName());
	    response.setBookingDateTime(booking.getBookingDateTime());
	    response.setStatus(booking.getStatus().name());
	    response.setPrice(booking.getPrice());

	    response.setAddress(
	            address.getHouseNumber() + ", " +
	            address.getStreet() + ", " +
	            address.getArea() + ", " +
	            address.getCity() + " - " +
	            address.getPincode()
	    );

	    return response;
	}

	@Override
	public List<BookingResponseDTO> getUserBookings(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));

		List<Booking> bookings = bookingRepository.findByUserId(user.getUserId());

		return bookings.stream().map(booking -> {

			Services service = serviceRepository.findById(booking.getService().getServiceId())
					.orElseThrow(() -> new RuntimeException("Service not found"));

			Address address = addressRepository.findById(booking.getAddress().getAddressId())
					.orElseThrow(() -> new RuntimeException("Address not found"));

			AddressRequestDTO addressDTO = new AddressRequestDTO(address.getHouseNumber(), address.getStreet(),
					address.getArea(), address.getCity(), address.getPincode(), address.getState());
			String fullAddress = userServiceImpl.buildFullAddress(addressDTO);

			return new BookingResponseDTO(booking.getBookingId(), booking.getUserId(),
					booking.getService().getServiceName(), booking.getBookingDateTime(), booking.getStatus().name(),
					booking.getPrice(), fullAddress);

		}).collect(Collectors.toList());

	}

	@Override
	public String cancelBooking(Long bookingId, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found"));
	
	Booking booking =	bookingRepository.findByBookingIdAndUserId(bookingId, user.getUserId()).orElseThrow(()-> new RuntimeException("bookingId not found"));
	if (booking.getStatus() != BookingStatus.PENDING) {
        throw new RuntimeException("Only PENDING bookings can be canceled");
    }
	booking.setStatus(BookingStatus.CANCELLED
			);
    bookingRepository.save(booking);

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

		        // USER
		        dto.setUserId(booking.getUserId());
//		        dto.setUserName(booking.get);

		        // SERVICE
		        dto.setServiceName(booking.getService().getServiceName());

		        // ADDRESS
		        dto.setAddress(
		            booking.getAddress().getHouseNumber() + ", " +
		            booking.getAddress().getStreet() + ", " +
		            booking.getAddress().getCity()
		        );

		        return dto;

		    }).collect(Collectors.toList());
		}

	@Override
	public String updateBookingStatus(Long bookingId, BookingStatusUpdateRequestDTO requestDTO) {
		bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("booking id not found"));
		
		return null;
	}

	

	}

	
	

	
	

	


