package com.EventManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.Booking;
import com.EventManagementSystem.entity.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	 List <Booking> findByUserId (Long userId);
	 
	 Optional <Booking> findByBookingIdAndUserId(Long bookingId , Long userId);
	 
	
}
