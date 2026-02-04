import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./NewAndNoteworthy.css";

const NewAndNoteworthy = () => {
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9059/api/user/getAllServiceCategory"
      );
      setCategories(res.data || []); // fallback to empty array
    } catch (err) {
      console.error("Failed to load categories", err);
    }
  };

  const handleClick = (categoryName) => {
    // Navigate to category-specific page
    navigate(`/services/${categoryName.toLowerCase()}`);
  };

 const getImageUrl = (imageUrl) => {
  return imageUrl ? `/Images/${imageUrl}` : "/Images/placeholder.png";
};

  return (
    <div className="new-and-noteworthy">
      <h2 className="section-title">New and noteworthy</h2>

      <div className="services-grid">
        {categories.map((cat) => (
          <div
            key={cat.serviceCategoryId}
            className="service-card"
            onClick={() => handleClick(cat.serviceCategoryName)}
          >
            {cat.isNew && <span className="badge">NEW</span>}

           <div className="image-container">
  <img
    src={getImageUrl(cat.imageUrl)}
    alt={cat.serviceCategoryName}
    className="service-image"
    onError={(e) => {
      e.target.onerror = null;
      e.target.src = "/Images/placeholder.png";
    }}
  />
</div>

            <p className="service-title">{cat.serviceCategoryName}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NewAndNoteworthy;
