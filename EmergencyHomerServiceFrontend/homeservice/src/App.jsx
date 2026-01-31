import React from "react";
import Register from "./component/Register/Register";
import Login from "./component/Login/Login";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import AdminDashboard from "./component/Dashboard/AdminDashboard";
import ServiceProvider from "./component/Dashboard/ServiceProvider";
import ServiceCategories from "./component/Dashboard/ServiceCategories";
import ServiceProviderDashboard from "./component/Dashboard/ServiceProviderDashboard";
import ProviderDashboard from "./component/Dashboard/ProviderDashboard";
import ManageUsers from "./component/Dashboard/ManageUsers";

import Home from "./component/Home/Home";
import ServicesSection from "./component/Home/ServicesSection";
import ServiceDetails from "./component/Home/ServiceDetails";
import NewAndNoteworthy from "./component/Home/NewAndNoteworthy";

import ProtectedRoute from "./component/Login/ProtectedRoute";
import UserLayout from "./component/Layout/UserLayout";

import BookService from "./component/Booking/BookService";
import MyBookings from "./component/Booking/MyBookings";
import AdminBookings from "./component/Booking/AdminBookings";

import Customer from "./component/HappyCustomer/Customer";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* USER ROUTES */}
        <Route element={<ProtectedRoute><UserLayout /></ProtectedRoute>}>
          <Route path="/home" element={<Home />} />
          <Route path="/services/:serviceCategoryName" element={<ServicesSection />} />
          <Route path="/serviceDetails/:serviceId" element={<ServiceDetails />} />
          <Route path="/bookService/:serviceId" element={<BookService />} />
          <Route path="/myBookings" element={<MyBookings />} />
          <Route path="/happyCustomer" element={<Customer />} />
          <Route path="/new-and-noteworthy" element={<NewAndNoteworthy />} />
        </Route>

        {/* ADMIN ROUTES */}
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/service-categories" element={<ServiceCategories />} />
        <Route path="/admin/bookings" element={<AdminBookings />} />
        <Route path="/admin/manage-users" element={<ManageUsers />} />

        {/* SERVICE PROVIDER ROUTES */}
        <Route path="/service-providers" element={<ServiceProvider />} />
        <Route path="/service-provider/dashboard" element={<ServiceProviderDashboard />} />
        <Route path="/provider/dashboard" element={<ProviderDashboard />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
