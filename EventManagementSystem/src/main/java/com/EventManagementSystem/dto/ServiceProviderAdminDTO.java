package com.EventManagementSystem.dto;

import java.util.List;

import com.EventManagementSystem.enumT.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderAdminDTO {
	
	private Long providerId;
    private String providerName;
    private String email;
    private String phone;
    private String categoryName;
    private List<String> zoneNames;
    private Integer experienceInYears;
    private ApprovalStatus approvalStatus;

}
