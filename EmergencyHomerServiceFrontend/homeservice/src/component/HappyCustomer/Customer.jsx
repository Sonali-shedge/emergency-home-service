import { useEffect, useState } from "react";
import "./Customer.css";

function Customer() {
  const [count, setCount] = useState([0, 0, 0, 0]);
  const max = [500, 50, 10000, 100];

  useEffect(() => {
    let timer;

    const onScroll = () => {
      if (window.scrollY > 300) {
        timer = setInterval(() => {
          setCount(prev =>
            prev.map((v, i) =>
              v < max[i] ? v + Math.ceil(max[i] / 80) : max[i]
            )
          );
        }, 30);

        window.removeEventListener("scroll", onScroll);
      }
    };

    window.addEventListener("scroll", onScroll);

    return () => {
      window.removeEventListener("scroll", onScroll);
      clearInterval(timer);
    };
  }, []);

  return (
    <div className="services-container container">
  <h2 className="text-center mb-5">Our Growth & Achievements</h2>

  <div className="stats-card row text-center">
    <div className="col-12 col-sm-6 col-md-3 mb-3">
      <h2>{count[0]}+</h2>
      <p>TOTAL CUSTOMERS</p>
    </div>

    <div className="col-12 col-sm-6 col-md-3 mb-3">
      <h2>{count[1]}+</h2>
      <p>TOTAL TECHNICIANS</p>
    </div>

    <div className="col-12 col-sm-6 col-md-3 mb-3">
      <h2>{count[2].toLocaleString()}+</h2>
      <p>TOTAL SERVICES</p>
    </div>

    <div className="col-12 col-sm-6 col-md-3 mb-3">
      <h2>{count[3]}%</h2>
      <p>CLIENT SATISFACTION</p>
    </div>
  </div>
</div>

  );
}

export default Customer;
