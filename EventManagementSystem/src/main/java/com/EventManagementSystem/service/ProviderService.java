package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.dto.ServiceProviderRequestDTO;
import com.EventManagementSystem.dto.ServiceProviderResponseDTO;
import com.EventManagementSystem.entity.ServiceProvider;

public interface ProviderService {

	public ServiceProviderResponseDTO createServiceProvider(ServiceProviderRequestDTO serviceProviderRequestDTO);
	
	public List<ServiceProviderResponseDTO> getAllServiceProviders();
	
	public List<BookingResponseDTO> getProviderBookings(String email); 
	
	public void acceptBooking(Long bookingId, String name) ;
	
	public void rejectBooking(Long bookingId, String name) ;
	
	public void completeBooking(Long bookingId, String name);
	
	
	

}
