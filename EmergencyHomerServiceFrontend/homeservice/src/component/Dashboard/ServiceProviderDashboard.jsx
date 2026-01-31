import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceProviderDashboard.css";

const ServiceProviderDashboard = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [activeTab, setActiveTab] = useState("pending"); // "pending", "inprogress", "completed"

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchProviderBookings();
  }, []);

  const fetchProviderBookings = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9059/api/provider/getProviderBookings",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setBookings(res.data);
      console.log("Bookings from backend:", res.data); // <--- check statuses
    } catch (err) {
      console.error(err);
      setError("Failed to load bookings");
    } finally {
      setLoading(false);
    }
  };

  const handleAccept = async (bookingId) => {
    try {
      await axios.put(
        `http://localhost:9059/api/provider/bookings/accept/${bookingId}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchProviderBookings();
    } catch (err) {
      alert(err.response?.data?.message || "Failed to accept booking");
    }
  };

  const handleReject = async (bookingId) => {
    try {
      await axios.put(
        `http://localhost:9059/api/provider/bookings/reject/${bookingId}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchProviderBookings();
    } catch (err) {
      alert(err.response?.data?.message || "Failed to reject booking");
    }
  };

  const handleComplete = async (bookingId) => {
    try {
      await axios.put(
        `http://localhost:9059/api/provider/bookings/complete/${bookingId}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchProviderBookings();
    } catch (err) {
      alert(err.response?.data?.message || "Failed to complete booking");
    }
  };

  if (loading) return <h3>Loading bookings...</h3>;
  if (error) return <h3>{error}</h3>;

  // Filter bookings based on active tab
  const filteredBookings = bookings.filter((b) => {
  const status = b.status.toUpperCase(); // normalize to uppercase
  if (activeTab === "pending") return status === "PENDING";
  if (activeTab === "inprogress") return status === "IN_PROGRESS";
  return status === "COMPLETED" || status === "REJECTED";
});


  return (
    <div className="provider-dashboard">
      <h2>Provider Dashboard</h2>

      {/* Tabs */}
      <div className="tabs">
        <button
          className={activeTab === "pending" ? "active" : ""}
          onClick={() => setActiveTab("pending")}
        >
          Pending
        </button>
        <button
          className={activeTab === "inprogress" ? "active" : ""}
          onClick={() => setActiveTab("inprogress")}
        >
          In Progress
        </button>
        <button
          className={activeTab === "completed" ? "active" : ""}
          onClick={() => setActiveTab("completed")}
        >
          Completed / Rejected
        </button>
      </div>

      {/* Booking list */}
      {filteredBookings.length === 0 ? (
        <p>No bookings in this category</p>
      ) : (
        <div className="booking-list">
          {filteredBookings.map((booking) => (
            <div className="booking-card" key={booking.bookingId}>
              <h3>{booking.serviceName}</h3>
              <p>
                <strong>Date:</strong>{" "}
                {new Date(booking.bookingDateTime).toLocaleString()}
              </p>
              <p>
                <strong>Price:</strong> ₹{booking.price}
              </p>
              <p>
                <strong>Address:</strong> {booking.address}
              </p>
              <p>
                <strong>Status:</strong>{" "}
                <span className={`status-badge ${booking.status.toLowerCase()}`}>
                  {booking.status}
                </span>
              </p>

              {/* Actions */}
              <div className="actions">
                {booking.status === "PENDING" && (
                  <>
                    <button
                      className="accept-btn"
                      onClick={() => handleAccept(booking.bookingId)}
                    >
                      Accept
                    </button>
                    <button
                      className="reject-btn"
                      onClick={() => handleReject(booking.bookingId)}
                    >
                      Reject
                    </button>
                  </>
                )}
                {booking.status === "IN_PROGRESS" && (
                  <button
                    className="complete-btn"
                    onClick={() => handleComplete(booking.bookingId)}
                  >
                    Mark as Completed
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ServiceProviderDashboard;
