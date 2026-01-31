import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Register.css";

function Register() {
  const navigate = useNavigate();

  /* ================= STATE ================= */
  const [cities, setCities] = useState([]);
  const [zones, setZones] = useState([]);
  const [serviceCategories, setServiceCategories] = useState([]);
  const [selectedCity, setSelectedCity] = useState("");

  const [user, setUser] = useState({
    userName: "",
    email: "",
    password: "",
    phone: "",
    role: "USER",
    experineceInYears: "",
    serviceCategoryId: null,
    zoneIds: [],
    address: [
      {
        houseNumber: "",
        street: "",
        state: "",
        pincode: "",
        city: "",
        zoneName: "",
        zoneId: null,
      },
    ],
  });

  /* ================= FETCH INITIAL DATA ================= */
  useEffect(() => {
    fetchCities();
    fetchServiceCategories();
  }, []);

  const fetchCities = async () => {
    try {
      const res = await axios.get("http://localhost:9059/api/zone/getAllCities");
      setCities(res.data);
    } catch (error) {
      console.error("Error fetching cities", error);
    }
  };

  const fetchServiceCategories = async () => {
    try {
      const res = await axios.get("http://localhost:9059/api/user/getAllServiceCategory");
      console.log("Service Categories:", res.data); // Added for debugging
      setServiceCategories(res.data);
    } catch (error) {
      console.error("Error fetching service categories", error);
    }
  };

  /* ================= HANDLERS ================= */
  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleAddressChange = (index, e) => {
    const updatedAddress = [...user.address];
    updatedAddress[index][e.target.name] = e.target.value;
    setUser({ ...user, address: updatedAddress });
  };

  const handleCityChange = async (city) => {
    setSelectedCity(city);
    const updatedAddress = [...user.address];
    updatedAddress[0].city = city;
    updatedAddress[0].zoneId = null;
    updatedAddress[0].zoneName = "";
    setUser({ ...user, address: updatedAddress, zoneIds: [] });

    try {
      const res = await axios.get(`http://localhost:9059/api/zone/getZoneByCity/${city}`);
      setZones(res.data);
    } catch (error) {
      console.error("Error fetching zones", error);
    }
  };

  const handleZoneChange = (zoneId, zoneName) => {
    const updatedAddress = [...user.address];
    updatedAddress[0].zoneId = zoneId;
    updatedAddress[0].zoneName = zoneName;
    updatedAddress[0].city = selectedCity;
    setUser({ ...user, address: updatedAddress, zoneIds: [zoneId] });
  };

  /* ================= SUBMIT ================= */
  const handleSubmit = async (e) => {
    e.preventDefault();
    const addr = user.address[0];

    if (!addr.city || !addr.zoneId) {
      alert("Please select city and zone");
      return;
    }

    if (user.role === "SERVICE_PROVIDER") {
      if (!user.experineceInYears || !user.serviceCategoryId) {
        alert("Please select service category and experience");
        return;
      }
    }

    try {
      await axios.post(
        "http://localhost:9059/api/auth/registerUser",
        user,
        { headers: { "Content-Type": "application/json" } }
      );
      alert("Registration Successful");
      navigate("/login");
    } catch (error) {
      console.error("Registration Error", error.response?.data || error);
      alert("Registration Failed");
    }
  };

  /* ================= UI ================= */
  return (
    <div className="register-container">
      <h2>Register</h2>

      <form onSubmit={handleSubmit}>
        {/* BASIC INFO */}
        <input
          name="userName"
          placeholder="User Name"
          value={user.userName}
          onChange={handleChange}
          required
        />
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={user.email}
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={user.password}
          onChange={handleChange}
          required
        />
        <input
          name="phone"
          placeholder="Phone"
          value={user.phone}
          onChange={handleChange}
          required
        />

        {/* ROLE */}
        <select name="role" value={user.role} onChange={handleChange}>
          <option value="USER">User</option>
          <option value="SERVICE_PROVIDER">Service Provider</option>
        </select>

        {/* SERVICE PROVIDER EXTRA */}
        {user.role === "SERVICE_PROVIDER" && (
          <>
            <select
              value={user.serviceCategoryId || ""}
              onChange={(e) =>
                setUser({
                  ...user,
                  serviceCategoryId: Number(e.target.value),
                })
              }
              required
            >
              <option value="" disabled hidden>
                Select Service Category
              </option>
              {serviceCategories.map((cat) => (
                <option key={cat.serviceCategoryId} value={cat.serviceCategoryId}>
                  {cat.serviceCategoryName}
                </option>
              ))}
            </select>

            <input
              type="number"
              name="experineceInYears"
              placeholder="Experience (Years)"
              value={user.experineceInYears}
              onChange={handleChange}
              required
            />
          </>
        )}

        {/* ADDRESS */}
        <input
          name="houseNumber"
          placeholder="House Number"
          onChange={(e) => handleAddressChange(0, e)}
          required
        />
        <input
          name="street"
          placeholder="Street"
          onChange={(e) => handleAddressChange(0, e)}
          required
        />

        {/* CITY */}
        <select
          value={selectedCity}
          onChange={(e) => handleCityChange(e.target.value)}
          required
        >
          <option value="">Select City</option>
          {cities.map((city, i) => (
            <option key={i} value={city}>
              {city}
            </option>
          ))}
        </select>

        {/* ZONE */}
        <select
          value={user.address[0].zoneId || ""}
          onChange={(e) => {
            const z = zones.find((zone) => zone.zoneId === Number(e.target.value));
            if (z) handleZoneChange(z.zoneId, z.zoneName);
          }}
          required
        >
          <option value="">Select Zone</option>
          {zones.map((zone) => (
            <option key={zone.zoneId} value={zone.zoneId}>
              {zone.zoneName}
            </option>
          ))}
        </select>

        <input
          name="state"
          placeholder="State"
          onChange={(e) => handleAddressChange(0, e)}
          required
        />
        <input
          name="pincode"
          placeholder="Pincode"
          onChange={(e) => handleAddressChange(0, e)}
          required
        />

        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;
