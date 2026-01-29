import "./HeroSection.css";

const HeroSection = () => {
  return (
    <div className="container mt-5">
      <div className="row align-items-start">

        {/* LEFT SIDE */}
         <div className="col-md-6 d-flex justify-content-center align-items-center">
          <h1 className="fw-bold display-5 text-center">
            Reliable home services at your doorstep — anytime, anywhere.
          </h1>
        </div>

        {/* RIGHT SIDE */}
        <div className="col-md-6">
          <div className="row g-3">
            <div className="col-6">
              <img
                src="/Images/image1.jpg"
                className="img-fluid rounded-4"
                alt="service 1"
              />
            </div>
            <div className="col-6">
              <img
                src="/Images/image2.jpg"
                className="img-fluid rounded-4"
                alt="service 2"
              />
            </div>
            <div className="col-6">
              <img
                src="/Images/image3.jpg"
                className="img-fluid rounded-4"
                alt="service 3"
              />
            </div>
            <div className="col-6">
              <img
                src="/Images/image4.jpg"
                className="img-fluid rounded-4"
                alt="service 4"
              />
            </div>
          </div>
        </div>

      </div>
    </div>
  );
};

export default HeroSection;