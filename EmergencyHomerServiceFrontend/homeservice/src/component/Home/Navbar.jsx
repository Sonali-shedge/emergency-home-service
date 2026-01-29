import React, { useEffect, useRef, useState } from "react";
import "./Navbar.css";
import {
  FaMapMarkerAlt,
  FaSearch,
  FaShoppingCart,
  FaUser,
} from "react-icons/fa";
import { Link, useNavigate, useLocation } from "react-router-dom";
import NotificationBell from "../Notification/NotificationBell";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [showServices, setShowServices] = useState(false);
  const serviceRef = useRef(null);

  // 🔹 TEMP userId (replace with auth/context later)
  const userId = 1;

  // ✅ Close services dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        serviceRef.current &&
        !serviceRef.current.contains(event.target)
      ) {
        setShowServices(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () =>
      document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // ✅ Close dropdown on route change
  useEffect(() => {
    setShowServices(false);
  }, [location.pathname]);

  // ✅ Category click
  const handleCategoryClick = (serviceCategoryName) => {
    setShowServices(false);
    navigate(`/services/${serviceCategoryName}`);
  };

  return (
    <nav className="navbar">
      {/* LEFT SECTION */}
      <div className="navbar-left">
        <div className="logo">
          <span className="logo-text">FixAtHome</span>
        </div>

        <ul className="nav-links">
          {/* HOME */}
          <li>
            <Link to="/">Home</Link>
          </li>

          {/* SERVICES DROPDOWN */}
          <li
            className="service-item"
            ref={serviceRef}
            onClick={() => setShowServices((prev) => !prev)}
          >
            Services
            {showServices && (
              <ul className="dropdown">
                <li onClick={() => handleCategoryClick("plumber")}>
                  Plumber
                </li>
                <li onClick={() => handleCategoryClick("electrician")}>
                  Electrician
                </li>
                <li onClick={() => handleCategoryClick("carpenter")}>
                  Carpenter
                </li>
              </ul>
            )}
          </li>

          {/* MY BOOKINGS */}
          <li>
            <Link to="/myBookings">My Bookings</Link>
          </li>
        </ul>
      </div>

      {/* RIGHT SECTION */}
      <div className="navbar-right">
        <div className="location-box">
          <FaMapMarkerAlt />
          <span>Home, 52, Lane No. 3...</span>
        </div>

        <div className="search-box">
          <FaSearch />
          <input
            type="text"
            placeholder="Search for 'Microwave repair'"
          />
        </div>

        {/* 🔔 NOTIFICATION BELL */}
        <NotificationBell userId={userId} />

        <FaShoppingCart className="icon" />

        <FaUser
          className="icon"
          onClick={() => navigate("/login")}
          title="Login"
        />
      </div>
    </nav>
  );
};

export default Navbar;
