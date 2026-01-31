import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function UpdateUser() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [addresses, setAddresses] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");

        axios
            .get(`http://localhost:9059/api/user/filterUser/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
            .then((res) => {
                setName(res.data.userName);
                setEmail(res.data.email)
                setPhone(res.data.phone);
                setAddresses(res.data.address || [])
            })
            .catch((err) => console.log(err));
    }, [id]);

    const submitUpdate = async () => {
        const token = localStorage.getItem("token");

        try {
            await axios.put(
                `http://localhost:9059/api/user/updateUser/${id}`,
                { userName: name, email, phone },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            alert("User updated successfully");
            navigate("/admin/manage-users");   //back
        } catch (err) {
            console.error(err);
            alert("Update failed");
        }
    };

    return (
  <div style={{ padding: "20px", width: "500px" }}>
    <h2>Update User</h2>

    <div style={{ marginBottom: "10px" }}>
      <input
        style={{ width: "100%", marginBottom: "8px" }}
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="Name"
      />

      <input
        style={{ width: "100%", marginBottom: "8px" }}
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />

      <input
        style={{ width: "100%" }}
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        placeholder="Phone"
      />
    </div>

    <h4>Address (read only)</h4>

        <div style={{ marginBottom: "15px" }}>
      {addresses.length ? (
        addresses.map((a, i) => (
          <textarea
            key={i}
            readOnly
            value={`${a.houseNumber}, ${a.street}, ${a.area}, ${a.city}, ${a.state} - ${a.pincode}`}
            style={{
              width: "100%",
              height: "70px",
              marginBottom: "8px",
              backgroundColor: "#f3f3f3",
              resize: "none",
            }}
          />
        ))
      ) : (
        <p>No address available</p>
      )}
    </div>

        <div style={{ display: "flex", gap: "10px", justifyContent: "flex-end" }}>
      <button onClick={submitUpdate}>Save</button>
      <button onClick={() => navigate(-1)}>Cancel</button>
    </div>
  </div>
);


}

export default UpdateUser;
