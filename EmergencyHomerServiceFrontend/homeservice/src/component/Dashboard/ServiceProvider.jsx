import { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceProvider.css";

function ServiceProvider() {
  const [pendingProviders, setPendingProviders] = useState([]);
  const [allProviders, setAllProviders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [processingId, setProcessingId] = useState(null);

  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role"); // must be "ADMIN"

  useEffect(() => {
    setLoading(true);
    Promise.all([fetchPendingProviders(), fetchAllProviders()])
      .catch(() => setError("Failed to load providers"))
      .finally(() => setLoading(false));
  }, []);

  // ---------------- FETCH DATA ----------------
  const fetchPendingProviders = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9059/api/provider/providers/pending",
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setPendingProviders(res.data);
    } catch {
      setError("Failed to load pending providers");
    }
  };

  const fetchAllProviders = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9059/api/admin/getAllServiceProviders",
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAllProviders(res.data);
    } catch {
      setError("Failed to load all providers");
    }
  };

  // ---------------- ACTIONS ----------------
  // Approve Provider (ADMIN)
  const approveProvider = async (id) => {
    setProcessingId(id);
    try {
      await axios.post(
        `http://localhost:9059/api/provider/providers/approve/${id}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Provider approved successfully");
      fetchPendingProviders();
      fetchAllProviders();
    } catch {
      alert("Failed to approve provider");
    } finally {
      setProcessingId(null);
    }
  };

  // Reject Provider (ADMIN)
  const rejectProvider = async (id) => {
    setProcessingId(id);
    try {
      await axios.post(
        `http://localhost:9059/api/provider/providers/reject/${id}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Provider rejected successfully");
      fetchPendingProviders();
      fetchAllProviders();
    } catch {
      alert("Failed to reject provider");
    } finally {
      setProcessingId(null);
    }
  };

  // Block / Unblock Provider (ADMIN + APPROVED)
  const toggleBlock = async (id) => {
    try {
      await axios.put(
        `http://localhost:9059/api/provider/providers/toggle-block/${id}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Provider block status updated");
      fetchAllProviders();
    } catch (err) {
      alert(err.response?.data || "Only admin can block approved providers");
    }
  };

  // ---------------- UI ----------------
  return (
    <div className="service-provider-page">
      <h2>Service Providers Management</h2>

      {loading && <p style={{ textAlign: "center" }}>Loading providers...</p>}
      {!loading && error && <p style={{ color: "red" }}>{error}</p>}

      {/* -------- Pending Providers -------- */}
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
                      {role === "ADMIN" && (
                        <>
                          <button
                            className="btn-approve"
                            disabled={processingId === p.providerId}
                            onClick={() => approveProvider(p.providerId)}
                          >
                            Approve
                          </button>
                          <button
                            className="btn-reject"
                            disabled={processingId === p.providerId}
                            onClick={() => rejectProvider(p.providerId)}
                          >
                            Reject
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      )}

      {/* -------- All Providers -------- */}
      {!loading && !error && (
        <section style={{ marginTop: "2rem" }}>
          <h3>All Providers</h3>
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
                    {role === "ADMIN" && p.approvalStatus === "APPROVED" && (
                      <button
                        className={p.blocked ? "btn-unblock" : "btn-block"}
                        onClick={() => toggleBlock(p.providerId)}
                      >
                        {p.blocked ? "Unblock" : "Block"}
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      )}
    </div>
  );
}

export default ServiceProvider;
