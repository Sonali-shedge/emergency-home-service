package com.EventManagementSystem.dto;

import java.time.LocalDateTime;

import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Services;
import com.EventManagementSystem.enumT.BookingStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
//	    private Long bookingId;
//
//	    private Long userId;
//	   
//	    private Services service;
//
//	    private LocalDateTime bookingDateTime;
//
//	    private BookingStatus status; // PENDING, CONFIRMED, COMPLETED
//	    private Double price;
//
//	    private Address address;
	
	
	private Long serviceId;
    private Long addressId;
    private Double price;

    // optional (if user selects date)
    private LocalDateTime bookingDateTime;

}
