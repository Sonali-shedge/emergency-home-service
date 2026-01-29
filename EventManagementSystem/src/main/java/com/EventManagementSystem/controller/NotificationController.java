package com.EventManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventManagementSystem.entity.Notification;
import com.EventManagementSystem.service.NotificationService;
import com.EventManagementSystem.service.NotificationServiceImpl;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationServiceImpl notificationServiceImpl;

	// 🔔 API 1: Get all notifications for user/admin
	@GetMapping("/{userId}")
	public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {

		return new ResponseEntity<List<Notification>>(notificationServiceImpl.getNotifications(userId), HttpStatus.OK);
	}

	// 🔔 API 2: Mark notification as read
	@PutMapping("/read/{notificationId}")
	public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {

		return new ResponseEntity<String>(notificationServiceImpl.markAsRead(notificationId), HttpStatus.OK);
	}

	// 🔔 API 3: Get unread notification count
	@GetMapping("/unread/count/{userId}")
	public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {

		return new ResponseEntity<Long>(notificationServiceImpl.getUnreadCount(userId), HttpStatus.OK);
	}
}
