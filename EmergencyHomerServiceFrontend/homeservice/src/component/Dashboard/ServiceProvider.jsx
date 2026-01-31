import { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceProvider.css";

function ServiceProvider() {
  const [pendingProviders, setPendingProviders] = useState([]);
  const [allProviders, setAllProviders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [processingId, setProcessingId] = useState(null);

  useEffect(() => {
    setLoading(true);
    Promise.all([fetchPendingProviders(), fetchAllProviders()])
      .catch(() => setError("Failed to load providers"))
      .finally(() => setLoading(false));
  }, []);

  // Fetch pending providers
  const fetchPendingProviders = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        "http://localhost:9059/api/provider/providers/pending",
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setPendingProviders(res.data);
    } catch (err) {
      console.error("Error fetching pending providers:", err);
      setError("Failed to load pending providers");
    }
  };

  // Fetch all providers
  const fetchAllProviders = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        "http://localhost:9059/api/admin/getAllServiceProviders",
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAllProviders(res.data);
    } catch (err) {
      console.error("Error fetching all providers:", err);
      setError("Failed to load all providers");
    }
  };

  // Approve provider
  const approveProvider = async (id) => {
    setProcessingId(id);
    try {
      await axios.post(
        `http://localhost:9059/api/provider/providers/approve/${id}`,
        {},
        { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } }
      );
      alert("Provider approved successfully");
      fetchPendingProviders();
      fetchAllProviders();
    } catch (err) {
      console.error("Error approving provider:", err);
      alert("Failed to approve provider");
    } finally {
      setProcessingId(null);
    }
  };

  // Reject provider
  const rejectProvider = async (id) => {
    setProcessingId(id);
    try {
      await axios.post(
        `http://localhost:9059/api/provider/providers/reject/${id}`,
        {},
        { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } }
      );
      alert("Provider rejected successfully");
      fetchPendingProviders();
      fetchAllProviders();
    } catch (err) {
      console.error("Error rejecting provider:", err);
      alert("Failed to reject provider");
    } finally {
      setProcessingId(null);
    }
  };

  return (
    <div className="service-provider-page">
      <h2>Service Providers Management</h2>

      {/* Loading state */}
      {loading && <p style={{ textAlign: "center" }}>Loading providers...</p>}

      {/* Error state */}
      {!loading && error && (
        <p style={{ textAlign: "center", color: "red" }}>{error}</p>
      )}

      {/* Pending Providers */}
      {!loading && !error && (
        <section>
          <h3>Pending Providers</h3>
          {pendingProviders.length === 0 ? (
            <p>No pending providers</p>
          ) : (
            <table className="provider-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Category</th>
                  <th>Zones</th>
                  <th>Experience</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {pendingProviders.map((p) => (
                  <tr key={p.providerId}>
                    <td>{p.providerName}</td>
                    <td>{p.email}</td>
                    <td>{p.phone}</td>
                    <td>{p.categoryName}</td>
                    <td>{p.zoneNames?.join(", ")}</td>
                    <td>{p.experienceInYears} yrs</td>
                    <td>{p.approvalStatus}</td>
                    <td>
                      <button
                        disabled={processingId === p.providerId}
                        className="btn-approve"
                        onClick={() => approveProvider(p.providerId)}
                      >
                        Approve
                      </button>
                      <button
                        disabled={processingId === p.providerId}
                        className="btn-reject"
                        onClick={() => rejectProvider(p.providerId)}
                      >
                        Reject
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      )}

      {/* All Providers */}
      {!loading && !error && (
        <section style={{ marginTop: "2rem" }}>
          <h3>All Providers</h3>
          {allProviders.length === 0 ? (
            <p>No providers found</p>
          ) : (
             <table className="provider-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Category</th>
                  <th>Zones</th>
                  <th>Experience</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {allProviders.map((p) => (
                  <tr key={p.providerId}>
                    <td>{p.providerName}</td>
                    <td>{p.email}</td>
                    <td>{p.phone}</td>
                    <td>{p.categoryName}</td>
                    <td>{p.zoneNames?.join(", ")}</td>
                    <td>{p.experienceInYears} yrs</td>
                    <td>{p.approvalStatus}</td>
                    <td>
                      {/* <button
                        disabled={processingId === p.providerId}
                        className="btn-approve"
                        onClick={() => approveProvider(p.providerId)}
                      >
                        Approve
                      </button>
                      <button
                        disabled={processingId === p.providerId}
                        className="btn-reject"
                        onClick={() => rejectProvider(p.providerId)}
                      >
                        Reject
                      </button> */}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      )}
    </div>
  );
}

export default ServiceProvider;
