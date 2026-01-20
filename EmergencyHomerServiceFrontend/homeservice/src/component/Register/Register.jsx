import { useState } from "react";
import axios from "axios";
import "./Register.css";
import { useNavigate } from "react-router-dom";

function Register() {
    const navigate = useNavigate();
  const [user, setUser] = useState({
    userName: "",
    email: "",
    password: "",
    phone: "",
    role: { roleName: "USER" }, // default USER
    address: [
      {
        houseNumber: "",
        street: "",
        area: "",
        city: "",
        state: "",
        pincode: ""
      }
    ]
  });

   // handle user fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  // for role
  const handleRoleChange = (e) => {
    setUser({
      ...user,
      role: { roleName: e.target.value }
    });
  };

  // for address
  const handleAddressChange = (e) => {
  const { name, value } = e.target;

  const updatedAddress = [...user.address];

  updatedAddress[0][name] =
    name === "latitude" || name === "longitude"
      ? value === "" ? "" : parseFloat(value)   // ✅ convert to number
      : value;

  setUser({ ...user, address: updatedAddress });
};


  // handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:9059/api/auth/registerUser",// backend url
        {
          userName: user.userName,
          email: user.email,
          password: user.password,
          phone: user.phone,
          role: user.role.roleName, // send role as string
          address: user.address
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );

      console.log("User registered:", response.data);
      alert("Registration Successful");
      navigate("/Login");

      // reset form
      setUser({
        userName: "",
        email: "",
        password: "",
        phone: "",
        role: { roleName: "USER" },
        address: [
          {
            houseNumber: "",
            street: "",
            area: "",
            city: "",
            state: "",
            pincode: ""
          }
        ]
      });

    } catch (error) {
      console.error("Registration error:", error.response ? error.response.data : error);
      alert("Registration Failed Check console for details");
    }
  };

  return (
    <div className="register-container">
      <h2>User Registration</h2>

      <form onSubmit={handleSubmit}>

        <input
          type="text"
          name="userName"
          placeholder="User Name"
          value={user.userName}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={user.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={user.password}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="phone"
          placeholder="Phone"
          value={user.phone}
          onChange={handleChange}
          required
        />

        {/* ROLE */}
        <select value={user.role.roleName} onChange={handleRoleChange} required>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>

        <h4>Address</h4>

        <input type="text" name="houseNumber" placeholder="House Number"
          onChange={handleAddressChange} required />

        <input type="text" name="street" placeholder="Street"
          onChange={handleAddressChange} required />

        <input type="text" name="area" placeholder="Area"
          onChange={handleAddressChange} required />

        <input type="text" name="city" placeholder="City"
          onChange={handleAddressChange} required />

        <input type="text" name="state" placeholder="State"
          onChange={handleAddressChange} required />

        <input type="text" name="pincode" placeholder="Pincode"
          onChange={handleAddressChange} required />

        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;