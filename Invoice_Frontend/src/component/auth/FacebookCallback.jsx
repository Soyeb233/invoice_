import React, { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import API from "../../api/axios";

export default function FacebookCallback() {
  const [params] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const code = params.get("code");

    if (code) {
      loginWithFacebook(code);
    }
  }, []);

  const loginWithFacebook = async (code) => {
    try {
      const res = await API.post("/auth/facebook", {
        code,
      });

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("user", JSON.stringify(res.data.user));

      navigate("/dashboard");
    } catch (err) {
      console.log(err);
      alert("Facebook login failed");
    }
  };

  return <h2>Logging in with Facebook...</h2>;
}