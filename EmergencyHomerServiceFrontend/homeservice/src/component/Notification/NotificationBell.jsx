import React, { useEffect, useState } from "react";
import axios from "axios";
import "./NotificationBell.css";

const NotificationBell = ({ userId }) => {
  const [open, setOpen] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);

  useEffect(() => {
    if (userId) {
      fetchUnreadCount();
    }
  }, [userId]);

  const fetchUnreadCount = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9059/api/notifications/unread/count/${userId}`
      );
      setUnreadCount(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchNotifications = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9059/api/notifications/${userId}`
      );
      setNotifications(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const toggleBell = async () => {
    setOpen(!open);
    if (!open) {
      await fetchNotifications();
    }
  };

  const markAsRead = async (id) => {
    try {
      await axios.put(
        `http://localhost:9059/api/notifications/read/${id}`
      );
      fetchNotifications();
      fetchUnreadCount();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="bell-container">
      <div className="bell-icon" onClick={toggleBell}>
        🔔
        {unreadCount > 0 && (
          <span className="badge">{unreadCount}</span>
        )}
      </div>

      {open && (
        <div className="dropdown">
          <div className="dropdown-header">Notifications</div>

          {notifications.length === 0 && (
            <div className="empty">No notifications</div>
          )}

          {notifications.map((n) => (
            <div
              key={n.notificationId}
              className={`notification-item ${
                n.isRead ? "read" : "unread"
              }`}
              onClick={() => markAsRead(n.notificationId)}
            >
              <p>{n.message}</p>
              <small>
                {new Date(n.createdAt).toLocaleString()}
              </small>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default NotificationBell;
