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
  const [addresses, setAddresses] = useState([]); // ✅ store addresses from backend
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchServiceDetails();
    fetchUserAddresses();
  }, [serviceId]);

  // Fetch service details
  const fetchServiceDetails = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(
        `http://localhost:9059/api/admin/getServicesById/${serviceId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setService(res.data);
    } catch (err) {
      console.error(err);
      setError("Unable to load service details");
    }
  };

  // Fetch user addresses from backend
  const fetchUserAddresses = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(
        "http://localhost:9059/api/user/addresses",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setAddresses(res.data);

      // auto-select first address as default
      if (res.data.length > 0) setAddressId(String(res.data[0].addressId));
    } catch (err) {
      console.error("Failed to fetch addresses", err);
    }
  };

  // Handle booking
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
      await axios.post(
        "http://localhost:9059/api/user/createBooking",
        bookingPayload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      alert("Booking successful!");
      navigate("/MyBookings");
    } catch (err) {
      console.error("Booking error:", err);
      if (err.response) {
        setError(err.response.data?.message || "Booking failed");
      } else {
        setError("Server not responding");
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

        {/* RADIO BUTTONS FOR ADDRESSES */}
        <div className="mb-3">
          <label className="form-label fw-bold">Select Address</label>
          {addresses.length === 0 && <p className="text-muted">No saved addresses found</p>}
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
                {addr.houseNumber}, {addr.street}, {addr.area}, {addr.city} – {addr.pincode}
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
