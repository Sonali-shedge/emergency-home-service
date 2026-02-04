import { useNavigate } from "react-router-dom";
const Footer = () => {

  const navigate = useNavigate();

  const handleClick = (serviceName) => {
    // Navigate to the service details page
    navigate(`/services/${serviceName.toLowerCase()}`);
  };
  return (
    <footer className="bg-white text-dark pt-5 mt-5 border-top">
      <div className="container">
        <div className="row">

          {/* About */}
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Emergency Home Services</h5>
            <p className="small">
              Fast, reliable, and professional home services available 24/7.
              We are here when you need us the most.
            </p>
          </div>

          {/* Services */}
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Our Services</h5>
            <ul className="list-unstyled small">
        <li onClick={() => handleClick("Plumber")} style={{cursor: "pointer"}}>Plumber</li>
        <li onClick={() => handleClick("Electrician")} style={{cursor: "pointer"}}>Electrician</li>
        <li onClick={() => handleClick("AC Repair")} style={{cursor: "pointer"}}>AC Repair</li>
        <li onClick={() => handleClick("Fan Repair")} style={{cursor: "pointer"}}>Fan Repair</li>
      </ul>
          </div>

          {/* Contact */}
          <div className="col-md-4 mb-4">
            <h5 className="text-primary">Contact Us</h5>
            <p className="small mb-1">📍 India</p>
            <a href="tel:919876543210"
            className="text-decoration-none d-block">
                📞 +91 98765 43210
            </a>
            <a href="mailto:fixathome34@gmail.com"
            className="text-decoration-none d-block">
                ✉️ fixathome34@gmail.com
            </a>
          </div>

        </div>

        <hr />

        <div className="text-center pb-3 small">
          © {new Date().getFullYear()} Emergency Home Services. All rights reserved.
        </div>
      </div>
    </footer>
  );
};

export default Footer;