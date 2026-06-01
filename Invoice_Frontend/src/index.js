import React from "react";

import ReactDOM from "react-dom/client";

import App from "./App";

import {
  BrowserRouter,
} from "react-router-dom";

import {
  GoogleOAuthProvider,
} from "@react-oauth/google";

const root =
  ReactDOM.createRoot(
    document.getElementById(
      "root"
    )
  );

root.render(
  <BrowserRouter basename="/invoice">
    <GoogleOAuthProvider clientId="600473531643-oi3n07dnckl59ckqltksq55ggm0hpjm1.apps.googleusercontent.com">
      <App />
    </GoogleOAuthProvider>
  </BrowserRouter>
);