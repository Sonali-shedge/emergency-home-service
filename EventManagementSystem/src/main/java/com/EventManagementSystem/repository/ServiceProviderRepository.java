package com.EventManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.ServiceProvider;
import com.EventManagementSystem.entity.User;
import com.EventManagementSystem.enumT.ApprovalStatus;
import com.EventManagementSystem.enumT.AvailabilityStatus;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

	List<ServiceProvider> findByServiceCategory_ServiceCategoryIdAndAvailabilityStatus(Long serviceCategoryId,
			AvailabilityStatus availabilityStatus);

	@Query("""
			    SELECT sp FROM ServiceProvider sp
			    JOIN sp.serviceZones z
			    WHERE z.zoneId = :zoneId
			      AND sp.serviceCategory.id = :categoryId
			      AND sp.availabilityStatus = 'AVAILABLE'
			""")
	List<ServiceProvider> findAvailableProviders( Long zoneId, Long categoryId , AvailabilityStatus status);
	
	List<ServiceProvider> findByApprovalStatus(ApprovalStatus status);

    // Login / mapping user → provider
    Optional<ServiceProvider> findByUser(User user);
}
