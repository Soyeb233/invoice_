import React from "react";

import {
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import Login from "../component/auth/Login";
import Signup from "../component/auth/Signup";

import Dashboard from "../component/dashboard/Dashboard";

import FacebookCallback from "../component/auth/FacebookCallback";
import OAuthSuccess from "../component/auth/OAuthSuccess";

import UsersList from "../component/dashList/UsersList";
import SalesList from "../component/dashList/SalesList";
import OrdersList from "../component/dashList/OrdersList";
import RevenueList from "../component/dashList/RevenueList";

import DashboardLayout from "../component/dashboard/DashboardLayout";

import ProtectedRoute from "./ProtectedRoute";

export default function Routing() {

  return (
    <Routes>

      {/* Public Routes */}
      <Route path="/" element={<Navigate to="/login" />} />

      <Route path="/login" element={<Login />} />

      <Route path="/signup" element={<Signup />} />

      <Route
        path="/facebook-callback"
        element={<FacebookCallback />}
      />

      <Route
        path="/oauth-success"
        element={<OAuthSuccess />}
      />

      {/* Protected Layout */}
      <Route
        element={
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >

        <Route
          path="/dashboard"
          element={<Dashboard />}
        />

        <Route
          path="/users"
          element={<UsersList />}
        />

        <Route
          path="/sales"
          element={<SalesList />}
        />

        <Route
          path="/orders"
          element={<OrdersList />}
        />

        <Route
          path="/revenue"
          element={<RevenueList />}
        />

      </Route>

      {/* Fallback */}
      <Route
        path="*"
        element={<Navigate to="/login" />}
      />

    </Routes>
  );
}