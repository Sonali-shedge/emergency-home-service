import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
} from "@mui/material";

const AdminBookings = () => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    getAllBookings();
  }, []);

  // 🔹 Fetch all bookings
  const getAllBookings = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:9059/api/admin/allBookings",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setBookings(response.data);
      console.log("All bookings:", response.data);
    } catch (error) {
      console.error("Failed to fetch bookings", error);
    }
  };

  // 🔹 Update booking status
  const updateBookingStatus = async (bookingId, status) => {
    try {
      const token = localStorage.getItem("token");

      await axios.put(
        `http://localhost:9059/api/admin/${bookingId}/status`,
        { status },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      getAllBookings(); // refresh table
    } catch (error) {
      console.error("Failed to update booking status", error);
    }
  };

  return (
    <TableContainer component={Paper} sx={{ mt: 3 }}>
      <Typography variant="h5" sx={{ m: 2 }}>
        All Bookings (Admin)
      </Typography>

      <Table>
        <TableHead>
          <TableRow>
            <TableCell sx={{ fontWeight: "bold" }}>Booking ID</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>User ID</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>Service</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>Date & Time</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>Status</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>Price</TableCell>
            <TableCell sx={{ fontWeight: "bold" }}>Address</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {bookings.map((booking) => (
            <TableRow key={booking.bookingId}>
              <TableCell>{booking.bookingId}</TableCell>
              <TableCell>{booking.userId}</TableCell>
              <TableCell>{booking.serviceName}</TableCell>
              <TableCell>
                {new Date(booking.bookingDateTime).toLocaleString()}
              </TableCell>

              {/* 🔹 STATUS DROPDOWN */}
              <TableCell>
                <select
                  value={booking.status}
                  disabled={
                    booking.status === "COMPLETED" ||
                    booking.status === "CANCELLED"
                  }
                  onChange={(e) =>
                    updateBookingStatus(
                      booking.bookingId,
                      e.target.value
                    )
                  }
                >
                  <option value="PENDING">PENDING</option>
                  <option value="CONFIRMED">CONFIRMED</option>
                  <option value="COMPLETED">COMPLETED</option>
                  <option value="CANCELLED">CANCELLED</option>
                </select>
              </TableCell>
              <TableCell>₹{booking.price}</TableCell>
              <TableCell>{booking.address}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default AdminBookings;
