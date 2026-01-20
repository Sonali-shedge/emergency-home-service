package com.EventManagementSystem.entity;

import java.time.LocalDateTime;



import com.EventManagementSystem.enumT.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;
    
    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Services service;

    private LocalDateTime bookingDateTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // PENDING, CONFIRMED, COMPLETED
    private Double price;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
}

