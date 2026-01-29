import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ProviderDashboard.css";

const ProviderDashboard = () => {
  const token = localStorage.getItem("token"); // Provider token
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get("http://localhost:9059/api/provider/getProviderBookings", {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(res => {
      setBookings(Array.isArray(res.data) ? res.data : []);
    })
    .catch(err => {
      console.error("Error fetching provider bookings", err);
    })
    .finally(() => setLoading(false));
  }, [token]);

  const handleAction = (bookingId, action) => {
    // Placeholder: replace with actual backend API later
    console.log(`Action: ${action} on booking ${bookingId}`);

    // Update UI optimistically
    setBookings(prev =>
      prev.map(b =>
        b.bookingId === bookingId
          ? {
              ...b,
              status:
                action === "accept"
                  ? "CONFIRMED"
                  : action === "start"
                  ? "IN_PROGRESS"
                  : action === "complete"
                  ? "COMPLETED"
                  : b.status,
            }
          : b
      )
    );
  };

  if (loading) return <p style={{ textAlign: "center" }}>Loading bookings...</p>;

  if (bookings.length === 0)
    return <p style={{ textAlign: "center" }}>No bookings assigned yet.</p>;

  return (
    <div className="provider-dashboard-container">
      <h2>My Assigned Bookings</h2>

      {bookings.map(booking => (
        <div key={booking.bookingId} className="provider-booking-card">
          <h4>{booking.serviceName}</h4>
          <p><b>User:</b> {booking.userName}</p>
          <p>
            <b>Date:</b>{" "}
            {booking.bookingDateTime
              ? new Date(booking.bookingDateTime).toLocaleString()
              : "N/A"}
          </p>
          <p><b>Price:</b> ₹{booking.price}</p>
          <p><b>Address:</b> {booking.address}</p>
          <p>
            <b>Status:</b>{" "}
            <span className={`status ${booking.status}`}>
              {booking.status}
            </span>
          </p>

          {/* Conditional Action Buttons */}
          <div className="action-buttons">
            {booking.status === "PENDING" && (
              <button onClick={() => handleAction(booking.bookingId, "accept")}>
                Accept
              </button>
            )}
            {booking.status === "CONFIRMED" && (
              <button onClick={() => handleAction(booking.bookingId, "start")}>
                Start
              </button>
            )}
            {booking.status === "IN_PROGRESS" && (
              <button onClick={() => handleAction(booking.bookingId, "complete")}>
                Complete
              </button>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default ProviderDashboard;
