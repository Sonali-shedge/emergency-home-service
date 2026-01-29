import React from "react";
import Navbar from "../Home/Navbar";
import { Outlet } from "react-router-dom";
import Footer from "../Home/Footer";

const UserLayout = () => {
  return (
    <>
      <Navbar />
       
      <Outlet />
      <Footer/>
     
    </>
  );
};

export default UserLayout;
