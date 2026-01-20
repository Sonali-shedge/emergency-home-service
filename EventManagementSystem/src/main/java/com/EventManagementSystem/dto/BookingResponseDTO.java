package com.EventManagementSystem.dto;

import java.time.LocalDateTime;

import com.EventManagementSystem.entity.Address;
import com.EventManagementSystem.entity.Services;
import com.EventManagementSystem.enumT.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
	
	

	 private Long bookingId;

	    private Long userId;
	   
	    private String serviceName;

	    private LocalDateTime bookingDateTime;

	    private String status; // PENDING, CONFIRMED, COMPLETED
	    private Double price;

	    private String address;
   
	
	

}
