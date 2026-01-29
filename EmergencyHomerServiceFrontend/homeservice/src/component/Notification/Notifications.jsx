import React, { useEffect, useState } from "react";
import axios from "axios";

const Notifications = ({ userId }) => {
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);

  useEffect(() => {
    if(userId)
    {
    fetchNotifications();
    fetchUnreadCount();
    }
  }, [userId]);

  const fetchNotifications = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9059/api/notifications/${userId}`
      );
      setNotifications(res.data);
    } catch (error) {
      console.error("Error fetching notifications", error);
    }
  };

  const fetchUnreadCount = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9059/api/notifications/unread/count/${userId}`
      );
      setUnreadCount(res.data);
    } catch (error) {
      console.error("Error fetching unread count", error);
    }
  };

  const markAsRead = async (notificationId) => {
    try {
      await axios.put(
        `http://localhost:9059/api/notifications/read/${notificationId}`
      );
      fetchNotifications();
      fetchUnreadCount();
    } catch (error) {
      console.error("Error marking as read", error);
    }
  };

  return (
    <div style={{ width: "350px", padding: "10px" }}>
      <h3>
        🔔 Notifications{" "}
        {unreadCount > 0 && (
          <span style={{ color: "red" }}>({unreadCount})</span>
        )}
      </h3>

      {notifications.length === 0 && <p>No notifications</p>}

      <ul style={{ listStyle: "none", padding: 0 }}>
        {notifications.map((notification) => (
          <li
            key={notification.notificationId}
            style={{
              backgroundColor: notification.isRead ? "#f5f5f5" : "#e6f0ff",
              marginBottom: "8px",
              padding: "8px",
              borderRadius: "5px",
              cursor: "pointer",
            }}
            onClick={() => markAsRead(notification.notificationId)}
          >
            <p style={{ margin: 0 }}>{notification.message}</p>
            <small>
              {new Date(notification.createdAt).toLocaleString()}
            </small>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Notifications;
