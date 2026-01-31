import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import {
  Box,
  Button,
  TextField,
  Typography,
  Paper,
  Link,
} from "@mui/material";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    const response = await axios.post(
      "http://localhost:9059/api/auth/login",
      { email, password }
    );

    console.log("LOGIN RESPONSE:", response.data);

   const { token, role, userName, providerId } = response.data;

    // Store login details
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    localStorage.setItem("userName", userName);
    console.log("ROLE TYPE:", typeof role, role);

    alert("Login Successful");

    // Role-based navigation
    if (role === "ADMIN") {
      navigate("/admin/dashboard");
    } 
   else if (role === "SERVICE_PROVIDER") {
  localStorage.setItem("providerId", providerId);
  navigate("/service-provider/dashboard");
}
    else {
      navigate("/home");
    }

  } catch (error) {
    console.error("Login failed:", error.response?.data || error.message);
    alert(error.response?.data?.message || "Invalid credentials");
  }
};


  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "linear-gradient(135deg, #1976d2, #42a5f5)",
      }}
    >
      <Paper
        elevation={6}
        sx={{
          padding: 4,
          width: 350,
          borderRadius: 2,
        }}
      >
        <Typography variant="h5" align="center" gutterBottom>
          Login
        </Typography>

        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Email"
            type="email"
            margin="normal"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <TextField
            fullWidth
            label="Password"
            type="password"
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <Button
            fullWidth
            type="submit"
            variant="contained"
            sx={{ marginTop: 2, padding: 1.2 }}
          >
            Login
          </Button>

          {/* Register Link */}
          <Typography
            variant="body2"
            align="center"
            sx={{ marginTop: 2 }}
          >
            Don’t have an account?{" "}
            <Link
              component="button"
              underline="hover"
              onClick={() => navigate("/Register")}
              sx={{ fontWeight: "bold" }}
            >
              Register now
            </Link>
          </Typography>
        </Box>
      </Paper>
    </Box>
  );
}

export default Login;
