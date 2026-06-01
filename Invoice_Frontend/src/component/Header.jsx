// Header.jsx

import React from "react";

import { FaBars } from "react-icons/fa";

import "../style/Header.css";

export default function Header({
  sidebarOpen,
  setSidebarOpen,
}) {

  const username=localStorage.getItem("user");
  return (
    <header className="top-header">
      <div className="header-left">
        <button
          className="menu-btn"
          onClick={() =>
            setSidebarOpen(!sidebarOpen)
          }
        >
          <FaBars />
        </button>

        <div>
          <h2>Invoice Dashboard</h2>
          <p>Welcome Back, {username} 👋</p>
        </div>
      </div>
    </header>
  );
}