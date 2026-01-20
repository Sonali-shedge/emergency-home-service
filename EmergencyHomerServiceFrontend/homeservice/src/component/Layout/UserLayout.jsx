import React from "react";
import Navbar from "../Home/Navbar";
import { Outlet } from "react-router-dom";

const UserLayout = () => {
  return (
    <>
      <Navbar />
      <Outlet />
    </>
  );
};

export default UserLayout;
