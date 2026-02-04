import { Navigate } from "react-router-dom";

const PublicRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (token) {
    if (role === "ADMIN") {
      return <Navigate to="/admin/dashboard" replace />;
    }
    if (role === "SERVICE_PROVIDER") {
      return <Navigate to="/service-provider/dashboard" replace />;
    }
    return <Navigate to="/home" replace />;
  }

  return children;
};

export default PublicRoute;
