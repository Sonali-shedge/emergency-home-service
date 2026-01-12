import { useEffect, useState } from "react";
import "./AdminDashboard.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function AdminDashboard() {
  const navigate = useNavigate();
  const [totalUsers , setTotalUsers]= useState(0);
  const [serviceProviders , setServiceProviders]= useState(0);

  useEffect(()=>
  {
    fetchAllUsers();
  } , [])
  useEffect(()=>
  {
    fetchServiceProviders();
  } , [])

  const fetchAllUsers = async () => {
  const token = localStorage.getItem("token");
  console.log("TOKEN FROM STORAGE:", token);

  try {
    const response = await axios.get(
      "http://localhost:9059/api/user/allUser",
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    setTotalUsers(response.data.length);
    console.log(response.data )
  } catch (error) {
  if (error.response) {
    // Backend responded with status code
    console.error("STATUS:", error.response.status);
    console.error("DATA:", error.response.data);
    console.error("HEADERS:", error.response.headers);
  } else if (error.request) {
    // Request sent but no response (CORS / Network)
    console.error("NO RESPONSE RECEIVED:", error.request);
  } else {
    // Something else
    console.error("ERROR MESSAGE:", error.message);
  }
}
  };

  const fetchServiceProviders = async () => {
  const token = localStorage.getItem("token");
  console.log("TOKEN FROM STORAGE:", token);

  try {
    const response = await axios.get(
      "http://localhost:9059/api/admin/getAllServiceProviders",
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    setServiceProviders(response.data.length);
    console.log(response.data )
  } catch (error) {
  if (error.response) {
    // Backend responded with status code
    console.error("STATUS:", error.response.status);
    console.error("DATA:", error.response.data);
    console.error("HEADERS:", error.response.headers);
  } else if (error.request) {
    // Request sent but no response (CORS / Network)
    console.error("NO RESPONSE RECEIVED:", error.request);
  } else {
    // Something else
    console.error("ERROR MESSAGE:", error.message);
  }
}
};
  return (
    <div className="admin-dashboard">

      {/* Sidebar */}
      <aside className="admin-sidebar">
        <h2 className="admin-logo">ADMIN PANEL</h2>
        <ul>
          <li>Dashboard</li>
          <li>Manage Users</li>
          <li onClick={()=> navigate("/ServiceProvider")} >Service Providers</li>
          <li onClick={()=> navigate("/ServiceCategories")}>Service Categories</li>
          <li>Service Requests</li>
          <li>Reports</li>
          <li className="logout">Logout</li>
        </ul>
      </aside>

      {/* Main Section */}
      <main className="admin-main">

        {/* Top Bar */}
        <header className="admin-topbar">
          <h3>Admin Dashboard</h3>
          <span>Welcome, Admin 👋</span>
        </header>

        {/* Stats Cards */}
        <section className="admin-cards">
          <div className="admin-card">
            <h4>Total Users</h4>
            <p>{totalUsers}</p>
          </div>

          <div className="admin-card">
            <h4>Service Providers</h4>
            <p>{serviceProviders}</p>
          </div>

          <div className="admin-card">
            <h4>Active Requests</h4>
            <p>22</p>
          </div>

          <div className="admin-card">
            <h4>Completed Services</h4>
            <p>98</p>
          </div>
        </section>

        {/* Recent Activity */}
        <section className="admin-table-section">
          <h4>Recent Service Requests</h4>
          <table className="admin-table">
            <thead>
              <tr>
                <th>Request ID</th>
                <th>User</th>
                <th>Category</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>#101</td>
                <td>John Miller</td>
                <td>Electrician</td>
                <td className="pending">PENDING</td>
              </tr>
              <tr>
                <td>#102</td>
                <td>Emma Johnson</td>
                <td>Plumber</td>
                <td className="completed">COMPLETED</td>
              </tr>
              <tr>
                <td>#103</td>
                <td>David Smith</td>
                <td>Cleaning</td>
                <td className="active">IN_PROGRESS</td>
              </tr>
            </tbody>
          </table>
        </section>

      </main>
    </div>
  );
}

export default AdminDashboard;
