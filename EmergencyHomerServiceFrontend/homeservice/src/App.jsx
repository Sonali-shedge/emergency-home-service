import React from 'react'
import Register from './component/Register/Register'
import Login from './component/Login/Login'
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import AdminDashboard from './component/Dashboard/AdminDashboard';
import ServiceProvider from './component/Dashboard/ServiceProvider';
import ServiceCategories from './component/Dashboard/ServiceCategories';
import Home from './component/Home/Home';
import Navbar from './component/Home/Navbar';
import Service from './component/Services/Service';
import ServicesSection from './component/Home/ServicesSection';
import AdminRoute from './component/Login/AdminRoute';
import ProtectedRoute from './component/Login/ProtectedRoute';
import ServiceDetails from './component/Home/ServiceDetails';
import UserLayout from './component/Layout/UserLayout';
import BookService from './component/Booking/BookService';
import MyBookings from './component/Booking/MyBookings';
import AdminBookings from './component/Booking/AdminBookings';
import AdminLayout from './component/Layout/AdminLayout';
import Customer from './component/HappyCustomer/Customer';
import ProviderDashboard from './component/Dashboard/ProviderDashboard';


const App = () => {
  return (
    <BrowserRouter>
    
      <Routes>

        <Route path="/" element={<Navigate to="/Login" replace />} />

        <Route path="/login" element={<Login />} />
        <Route path="/providerDashboard" element={<ProviderDashboard />} />
        <Route path="/register" element={<Register />} />


        {/* USER */}
        <Route element={<ProtectedRoute><UserLayout /></ProtectedRoute>}>

          <Route path="/home" element={<Home />} />

          <Route
            path="/services/:serviceCategoryName"
            element={<ServicesSection />}
          />

          <Route
            path="/serviceDetails/:serviceId"
            element={<ServiceDetails />}
          />

          <Route
            path="/bookService/:serviceId"
            element={<BookService />}
          />
          <Route
            path="/myBookings"
            element={<MyBookings />}
          />
           <Route
            path="/happyCustomer"
            element={<Customer />}
          />

          

        </Route>
        {/* ADMIN */}

        <Route
          path="/admin/dashboard"
          element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
        />

        <Route
          path="/admin/bookings"
          element={
            <AdminRoute>
              <AdminBookings />
            </AdminRoute>
          }
        />

        <Route
          path="/admin/bookings"
          element={
            <AdminRoute>
              <AdminBookings />
            </AdminRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App