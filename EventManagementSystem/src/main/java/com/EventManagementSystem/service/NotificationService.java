package com.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.entity.Notification;

public interface NotificationService {
	
	public  List<Notification> getNotifications(Long userId) ;
	
	public String markAsRead(Long notificationId); 
	
	public Long getUnreadCount(Long userId); 
	
	  void createNotification(Long userId, String message);
	

}
