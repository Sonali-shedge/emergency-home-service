import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
// import "./ServicesSection.css"; // Make sure your CSS file exists

const ServicesSection = () => {
  const { serviceCategoryName } = useParams();
  const navigate = useNavigate();
  const [services, setServices] = useState([]);

  useEffect(() => {
    fetchServices();
  }, [serviceCategoryName]);

  const fetchServices = async () => {
    const token = localStorage.getItem("token");
    try {
      const response = await axios.get(
        `http://localhost:9059/api/admin/getAllServices/${serviceCategoryName}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setServices(response.data || []);
      console.log("Fetched services:", response.data);
    } catch (error) {
      console.error("Error fetching services", error);
      if (error.response) {
        console.error("Status:", error.response.status);
        console.error("Data:", error.response.data);
      } else if (error.request) {
        console.error("No response received:", error.request);
      } else {
        console.error("Error message:", error.message);
      }
    }
  };

  // Helper to get image URL from public folder
  const getImageUrl = (imageUrl) => {
    return imageUrl ? `/Images/${imageUrl}` : "/Images/placeholder.png";
  };

  return (
    <div className="services-container container mt-4">
      {/* Display category name */}
      <h2 className="mb-4 text-center text-primary">
        {serviceCategoryName?.toUpperCase()}
      </h2>

      <div className="row g-4">
        {services.length > 0 ? (
          services.map((service) => (
            <div key={service.serviceId} className="col-md-4">
              <div
                className="card h-100 shadow-sm service-card"
                onClick={() => navigate(`/serviceDetails/${service.serviceId}`)}
                style={{ cursor: "pointer" }}
              >
                <img
                  src={getImageUrl(service.imageUrl)}
                  alt={service.serviceName}
                  className="card-img-top"
                  style={{ height: "200px", objectFit: "cover" }}
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = "/Images/placeholder.png";
                  }}
                />

                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">{service.serviceName}</h5>
                  <p className="card-text">{service.serviceDescription}</p>
                  <p className="card-text fw-bold">
                    Starting at ₹{service.startingPrice}
                  </p>
                  <p className="card-text">
                    ⭐ {service.rating} ({service.totalReviews})
                  </p>
                  <button className="btn btn-primary mt-auto">
                    View Details
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <p className="text-center">No services available.</p>
        )}
      </div>
    </div>
  );
};

export default ServicesSection;
