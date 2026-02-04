package com.EventManagementSystem.dto;

import java.util.List;
import com.EventManagementSystem.enumT.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderResponseDTO {

    private Long providerId;
    private String providerName;
    private String email;
    private String phone;
    private Boolean blocked;
    private Integer experienceInYears;

    private AvailabilityStatus availabilityStatus;
    private String approvalStatus;

    private Double rating;
    private Boolean isVerified;

    private Long serviceCategoryId;  // ID of the service category
    private String categoryName;      // Name of the service category
    private List<String> zoneNames;   // Names of zones assigned to provider
}
