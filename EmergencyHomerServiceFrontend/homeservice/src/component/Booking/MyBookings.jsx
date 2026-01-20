import React, { useEffect, useState } from "react";
import axios from "axios";

const MyBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    try {
      setLoading(true);
      setError("");

      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:9059/api/user/myBookings",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setBookings(response.data);
      setLoading(false);
    } catch (err) {
      console.error("Error fetching bookings:", err);
      setError("Failed to load bookings. Please try again.");
      setLoading(false);
    }
  };

  const cancelBooking = async (bookingId) => {
    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this booking?"
    );

    if (!confirmCancel) return;

    try {
      const token = localStorage.getItem("token");

      await axios.put(
        `http://localhost:9059/api/user/${bookingId}/cancel`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Booking cancelled successfully");

      // Refresh bookings
      fetchBookings();
    } catch (err) {
      console.error("Cancel booking failed:", err);
      alert(err.response?.data?.message || "Failed to cancel booking");
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-3">My Bookings</h2>

      {loading && <p>Loading your bookings...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {!loading && bookings.length === 0 && (
        <p>You have no bookings yet.</p>
      )}

      {!loading && bookings.length > 0 && (
        <table className="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Service</th>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
              <th>Price</th>
              <th>Address</th>
              <th>Action</th> {/* NEW */}
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking, index) => {
              const dateTime = new Date(booking.bookingDateTime);
              const canCancel =
                booking.status === "PENDING" ||
                booking.status === "CONFIRMED";

              return (
                <tr key={booking.bookingId}>
                  <td>{index + 1}</td>
                  <td>{booking.serviceName}</td>
                  <td>{dateTime.toLocaleDateString()}</td>
                  <td>
                    {dateTime.toLocaleTimeString([], {
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                  </td>
                  <td>{booking.status}</td>
                  <td>₹{booking.price}</td>
                  <td>{booking.address}</td>
                  <td>
                    {canCancel ? (
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() =>
                          cancelBooking(booking.bookingId)
                        }
                      >
                        Cancel
                      </button>
                    ) : (
                      <span className="text-muted">—</span>
                    )}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default MyBookings;
