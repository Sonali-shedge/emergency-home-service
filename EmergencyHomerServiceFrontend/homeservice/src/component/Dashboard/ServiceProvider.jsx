import { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceProvider.css";

function ServiceProviders() {
  const [providers, setProviders] = useState([]);

  useEffect(() => {
    fetchProviders();
  }, []);

  const fetchProviders = async () => {
    try {
      const response = await axios.get(
        "http://localhost:9059/api/admin/getAllServiceProviders",
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setProviders(response.data);
    } catch (error) {
      console.error("Error fetching providers", error);
    }
  };

//   const toggleAvailability = async (id, currentStatus) => {
//     try {
//       await axios.put(
//         `http://localhost:9059/api/admin/provider/${id}/availability`,
//         { available: !currentStatus },
//         {
//           headers: {
//             Authorization: `Bearer ${localStorage.getItem("token")}`,
//           },
//         }
//       );
//       fetchProviders();
//     } catch (error) {
//       console.error("Error updating status", error);
//     }
//   };

  return (
    <div className="service-provider-page">
      <h3>Service Providers</h3>

      <table className="provider-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Category</th>
            <th>Available</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {providers.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>{p.email}</td>
              <td>{p.serviceCategory}</td>
              <td>
                <span className={p.isAvailable ? "active" : "inactive"}>
                  {p.isAvailable ? "YES" : "NO"}
                </span>
              </td>
              <td>
                <button
                  className={p.isAvailable ? "btn-disable" : "btn-enable"}
                  onClick={() => toggleAvailability(p.id, p.isAvailable)}
                >
                  {p.isAvailable ? "Disable" : "Enable"}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ServiceProviders;
