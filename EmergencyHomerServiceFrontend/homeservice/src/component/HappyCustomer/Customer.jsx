import { useEffect, useState } from "react";
import "./Customer.css";

function Customer() {
  const [count, setCount] = useState([0, 0, 0, 0]);
  const max = [500, 50, 10000, 100];

  useEffect(() => {
    const onScroll = () => {
      if (window.scrollY > 300) {
        const timer = setInterval(() => {
          setCount(prev =>
            prev.map((v, i) =>
              v < max[i] ? v + Math.ceil(max[i] / 80) : max[i]
            )
          );
        }, 30);

        window.removeEventListener("scroll", onScroll);
        return () => clearInterval(timer);
      }
    };

    window.addEventListener("scroll", onScroll);
  }, []);

  return (
    <div className="stats-card">
      <div>
        <h2>{count[0]}+</h2>
        <p>ATHLETES PHOTOGRAPHED</p>
      </div>
      <div>
        <h2>{count[1]}+</h2>
        <p>COMPETITIONS COVERED</p>
      </div>
      <div>
        <h2>{count[2].toLocaleString()}+</h2>
        <p>PHOTOS DELIVERED</p>
      </div>
      <div>
        <h2>{count[3]}%</h2>
        <p>CLIENT SATISFACTION</p>
      </div>
    </div>
  );
}

export default Customer;