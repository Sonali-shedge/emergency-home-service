package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.BookingRequestDTO;
import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.BookingStatusUpdateRequestDTO;

public interface BookingService {
	
	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, String email);
	
	public List<BookingResponseDTO> getUserBookings(String email);
	
	public  String cancelBooking(Long bookingId, String email); 
	
	public  List<BookingResponseDTO> getAllBookings();
	
	public String updateBookingStatus(Long bookingId, BookingStatusUpdateRequestDTO requestDTO); 

}
