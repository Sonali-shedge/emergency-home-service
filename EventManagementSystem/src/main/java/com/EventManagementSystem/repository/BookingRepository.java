package com.EventManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.dto.BookingResponseDTO;
import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.enumT.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	List<Booking> findByUser_UserId(Long userId);
	 
	 Optional<Booking> findByBookingIdAndUser_UserId(Long bookingId, Long userId);
	 
	    List<Booking> findByUserEmailOrderByBookingDateTimeDesc(String email);
	    
	    List<Booking> findByServiceProvider_Email(String email);
	    
	    List<Booking> findByServiceProviderAndStatusInOrderByBookingDateTimeAsc(
	            ServiceProvider provider,
	            List<BookingStatus> statusList
	    );
	
	}
	    
	   
	 
	

