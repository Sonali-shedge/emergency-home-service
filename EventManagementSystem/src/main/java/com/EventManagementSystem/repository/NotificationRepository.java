package com.EventManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EventManagementSystem.entity.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
	 List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

	 long countByUserIdAndIsReadFalse(Long userId);
}
