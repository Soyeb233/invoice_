// Header.jsx

import React from "react"; 

import "../style/Header.css";

export default function HeaderLogin({
  sidebarOpen,
  setSidebarOpen,
}) {
  return (
    <header className="top-header">
      <div className="header-left">
         

        <div>
          <h2>Invoice</h2>
           
        </div>
      </div>
    </header>
  );
}