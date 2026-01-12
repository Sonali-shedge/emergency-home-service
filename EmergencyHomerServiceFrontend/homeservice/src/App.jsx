import React from 'react'
import Register from './component/Register/Register'
import Login from './component/Login/Login'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminDashboard from './component/Dashboard/AdminDashboard';
import ServiceProvider from './component/Dashboard/ServiceProvider';
import ServiceCategories from './component/Dashboard/ServiceCategories';
import Home from './component/Home/Home';
import Navbar from './component/Home/Navbar';
import Service from './component/Services/Service';
import ServicesSection from './component/Home/ServicesSection';

const App = () => {
  return (
    <>
    <Router>
      <Routes>
        {/* Default page → Register */}
        <Route path="/Home" element={<Home />} />

        {/* Login page */}
        <Route path="/Login" element={<Login />} />
         <Route path="/Admindashboard" element={<AdminDashboard />} />
         <Route path="/ServiceProvider" element={<ServiceProvider />} />
          <Route path="/ServiceCategories" element={<ServiceCategories />} />
          <Route path="/Services/:serviceCategoryName" element={<ServicesSection />} />
             {/* <Route path="/Service" element={<Services />} /> */}


      </Routes>
    </Router>
    </>
  )       
}

export default App