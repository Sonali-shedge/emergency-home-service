package com.EventManagementSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.service.ProviderServiceImpl;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    ProviderServiceImpl providerServiceImpl;

    // 🔹 Get bookings for provider
    @GetMapping("/getProviderBookings")
    public ResponseEntity<List<BookingResponseDTO>> getProviderBookings(Principal principal) {
        String email = principal.getName();
        return new ResponseEntity<>(providerServiceImpl.getProviderBookings(email), HttpStatus.OK);
    }

    // 🔹 Get all pending provider requests
    @GetMapping("/providers/pending")
    public ResponseEntity<List<ServiceProviderResponseDTO>> getPendingProviders() {
        return ResponseEntity.ok(providerServiceImpl.getPendingProviders());
    }

    // 🔹 Approve provider
    @PostMapping("/providers/approve/{providerId}")
    public ResponseEntity<String> approveProvider(@PathVariable Long providerId) {
        providerServiceImpl.approveOrRejectProvider(providerId, true);
        return ResponseEntity.ok("Service provider approved successfully");
    }

    // 🔹 Reject provider
    @PostMapping("/providers/reject/{providerId}")
    public ResponseEntity<String> rejectProvider(@PathVariable Long providerId) {
        providerServiceImpl.approveOrRejectProvider(providerId, false);
        return ResponseEntity.ok("Service provider rejected successfully");
    }

    // 🔹 Toggle block/unblock provider
    @PutMapping("/providers/toggle-block/{id}")
    public ResponseEntity<String> toggleBlock(@PathVariable Long id) {
        providerServiceImpl.toggleBlockProvider(id);
        return ResponseEntity.ok("Provider block status updated");
    }

    // 🔹 Booking actions
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

    @PutMapping("/bookings/reject/{bookingId}")
    public ResponseEntity<String> rejectBooking(@PathVariable Long bookingId, Principal principal) {
        providerServiceImpl.rejectBooking(bookingId, principal.getName());
        return ResponseEntity.ok("Booking rejected");
    }
}
