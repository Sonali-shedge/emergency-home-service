package com.EventManagementSystem.dto;

import java.time.LocalDateTime;

import com.EventManagementSystem.enumT.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusUpdateRequestDTO {
	
	private BookingStatus status;

}
