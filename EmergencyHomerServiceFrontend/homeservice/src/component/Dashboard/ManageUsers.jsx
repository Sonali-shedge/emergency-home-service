import { useEffect, useState } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import { Phone } from "lucide-react";

function ManageUsers() {
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");

        axios
            .get("http://localhost:9059/api/user/allUser", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
            .then((res) => {
                setUsers(res.data);
            })
            .catch((err) => console.log(err));
    }, []);

    // Deleted user
    const handleDeleteUser = async (userId) => {
        const token = localStorage.getItem("token");

        if (!window.confirm("Are you sure?")) return;

        try {
            await axios.delete(
                `http://localhost:9059/api/user/delete/${userId}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setUsers(users.filter((u) => u.userId !== userId));
            alert("User deleted successfully");   
        } catch (error) {
            console.error(error);
            alert("Delete failed");
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>All Users</h2>

            <table border="1" width="100%" cellPadding="10">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>
                    {users.map((u, index) => (
                        <tr key={u.userId}>
                            <td>{index + 1}</td>
                            <td>{u.userName}</td>
                            <td>{u.email}</td>
                            <td>{u.phone}</td>
                            <td>{u.role}</td>
                            <td>
                                {u.address?.length
                                    ? u.address.map((a, i) => (
                                        <div key={i}>
                                            {a.houseNumber}, {a.street}, {a.area}, {a.city}, {a.state} - {a.pincode}
                                        </div>
                                    ))
                                    : "N/A"}
                            </td>

                            <td>
                                <div style={{ display: "flex", gap: "15px", justifyContent: "center" }}>

                                    {/* DELETE ICON */}
                                    <i
                                        className="fa-solid fa-trash"
                                        style={{ color: "red", cursor: "pointer", fontSize: "18px" }}
                                        title="Delete User"
                                        onClick={() => handleDeleteUser(u.userId)}
                                    ></i>

                                    {/* UPDATE ICON */}
                                    <i
                                        className="fa-solid fa-pen-to-square"
                                        style={{ color: "green", cursor: "pointer", fontSize: "18px" }}
                                        title="Update User"
                                        onClick={() => navigate(`/admin/update-user/${u.userId}`)}
                                    ></i>

                                </div>
                            </td>

                        </tr>
                    ))}
                </tbody>
            </table>

            
        </div>
    );
}

export default ManageUsers;
