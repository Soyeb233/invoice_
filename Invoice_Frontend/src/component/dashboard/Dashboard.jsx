import React, { useEffect, useState } from "react";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
  BarChart,
  Bar,
} from "recharts";

import "../../style/Dashboard.css";
import axios from "axios";

const lineData = [
  { month: "Jan", users: 30 },
  { month: "Feb", users: 45 },
  { month: "Mar", users: 60 },
  { month: "Apr", users: 40 },
  { month: "May", users: 90 },
];

const barData = [
  { month: "Jan", sales: 40 },
  { month: "Feb", sales: 35 },
  { month: "Mar", sales: 70 },
  { month: "Apr", sales: 55 },
  { month: "May", sales: 80 },
];

 

export default function Dashboard() {

const [totalUsers,setTotalUsers]=useState(0);
const[totalSales,setTotalSales] =useState(0);
const[orders,setOrders]=useState(0);
const[totalRevenue,setTotalRevenue]=useState(0);

 

useEffect(()=>{
     
     
    setTotalUsers(totalUsers);
    setTotalSales(totalSales);
    setOrders(orders);
    setTotalRevenue(totalRevenue);
     
},[totalUsers,totalSales,orders,totalRevenue])


const totalUsersHandler=()=>{
  alert("totalUsersHandler clicked");
  axios.get("/api/v1/dashboard/total-users")
  .then(response=>{
     setTotalUsers(response.data.totalUsers);
  })
  .catch(error => {
      console.error(error);
    });
}
const totalSalesHandler=()=>{
  alert("totalSalesHandler clicked");
}

const orderHandler=()=>{
  alert("Orders clicked");
}

const totalRevenueHandler=()=>{
  alert("totalRevenueHandler clicked");
}



  return (
    <div className="dashboard-page">
      {/* Cards */}
      <div className="cards-grid">
        <div className="card"
         onClick={totalUsersHandler}
         style={{ cursor: "pointer" }}>
          <h3>Total Users</h3>
          <h1>245</h1>
        </div>

        <div className="card"
         onClick={totalSalesHandler}
          style={{ cursor: "pointer" }}>
          <h3>Total Sales</h3>
          <h1>₹52,000</h1>
        </div>

        <div className="card"
         onClick={orderHandler}
         style={{ cursor: "pointer" }}>
          <h3>Orders</h3>
          <h1>320</h1>
        </div>

        <div className="card"
         onClick={totalRevenueHandler}
          style={{ cursor: "pointer" }}>
          <h3>Revenue</h3>
          <h1>₹1,20,000</h1>
        </div>
      </div>

      {/* Charts */}
      <div className="charts-grid">
        <div className="chart-card">
          <h3>User Growth</h3>

          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={lineData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Line
                type="monotone"
                dataKey="users"
                stroke="#5b5bf7"
                strokeWidth={3}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>

        <div className="chart-card">
          <h3>Sales Analytics</h3>

          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={barData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="sales" fill="#19c37d" radius={[5, 5, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Table */}
       <div className="table-card">
        <h3>Users List</h3>

        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
              </tr>
            </thead>

            <tbody>
              {totalUsers.length > 0 ? (
                totalUsers.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                    <td>{user.role}</td>
                  </tr>
                ))
              ) : (
                <tr>
                   <td colSpan="4" style={{ textAlign: "center", verticalAlign: "middle" }}>
                     No users found
                     </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}