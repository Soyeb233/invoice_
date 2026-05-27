// DashboardLayout.jsx

import React, { useState } from "react";

import { Outlet } from "react-router-dom";

 

import "../../style/DashboardLayout.css";
import Sidebar from "../Sidebar";
import Header from "../Header";

export default function DashboardLayout() {
  const [sidebarOpen, setSidebarOpen] =
    useState(true);

  return (
    <div className="layout-wrapper">
      {/* HEADER */}
      <Header
        sidebarOpen={sidebarOpen}
        setSidebarOpen={setSidebarOpen}
      />

      {/* BODY */}
      <div className="dashboard-body">
        {/* SIDEBAR */}
        <Sidebar sidebarOpen={sidebarOpen} />

        {/* MAIN CONTENT */}
        <main
          className={`main-content ${
            sidebarOpen
              ? "expanded"
              : "collapsed"
          }`}
        >
          <div className="page-content">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
}