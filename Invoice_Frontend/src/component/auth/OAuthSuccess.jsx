import React, { useEffect } from "react";

import {
  useNavigate,
  useSearchParams,
} from "react-router-dom";

export default function OAuthSuccess() {

  const navigate = useNavigate();

  const [searchParams] =
    useSearchParams();

  useEffect(() => {

    // Get token from URL
    const token =
      searchParams.get("token");

    if (token) {

      // Save JWT
      localStorage.setItem(
        "token",
        token
      );

      // Redirect dashboard
      navigate("/dashboard");

    } else {

      navigate("/login");

    }

  }, [navigate,searchParams]);

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        fontSize: "24px",
      }}
    >
      Logging you in...
    </div>
  );
}