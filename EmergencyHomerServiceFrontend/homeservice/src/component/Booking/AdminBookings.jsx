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
      console.log(response.data);
    } catch (error) {
      console.error("Failed to fetch bookings", error);
    }
  };

  return (
    <TableContainer component={Paper}>
      <Typography variant="h5" sx={{ m: 2 }}>
        All Bookings (Admin)
      </Typography>

      <Table>
        <TableHead>
          <TableRow>
            <TableCell><b>Booking ID</b></TableCell>
            <TableCell><b>User ID</b></TableCell>
            <TableCell><b>Service</b></TableCell>
            <TableCell><b>Date & Time</b></TableCell>
            <TableCell><b>Status</b></TableCell>
            <TableCell><b>Price</b></TableCell>
            <TableCell><b>Address</b></TableCell>
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
              <TableCell>{booking.status}</TableCell>
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
