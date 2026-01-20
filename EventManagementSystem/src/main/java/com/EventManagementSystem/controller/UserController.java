package com.EventManagementSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.AddressResponseDTO;
import com.EventManagementSystem.dto.BookingRequestDTO;
import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceCategoryResponseDTO;
import com.EventManagementSystem.dto.UserRequestDTO;
import com.EventManagementSystem.dto.UserResponseDTO;
import com.EventManagementSystem.entity.ServiceCategory;
import com.EventManagementSystem.service.AddressServiceImpl;
import com.EventManagementSystem.service.BookingServiceImpl;
import com.EventManagementSystem.service.CategoryServiceImpl;
import com.EventManagementSystem.service.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@Autowired
	private BookingServiceImpl bookingServiceImpl;

	@Autowired
	private AddressServiceImpl addressServiceImpl;

	@GetMapping("/filterUser/{uId}")
	public ResponseEntity<UserResponseDTO> filterUser(@PathVariable Long uId) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.filterUser(uId), HttpStatus.OK);
	}

	@GetMapping("/allUser")
	public ResponseEntity<List<UserResponseDTO>> allUser() {
		return new ResponseEntity<List<UserResponseDTO>>(userServiceImpl.allUser(), HttpStatus.OK);
	}

	@PutMapping("/updateUser/{uId}")

	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long uId,
			@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<UserResponseDTO>(userServiceImpl.updateUser(uId, userRequestDTO), HttpStatus.OK);
	}

	@GetMapping("/getAllServiceCategory")
	public ResponseEntity<List<ServiceCategoryResponseDTO>> getAllServiceCategory() {
		return new ResponseEntity<List<ServiceCategoryResponseDTO>>(categoryServiceImpl.getAllServiceCategory(),
				HttpStatus.OK);
	}

	@PostMapping("/createBooking")
	public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO,
			Principal principal) {
		String email = principal.getName();
		BookingResponseDTO response = bookingServiceImpl.createBooking(bookingRequestDTO, email);
		return new ResponseEntity<BookingResponseDTO>(response, HttpStatus.CREATED);
	}

	@GetMapping("/addresses")
	public ResponseEntity<List<AddressResponseDTO>> getUserAddresses(Principal principal) {
		String email = principal.getName();
		return new ResponseEntity<List<AddressResponseDTO>>(addressServiceImpl.getUserAddresses(email), HttpStatus.OK);
	}

	@GetMapping("/myBookings")
	public ResponseEntity<List<BookingResponseDTO>> getUserBookings(Principal principal) {
		String email = principal.getName();
		return new ResponseEntity<List<BookingResponseDTO>>(bookingServiceImpl.getUserBookings(email), HttpStatus.OK);
	}

	@PutMapping("/{bookingId}/cancel")
	public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId, Principal principal) {
		String email = principal.getName();
		return new ResponseEntity<String>(bookingServiceImpl.cancelBooking(bookingId, email), HttpStatus.OK);
	}
	
	
}
