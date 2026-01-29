package com.EventManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceProvider {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long providerId;
//	private String providerName;
//	private Integer experineceInYears;
//	@Enumerated(EnumType.STRING)
//	private AvailabilityStatus availabilityStatus;
//	private Integer serviceRadiusinKm;
//	private Double rating;
//	private Boolean isVerified;
//	private LocalDateTime createdAt;
//	private Double latitude;
//	private Double longitude;
//
//	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//	@JoinColumn(name = "serviceCategoryId")
//	private ServiceCategory serviceCategory;
//
//	@PrePersist
//	public void onCreate() {
//		this.createdAt = LocalDateTime.now();
//
//	}
//
//}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long providerId;

    private String providerName;
    
    private String email;
    
    private Integer experineceInYears;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    private Double rating;
    private Boolean isVerified;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceCategoryId")
    private ServiceCategory serviceCategory;

    // 🔥 ZONE-BASED SERVICE (CORE)
    @ManyToMany
    @JoinTable(
        name = "provider_zone",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "zone_id")
    )
    private List<Zone> serviceZones;
    
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.approvalStatus = ApprovalStatus.PENDING;       
        this.availabilityStatus = AvailabilityStatus.UNAVAILABLE; 
        this.isVerified = false;                                
        this.rating = 0.0;
    }
}