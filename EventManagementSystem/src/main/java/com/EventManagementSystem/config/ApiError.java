package com.EventManagementSystem.config;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ApiError {
	
	private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
