import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";


const ServiceDetails = () => {
  const { serviceId } = useParams();
  const navigate = useNavigate();
  const [service, setService] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchServiceDetails();
  }, [serviceId]);

  const fetchServiceDetails = async () => {
    const token = localStorage.getItem("token");
    setLoading(true);
    setError("");

    try {
      const response = await axios.get(
        `http://localhost:9059/api/admin/getServicesById/${serviceId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setService(response.data);
    } catch (err) {
  console.error("Full error object:", err);

  if (err.response) {
    // Backend responded with status code outside 2xx
    setError(
      err.response.data?.message || "Server responded with an error"
    );
  } else if (err.request) {
    // Request made but no response
    setError("No response from server. Please try again later.");
  } else {
    // Something else happened
    setError(err.message || "Something went wrong");
  }
}
 finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <h3 className="text-center mt-5">Loading service details...</h3>;
  }

  if (error) {
    return <h3 className="text-center mt-5 text-danger">{error}</h3>;
  }

  return (
    <div className="container mt-5">
      <div className="row">

        {/* IMAGE */}
        <div className="col-md-6">
          <img
            src={service.imageUrl}
            alt={service.serviceName}
            className="img-fluid rounded shadow"
          />
        </div>

        {/* DETAILS */}
        <div className="col-md-6">
          <h2>{service.serviceName}</h2>
          <p className="mt-3">{service.serviceDescription}</p>

          <h4 className="text-success mt-3">
            Starting at ₹{service.startingPrice}
          </h4>

          <p className="mt-2">
            ⭐ {service.rating} ({service.totalReviews} reviews)
          </p>

          {service.duration && <p>⏱ Duration: {service.duration}</p>}

          {/* BOOK SERVICE BUTTON */}
          <button
            className="btn btn-primary mt-4"
            onClick={() => navigate(`/bookService/${serviceId}`)}
          >
            Book Service
          </button>
        </div>

      </div>
    </div>
  );
};

export default ServiceDetails;
