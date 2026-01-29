import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyBookings.css";

const MyBooking = () => {
  const token = localStorage.getItem("token");
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:9059/api/user/myBookings", {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(res => {
      console.log("Bookings API response:", res.data);
      setBookings(Array.isArray(res.data) ? res.data : []);
    })
    .catch(err => {
      console.error("Error fetching bookings", err);
    });
  }, [token]);

  const handleCancelBooking = (bookingId) => {
    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this booking?"
    );
    if (!confirmCancel) return;

    axios.put(
      `http://localhost:9059/api/user/${bookingId}/cancel`,
      {},
      { headers: { Authorization: `Bearer ${token}` } }
    )
    .then(() => {
      // Update UI instantly
      setBookings(prevBookings =>
        prevBookings.map(booking =>
          booking.bookingId === bookingId
            ? { ...booking, status: "CANCELLED" }
            : booking
        )
      );
    })
    .catch(err => {
      console.error("Cancel booking failed", err);
      alert("Unable to cancel booking");
    });
  };

  return (
    <div className="my-booking-container">
      <h2>My Bookings</h2>

      {bookings.length === 0 ? (
        <p className="no-bookings">No bookings found</p>
      ) : (
        bookings.map(booking => (
          <div key={booking.bookingId} className="booking-card">
            <h4>{booking.serviceName}</h4>

            <p>
              <b>Date:</b>{" "}
              {booking.bookingDateTime
                ? new Date(booking.bookingDateTime).toLocaleString()
                : "N/A"}
            </p>

            <p>
              <b>Status:</b>{" "}
              <span className={`status ${booking.status}`}>
                {booking.status}
              </span>
            </p>

            <p><b>Price:</b> ₹{booking.price}</p>
            <p><b>Provider:</b> {booking.providerName || "Not assigned"}</p>
            <p><b>Address:</b> {booking.address}</p>

            {/* ✅ Cancel Button */}
            {(booking.status === "PENDING" || booking.status === "CONFIRMED") && (
              <button
                className="cancel-btn"
                onClick={() => handleCancelBooking(booking.bookingId)}
              >
                Cancel Booking
              </button>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default MyBooking;
