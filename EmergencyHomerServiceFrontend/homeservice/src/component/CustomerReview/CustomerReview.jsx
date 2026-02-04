import React from "react";

const CustomerReview = () => {
  const reviews = [
    {
      name: "Rahul Sharma",
      location: "Pune",
      review:
        "Excellent service! The electrician arrived on time and fixed the issue quickly. Very professional and affordable.",
      rating: 5,
    },
    {
      name: "Anjali Patil",
      location: "Mumbai",
      review:
        "Booking was easy and the plumber was very polite. Work was neat and clean. Highly recommended!",
      rating: 4,
    },
    {
      name: "Amit Verma",
      location: "Bangalore",
      review:
        "AC servicing was done perfectly. Transparent pricing and great customer support.",
      rating: 5,
    },
  ];

  return (
    <div className="container py-5 mt-5">
      {/* Heading */}
      <div className="text-center mb-5">
        <h2 className="fw-bold">What Our Customers Say</h2>
        <p className="text-muted">
          Trusted by thousands of happy customers across India
        </p>
      </div>

      {/* Reviews */}
      <div className="row g-4">
        {reviews.map((item, index) => (
          <div className="col-md-4" key={index}>
            <div className="card h-100 shadow-sm border-0">
              <div className="card-body">
                <p className="text-muted">"{item.review}"</p>

                <div className="mt-4">
                  <h6 className="mb-0 fw-bold">{item.name}</h6>
                  <small className="text-muted">{item.location}</small>

                  <div className="mt-2 text-warning">
                    {"★".repeat(item.rating)}
                    {"☆".repeat(5 - item.rating)}
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CustomerReview;
