import { useEffect, useState } from "react";
import axios from "axios";
import "./ServiceCategories.css";

function ServiceCategories() {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:9059/api/admin/service-categories",
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setCategories(response.data);
    } catch (error) {
      console.error("Error fetching categories", error);
    }
  };

  const addCategory = async () => {
    if (!name || !description) {
      alert("Please fill all fields");
      return;
    }

    try {
      await axios.post(
        "http://localhost:9059/api/admin/service-categories",
        {
          serviceCategoryName: name,
          description: description,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setName("");
      setDescription("");
      fetchCategories();
    } catch (error) {
      console.error("Error adding category", error);
    }
  };

  const toggleStatus = async (id, isActive) => {
    try {
      await axios.put(
        `http://localhost:9059/api/admin/service-categories/${id}/status`,
        { active: !isActive },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      fetchCategories();
    } catch (error) {
      console.error("Error updating status", error);
    }
  };

  return (
    <div className="category-page">
      <h3>Service Categories</h3>

      {/* Add Category */}
      <div className="add-category">
        <input
          type="text"
          placeholder="Category Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="text"
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <button onClick={addCategory}>Add Category</button>
      </div>

      {/* Category Table */}
      <table className="category-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {categories.map((c) => (
            <tr key={c.id}>
              <td>{c.serviceCategoryName}</td>
              <td>{c.description}</td>
              <td>
                <span className={c.isActive ? "active" : "inactive"}>
                  {c.isActive ? "ACTIVE" : "INACTIVE"}
                </span>
              </td>
              <td>
                <button
                  className={c.isActive ? "btn-disable" : "btn-enable"}
                  onClick={() => toggleStatus(c.id, c.isActive)}
                >
                  {c.isActive ? "Deactivate" : "Activate"}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ServiceCategories;
