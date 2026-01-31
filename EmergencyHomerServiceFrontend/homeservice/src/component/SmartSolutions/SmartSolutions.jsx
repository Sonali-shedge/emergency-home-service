import React from "react";

const SmartSolutions = () => {
  return (
    <div className="container py-5">
      <div className="row align-items-center">
        
        {/* Left SIDE */}
        <div className="col-lg-6 text-center">
          <video
            src="/SmartSolutions/video.mp4"
            className="img-fluid rounded shadow"
            autoPlay
            muted
            loop
          />
        </div>

        {/* Right side */}
        <div className="col-lg-6 mb-4 mb-lg-0">
          <h2 className="fw-bold mb-4 mt-5">
            Smart Solutions for Everyday Home Repairs
          </h2>

          <p className="text-muted mb-3">
            FixAtHome provides fast, reliable, and affordable home services for
            households and offices. We offer a smart and convenient solution for
            all your essential repair and maintenance needs on one platform.
          </p>

          <p className="text-muted mb-3">
            Finding a trusted plumber, electrician, or AC technician at the
            right time can be stressful — FixAtHome makes it simple and
            hassle-free. We use high-quality tools and modern techniques to
            ensure every job is completed efficiently, safely, and at a
            reasonable cost.
          </p>

          <p className="text-muted mb-3">
            Our team consists of trained, verified, and experienced professionals
            who believe in commitment, transparency, and customer satisfaction.
          </p>

          <p className="text-muted">
            Whether it’s a leaking pipe, electrical fault, or AC servicing and
            repair, our skilled technicians are ready to assist you.
          </p>
        </div>

      </div>
    </div>
  );
};

export default SmartSolutions;
