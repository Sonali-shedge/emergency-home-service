package com.EventManagementSystem.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.entity.Notification;
import com.EventManagementSystem.repository.NotificationRepository;

@Service
public class NotificationServiceImpl  implements NotificationService{
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> getNotifications(Long userId) {
		return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}

	@Override
	public String markAsRead(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new RuntimeException("notification not found"));
		notification.setRead(true);
		notificationRepository.save(notification);
		return "Notification marked as read successfully";
	}

	@Override
	public Long getUnreadCount(Long userId) {
		return notificationRepository.countByUserIdAndIsReadFalse(userId);
	}

	@Override
	public void createNotification(Long userId, String message) {
		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setMessage(message);
		notification.setRead(false);
		notificationRepository.save(notification);
	}

	

	

	

}
