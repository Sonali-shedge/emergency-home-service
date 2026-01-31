package com.EventManagementSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderAdminDTO;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.service.ProviderServiceImpl;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

	@Autowired
	ProviderServiceImpl providerServiceImpl;

	@GetMapping("/getProviderBookings")
	public ResponseEntity<List<BookingResponseDTO>> getProviderBookings(Principal principal) {
		String email = principal.getName();
		return new ResponseEntity<List<BookingResponseDTO>>(providerServiceImpl.getProviderBookings(email),
				HttpStatus.OK);
	}

	// 1️⃣ Get all pending provider requests
	@GetMapping("/providers/pending")
	public ResponseEntity<List<ServiceProviderAdminDTO>> getPendingProviders() {
		return new ResponseEntity<List<ServiceProviderAdminDTO>>(providerServiceImpl.getPendingProviders(),
				HttpStatus.OK);
	}

	// 2️⃣ Approve provider
	@PostMapping("/providers/approve/{providerId}")
	public ResponseEntity<String> approveProvider(@PathVariable Long providerId) {

		providerServiceImpl.approveOrRejectProvider(providerId, true);
		return ResponseEntity.ok("Service provider approved successfully");
	}

	// 3️⃣ Reject provider
	@PostMapping("/providers/reject/{providerId}")
	public ResponseEntity<String> rejectProvider(@PathVariable Long providerId) {

		providerServiceImpl.approveOrRejectProvider(providerId, false);
		return ResponseEntity.ok("Service provider rejected successfully");
	}

	@PutMapping("/bookings/accept/{bookingId}")
	public ResponseEntity<String> acceptBooking(@PathVariable Long bookingId, Principal principal) {
		providerServiceImpl.acceptBooking(bookingId, principal.getName());
		return ResponseEntity.ok("Booking accepted");
	}

	@PutMapping("/bookings/complete/{bookingId}")
	public ResponseEntity<String> completeBooking(@PathVariable Long bookingId, Principal principal) {

		providerServiceImpl.completeBooking(bookingId, principal.getName());
		return ResponseEntity.ok("Booking marked as completed");
	}

	/* REJECT BOOKING */
	@PutMapping("/bookings/reject/{bookingId}")
	public ResponseEntity<String> rejectBooking(@PathVariable Long bookingId, Principal principal) {
		providerServiceImpl.rejectBooking(bookingId, principal.getName());
		return ResponseEntity.ok("Booking rejected");
	}
}
