import { Outlet } from "react-router-dom";

const AdminLayout = () => {
  return (
    <>
      <h2>Admin Dashboard</h2>
      <Outlet />
    </>
  );
};

export default AdminLayout;
