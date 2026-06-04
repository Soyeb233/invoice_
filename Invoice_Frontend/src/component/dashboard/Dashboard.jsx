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
import UserList from "../user/UserList";
import API from "../../api/axios";

export default function Dashboard() {
  const [dashboardData, setDashboardData] = useState({
    totalUsers: 0,
    totalFileUpload: 0,
    orders: 0,
    totalRevenue: 0,
  });

  const [lineData, setLineData] = useState([]);
  const [barData, setBarData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      setLoading(true);

      const response = await API.get("/api/v1/dashboard/");

      const data = response.data.data;

      console.log("Dashboard Data:", data);

      setDashboardData({
        totalUsers: data.totalUsers || 0,
        totalFileUpload: data.totalFileUpload || 0,
        orders: data.orders || 0,
        totalRevenue: data.totalRevenue || 0,
      });

      setLineData(data.userGrowth || []);
      setBarData(data.salesAnalytics || []);
    } catch (error) {
      console.error("Dashboard API Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const totalUsersHandler = () => {
    alert(`Total Users : ${dashboardData.totalUsers}`);
  };

  const totalSalesHandler = () => {
    alert(`Total File Uploads : ${dashboardData.totalFileUpload}`);
  };

  const orderHandler = () => {
    alert(`Orders : ${dashboardData.orders}`);
  };

  const totalRevenueHandler = () => {
    alert(`Revenue : ₹${dashboardData.totalRevenue}`);
  };

  return (
    <div className="dashboard-page">
      {/* Cards */}
      <div className="cards-grid">
        <div
          className="card"
          onClick={totalUsersHandler}
          style={{ cursor: "pointer" }}
        >
          <h3>Active Users</h3>

          {loading ? (
            <div className="skeleton skeleton-number"></div>
          ) : (
            <h1>{dashboardData.totalUsers}</h1>
          )}
        </div>

        <div
          className="card"
          onClick={totalSalesHandler}
          style={{ cursor: "pointer" }}
        >
          <h3>File Uploads</h3>

          {loading ? (
            <div className="skeleton skeleton-number"></div>
          ) : (
            <h1>{dashboardData.totalFileUpload}</h1>
          )}
        </div>

        <div
          className="card"
          onClick={orderHandler}
          style={{ cursor: "pointer" }}
        >
          <h3>Orders</h3>

          {loading ? (
            <div className="skeleton skeleton-number"></div>
          ) : (
            <h1>{dashboardData.orders}</h1>
          )}
        </div>

        <div
          className="card"
          onClick={totalRevenueHandler}
          style={{ cursor: "pointer" }}
        >
          <h3>Revenue</h3>

          {loading ? (
            <div className="skeleton skeleton-number"></div>
          ) : (
            <h1>₹{dashboardData.totalRevenue}</h1>
          )}
        </div>
      </div>

      {/* Charts */}
      <div className="charts-grid">
        <div className="chart-card">
          <h3>User Growth</h3>

          {loading ? (
            <div className="chart-skeleton"></div>
          ) : (
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
          )}
        </div>

        <div className="chart-card">
          <h3>Sales Analytics</h3>

          {loading ? (
            <div className="chart-skeleton"></div>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={barData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Bar
                  dataKey="sales"
                  fill="#19c37d"
                  radius={[5, 5, 0, 0]}
                />
              </BarChart>
            </ResponsiveContainer>
          )}
        </div>
      </div>

      {/* User Table */}
      <div className="table-card">
        <UserList />
      </div>
    </div>
  );
}