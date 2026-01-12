import React from 'react'
 import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const ServicesSection = () => {
 

// function Service() {

  const { serviceCategoryName } = useParams();
  const [services, setServices] = useState([]);

  useEffect(() => {                
    fetchServices();
  }, [serviceCategoryName]);

  const fetchServices = async () => {
     const token = localStorage.getItem("token");
  console.log("TOKEN FROM STORAGE:", token);
    try {
      const response = await axios.get(
        `http://localhost:9059/api/admin/getAllServices/${serviceCategoryName}`,
{
         headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      );
      setServices(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching services", error);
   
  if (error.response) {
    // Backend responded with error status
    console.error("Status:", error.response.status);
    console.error("Data:", error.response.data);
  } else if (error.request) {
    // Request made but no response
    console.error("No response received:", error.request);
  } else {
    // Something else
    console.error("Error message:", error.message);
  }
}
    };


  return (
   <div className="services-container container mt-4">
  <h2 className="mb-4 text-center">
    {serviceCategoryName} Services
  </h2>

 

  <div className="row g-4">
    {services.map((service) => (
      <div key={service.serviceId} className="col-md-4">
        <div className="card h-100 shadow-sm">
          
          {/* DISPLAY IMAGE */}
          <img
            src={service.imageUrl}
            alt={service.serviceName}
            className="card-img-top"
            style={{ height: "200px", objectFit: "cover" }}
          />

          <div className="card-body d-flex flex-column">
            <h5 className="card-title">{service.serviceName}</h5>
            <p className="card-text">{service.serviceDescription}</p>
            <p className="card-text fw-bold">Starting at ₹{service.startingPrice}</p>
            <p className="card-text">⭐ {service.rating} ({service.totalReviews})</p>
            <button className="btn btn-primary mt-auto">View Details</button>
          </div>

        </div>
      </div>
    ))}
  </div>


</div>

  );
}



export default ServicesSection