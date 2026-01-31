import { useEffect, useState } from "react";
import "./AdminDashboard.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AdminDashboard() {
  const navigate = useNavigate();
  const [totalUsers, setTotalUsers] = useState(0);
  const [serviceProviders, setServiceProviders] = useState(0);

  useEffect(() => {
    fetchAllUsers();
    fetchServiceProviders();
  }, []);

  const fetchAllUsers = async () => {
    const token = localStorage.getItem("token");
    const response = await axios.get(
      "http://localhost:9059/api/user/allUser",
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    setTotalUsers(response.data.length);
  };

  const fetchServiceProviders = async () => {
    const token = localStorage.getItem("token");
    const response = await axios.get(
      "http://localhost:9059/api/admin/getAllServiceProviders",
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    setServiceProviders(response.data.length);
  };

  return (
    <div className="admin-wrapper">
      {/* Sidebar */}
      <aside className="admin-sidebar">
        <h2 className="logo">FixAtHome</h2>
        <ul>
          <li className="active">Dashboard</li>
          <li onClick={() => navigate("/admin/manage-users")}>
            Manage Users
          </li>
          <li onClick={() => navigate("/service-providers")}>
            Service Providers
          </li>
          <li onClick={() => navigate("/service-categories")}>
            Service Categories
          </li>
          <li onClick={() => navigate("/admin/bookings")}>
            Bookings
          </li>
          <li className="logout">Logout</li>
        </ul>
      </aside>

      {/* Main */}
      <main className="admin-content">
        <header className="admin-header">
          <h2>Admin Dashboard</h2>
          <span>Welcome, Admin 👋</span>
        </header>

        <section className="stats">
          <div className="stat-card">
            <h4>Total Users</h4>
            <p>{totalUsers}</p>
          </div>

          <div className="stat-card">
            <h4>Service Providers</h4>
            <p>{serviceProviders}</p>
          </div>

          <div className="stat-card">
            <h4>Active Requests</h4>
            <p>22</p>
          </div>

          <div className="stat-card">
            <h4>Completed Services</h4>
            <p>98</p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default AdminDashboard;
