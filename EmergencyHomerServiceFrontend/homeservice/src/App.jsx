import React from 'react'
import Register from './component/Register/Register'
import Login from './component/Login/Login'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminDashboard from './component/Dashboard/AdminDashboard';
import ServiceProvider from './component/Dashboard/ServiceProvider';
import ServiceCategories from './component/Dashboard/ServiceCategories';

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Default page → Register */}
        <Route path="/" element={<Register />} />

        {/* Login page */}
        <Route path="/login" element={<Login />} />
         <Route path="/Admindashboard" element={<AdminDashboard />} />
         <Route path="/ServiceProvider" element={<ServiceProvider />} />
          <Route path="/ServiceCategories" element={<ServiceCategories />} />
      </Routes>
    </Router>
  )       
}

export default App