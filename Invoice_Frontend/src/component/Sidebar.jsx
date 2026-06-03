// Sidebar.jsx

import React from "react";

import {
  NavLink,
  useNavigate,
} from "react-router-dom";

import {
  FaUsers,
  FaShoppingCart,
  FaChartLine,
  FaDollarSign,
  FaHome,
  FaSignOutAlt,
} from "react-icons/fa";

import "../style/Sidebar.css";

export default function Sidebar({ sidebarOpen }) {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <aside
      className={`sidebar ${
        sidebarOpen ? "open" : "closed"
      }`}
    >
      <nav className="sidebar-links">
        <NavLink to="/dashboard">
          <FaHome />
          {sidebarOpen && <span>Dashboard</span>}
        </NavLink>

        <NavLink to="/users">
          <FaUsers />
          {sidebarOpen && <span>Users</span>}
        </NavLink>

        <NavLink to="/sales">
          <FaChartLine />
          {sidebarOpen && <span>Sales</span>}
        </NavLink>

        <NavLink to="/orders">
          <FaShoppingCart />
          {sidebarOpen && <span>Orders</span>}
        </NavLink>

        <NavLink to="/revenue">
          <FaDollarSign />
          {sidebarOpen && <span>Revenue</span>}
        </NavLink>

         <NavLink to="/files">📁 Files</NavLink>
      </nav>

      <button
        className="logout-btn"
        onClick={logout}
      >
        <FaSignOutAlt />
        {sidebarOpen && <span>Logout</span>}
      </button>
    </aside>
  );
}