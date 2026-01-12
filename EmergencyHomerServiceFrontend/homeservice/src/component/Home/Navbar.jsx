import React, { useEffect, useRef, useState } from "react";
import "./Navbar.css";
import { FaMapMarkerAlt, FaSearch, FaShoppingCart, FaUser } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
    const navigate = useNavigate();
    const [showServices, setShowServices] = useState(false);
    const serviceRef = useRef(null);
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

  return () => {
    document.removeEventListener("mousedown", handleClickOutside);
  };
}, []);

// handle click logic

const handleCategoryClick = (serviceCategoryName)=>
{
    setShowServices(false);
    navigate(`/services/${serviceCategoryName}`)
}

    return (
        <nav className="navbar">
            {/* Left Section */}
            <div className="navbar-left">
                <div className="logo">
                    {/* <span className="logo-box">UC</span> */}
                    <span className="logo-text">FixAtHome</span>
                </div>

                <ul className="nav-links">
                    <li className="active">Homes</li>
                    <li className="service-item" onClick={() => setShowServices(!showServices)
                    }>Services {showServices && <ul className="dropdown">
                        <li onClick={()=> handleCategoryClick("plumber")}>Plumber</li>
                        <li onClick={()=> handleCategoryClick("Electrcian")}>Electrician</li>
                        <li onClick={()=> handleCategoryClick("carpenter")}>carpenter</li>
                        </ul>}</li>
                    {/* <li>Beauty</li> */}
                </ul>
            </div>

            {/* Right Section */}
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

                <FaShoppingCart className="icon" />
                <FaUser className="icon" onClick={() => navigate("/Login")} title="Login" />
            </div>
        </nav>
    );
};

export default Navbar;
