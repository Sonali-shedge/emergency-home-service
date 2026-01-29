import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const BookService = () => {
  const { serviceId } = useParams();
  const navigate = useNavigate();

  const [service, setService] = useState(null);
  const [bookingDate, setBookingDate] = useState("");
  const [timeSlot, setTimeSlot] = useState("");
  const [addressId, setAddressId] = useState("");
  const [addresses, setAddresses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // Fetch service details
  useEffect(() => {
    fetchServiceDetails();
    fetchUserAddresses();
  }, [serviceId]);

  const fetchServiceDetails = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(
        `http://localhost:9059/api/admin/getServicesById/${serviceId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setService(res.data);
    } catch (err) {
      console.error(err);
      setError("Unable to load service details");
    }
  };

  const fetchUserAddresses = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get("http://localhost:9059/api/user/addresses", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAddresses(res.data);
      if (res.data.length > 0) setAddressId(String(res.data[0].addressId));
    } catch (err) {
      console.error("Failed to fetch addresses", err);
      setError("Failed to load addresses");
    }
  };

  const handleBooking = async () => {
    if (!bookingDate || !timeSlot || !addressId) {
      alert("Please fill all details");
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      setError("User not authenticated");
      return;
    }

    const hourMap = {
      "9AM - 11AM": "09:00:00",
      "11AM - 1PM": "11:00:00",
      "2PM - 4PM": "14:00:00",
      "4PM - 6PM": "16:00:00",
    };

    const bookingDateTime = `${bookingDate}T${hourMap[timeSlot]}`;

    const bookingPayload = {
      serviceId: Number(serviceId),
      bookingDateTime,
      price: service.startingPrice,
      addressId: Number(addressId),
    };

    setLoading(true);
    setError("");

    try {
      const res = await axios.post(
        "http://localhost:9059/api/user/createBooking",
        bookingPayload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const booking = res.data; // Backend returns full booking info including provider
      console.log(booking);
      alert(
        `Booking successful! Provider assigned: ${booking.providerName}`
      );

      navigate("/MyBookings", { state: { refresh: true } });
    } catch (err) {
      console.error("Booking error:", err);
      if (err.response) {
        const backendMessage =
          err.response.data && typeof err.response.data === "object"
            ? err.response.data.message
            : err.response.data;
        setError(backendMessage || "Booking failed");
      } else if (err.request) {
        setError("No response from server");
      } else {
        setError("Booking failed: " + err.message);
      }
    } finally {
      setLoading(false);
    }
  };

  if (!service) return <h3 className="text-center mt-5">Loading service...</h3>;

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Book Service</h2>

      {/* SERVICE SUMMARY */}
      <div className="card p-3 mb-4 shadow-sm">
        <h4>{service.serviceName}</h4>
        <p>{service.serviceDescription}</p>
        <h5 className="text-success">Starting at ₹{service.startingPrice}</h5>
      </div>

      {/* BOOKING FORM */}
      <div className="card p-4 shadow-sm">
        {error && <p className="text-danger">{error}</p>}

        <div className="mb-3">
          <label className="form-label">Select Date</label>
          <input
            type="date"
            className="form-control"
            value={bookingDate}
            onChange={(e) => setBookingDate(e.target.value)}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Select Time Slot</label>
          <select
            className="form-control"
            value={timeSlot}
            onChange={(e) => setTimeSlot(e.target.value)}
          >
            <option value="">-- Select Time Slot --</option>
            <option value="9AM - 11AM">9AM - 11AM</option>
            <option value="11AM - 1PM">11AM - 1PM</option>
            <option value="2PM - 4PM">2PM - 4PM</option>
            <option value="4PM - 6PM">4PM - 6PM</option>
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label fw-bold">Select Address</label>
          {addresses.length === 0 && (
            <p className="text-muted">No saved addresses found</p>
          )}
          {addresses.map((addr) => (
            <div className="form-check mb-2" key={addr.addressId}>
              <input
                className="form-check-input"
                type="radio"
                name="address"
                value={addr.addressId}
                checked={addressId === String(addr.addressId)}
                onChange={(e) => setAddressId(e.target.value)}
              />
              <label className="form-check-label">
                {addr.houseNumber}, {addr.street}, {addr.area}, {addr.city} –{" "}
                {addr.pincode}
              </label>
            </div>
          ))}
        </div>

        <button
          className="btn btn-primary"
          onClick={handleBooking}
          disabled={loading}
        >
          {loading ? "Booking..." : "Confirm Booking"}
        </button>
      </div>
    </div>
  );
};

export default BookService;
