import { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceCategories.css";

function ServiceCategories() {
  const [categories, setCategories] = useState([]);
  const [services, setServices] = useState([]);
  const [selectedCategoryName, setSelectedCategoryName] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Category form toggle and states
  const [showCategoryForm, setShowCategoryForm] = useState(false);
  const [newCategoryName, setNewCategoryName] = useState("");
  const [newCategoryDescription, setNewCategoryDescription] = useState("");

  // Service form toggle and states
  const [showServiceForm, setShowServiceForm] = useState(false);
  const [newServiceName, setNewServiceName] = useState("");
  const [newServiceDescription, setNewServiceDescription] = useState("");
  const [newServicePrice, setNewServicePrice] = useState("");
  const [newServiceImage, setNewServiceImage] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  // Fetch all categories
  const fetchCategories = async () => {
    try {
      setLoading(true);
      setError("");
      const token = localStorage.getItem("token");

      const res = await axios.get(
        "http://localhost:9059/api/user/getAllServiceCategory",
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setCategories(res.data);
    } catch (err) {
      console.error("Error fetching categories:", err);
      setError("Failed to load categories");
    } finally {
      setLoading(false);
    }
  };

  // Fetch services under selected category
  const fetchServices = async (serviceCategoryName) => {
    try {
      setLoading(true);
      setError("");
      const token = localStorage.getItem("token");

      const res = await axios.get(
        `http://localhost:9059/api/admin/getAllServices/${serviceCategoryName}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setServices(res.data);
      setSelectedCategoryName(serviceCategoryName);
    } catch (err) {
      console.error("Error fetching services:", err);
      setError("Failed to load services");
    } finally {
      setLoading(false);
    }
  };

  // Create new category
  const createCategory = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      setError("");
      const token = localStorage.getItem("token");

      const requestBody = {
        serviceCategoryName: newCategoryName,
        description: newCategoryDescription,
      };

      const res = await axios.post(
        "http://localhost:9059/api/admin/createServiceCategory",
        requestBody,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setCategories((prev) => [...prev, res.data]);
      setNewCategoryName("");
      setNewCategoryDescription("");
      setShowCategoryForm(false);
      alert("Category created successfully!");
    } catch (err) {
      console.error("Error creating category:", err);
      setError("Failed to create category");
    } finally {
      setLoading(false);
    }
  };

  // Create new service under selected category
  const createService = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();
      formData.append("serviceName", newServiceName);
      formData.append("serviceDescription", newServiceDescription);
      formData.append("startingPrice", parseFloat(newServicePrice));
      if (newServiceImage) formData.append("image", newServiceImage);

      const res = await axios.post(
        `http://localhost:9059/api/admin/createServices/${selectedCategoryName}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            // "Content-Type": "multipart/form-data",
          },
        }
      );

      // Update services list
      setServices((prev) => [...prev, res.data]);

      // Reset form
      setNewServiceName("");
      setNewServiceDescription("");
      setNewServicePrice("");
      // setNewServiceImage(null);
      setShowServiceForm(false);

      alert("Service added successfully!");
    } catch (err) {
  console.error("Error creating service:", err);

  if (err.response) {
    // Backend responded with error status
    console.error("Status:", err.response.status);
    console.error("Data:", err.response.data);
    alert(err.response.data?.message || "Backend error while adding service");
  } else if (err.request) {
    // Request was sent but no response
    console.error("No response from server:", err.request);
    alert("No response from server");
  } else {
    // Something else happened
    console.error("Error message:", err.message);
    alert(err.message);
  }
}

  };

  return (
    <div className="service-categories-page">
      <h2>Service Categories</h2>

      {/* Add Category Button */}
      <button
        className="btn-add-category"
        onClick={() => setShowCategoryForm(!showCategoryForm)}
        style={{ marginBottom: "20px" }}
      >
        {showCategoryForm ? "Cancel" : "Add Category"}
      </button>

      {/* Category Form */}
      {showCategoryForm && (
        <form onSubmit={createCategory} className="create-category-form">
          <input
            type="text"
            placeholder="Category Name"
            value={newCategoryName}
            onChange={(e) => setNewCategoryName(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Description"
            value={newCategoryDescription}
            onChange={(e) => setNewCategoryDescription(e.target.value)}
            required
          />
          <button type="submit">Create Category</button>
        </form>
      )}

      {loading && <p style={{ textAlign: "center" }}>Loading...</p>}
      {!loading && error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}

      {/* Categories Table */}
      {!loading && !error && categories.length > 0 && (
        <div className="categories-table-wrapper">
          <table className="categories-table">
            <thead>
              <tr>
                <th>Category Name</th>
                <th>Description</th>
                <th>Active</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((cat) => (
                <tr key={cat.serviceCategoryId}>
                  <td>{cat.serviceCategoryName}</td>
                  <td>{cat.description}</td>
                  <td className={cat.isActive ? "status-active" : "status-inactive"}>
                    {cat.isActive ? "Active" : "Inactive"}
                  </td>
                  <td>
                    <button
                      className="btn-view"
                      onClick={() => fetchServices(cat.serviceCategoryName)}
                    >
                      View Services
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Services Table */}
      {selectedCategoryName && (
        <>
          {services.length > 0 ? (
            <div className="services-table-wrapper">
              <h3>Services under {selectedCategoryName}</h3>
              <table className="services-table">
                <thead>
                  <tr>
                    <th>Service Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    {/* <th>Image</th> */}
                  </tr>
                </thead>
                <tbody>
                  {services.map((s) => (
                    <tr key={s.id}>
                      <td>{s.serviceName}</td>
                      <td>{s.serviceDescription}</td>
                      <td>₹{s.startingPrice}</td>
                      {/* <td>
                        {s.imageUrl ? (
                          <img
                            src={s.imageUrl}
                            alt={s.serviceName}
                            style={{ width: "50px", height: "50px", objectFit: "cover" }}
                          />
                        ) : (
                          "No image"
                        )}
                      </td> */}
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p style={{ textAlign: "center" }}>No services available for this category</p>
          )}

          {/* Add Service Button */}
          <button
            className="btn-add-service"
            onClick={() => setShowServiceForm(!showServiceForm)}
            style={{ marginTop: "20px" }}
          >
            {showServiceForm ? "Cancel" : "Add Service"}
          </button>

          {/* Service Form */}
          {showServiceForm && (
            <form onSubmit={createService} className="create-service-form" style={{ marginTop: "10px" }}>
              <input
                type="text"
                placeholder="Service Name"
                value={newServiceName}
                onChange={(e) => setNewServiceName(e.target.value)}
                required
              />
              <input
                type="text"
                placeholder="Description"
                value={newServiceDescription}
                onChange={(e) => setNewServiceDescription(e.target.value)}
                required
              />
              <input
                type="number"
                placeholder="Starting Price"
                value={newServicePrice}
                onChange={(e) => setNewServicePrice(e.target.value)}
                required
                min="0"
                step="0.01"
              />
              {/* <input
                type="file"
                accept="image/*"
                onChange={(e) => setNewServiceImage(e.target.files[0])}
              /> */}
              <button type="submit">Create Service</button>
            </form>
          )}
        </>
      )}
    </div>
  );
}

export default ServiceCategories;
