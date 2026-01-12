import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import {
  Box,
  Button,
  TextField,
  Typography,
  Paper,
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
        {
          email: email,
          password: password,
        }
      );

      console.log(response.data);
      localStorage.setItem("token" , response.data.token);

      alert("Login Successful");

      // 🔴 IMPORTANT: store token (VERY NEEDED)
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
      }

      navigate("/AdminDashboard");
    } catch (error) {
      console.error(error);
      alert("Invalid email or password");
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
          Admin Login
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
        </Box>
      </Paper>
    </Box>
  );
}

export default Login;
