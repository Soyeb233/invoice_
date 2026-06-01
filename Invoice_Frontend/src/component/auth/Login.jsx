import React, { useState } from "react";
import "../../style/Login.css";
import API from "../../api/axios";
import { useNavigate } from "react-router-dom"; 

export default function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // EMAIL LOGIN
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await API.post(
        "/api/v1/auth/token",
        formData
      );

      localStorage.setItem(
        "token",
        response.data.data.token
      );

      localStorage.setItem(
        "user",
        JSON.stringify(response.data.data.username)
      );

      navigate("/dashboard");
    } catch (error) {
      alert("Login Failed");
    }
  };

 


  const handleGoogleLogin=()=>{

     window.location.href =
    "http://localhost:8282/authService/oauth2/authorization/google";

  }

  // FACEBOOK LOGIN (Redirect method)
  const handleFacebookLogin = () => {
    const clientId = "YOUR_FACEBOOK_APP_ID";
    const redirectUri = "http://localhost:3000/facebook-callback";

    window.location.href =
      `https://www.facebook.com/v18.0/dialog/oauth` +
      `?client_id=${clientId}` +
      `&redirect_uri=${redirectUri}` +
      `&scope=email`;
  };



  return (
    <div className="login-container">
      <form className="login-card" onSubmit={handleLogin}>
        <h2>Login</h2>

        <input
          type="username"
          name="username"
          placeholder="Email/Username"
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password" 
          placeholder="Password"
          onChange={handleChange}
          required
        />

        <button type="submit">Login</button>

        {/* GOOGLE LOGIN */}
        {/* <div className="google-box">
          <GoogleLogin
            onSuccess={handleGoogleSuccess}
            onError={() => alert("Google Failed")}
          /> 
        </div> */}

        <button
          type="button"
          className="facebook-btn"
          onClick={handleGoogleLogin}
        >
          Continue with Google
        </button>

        {/* FACEBOOK LOGIN */}
        <button
          type="button"
          className="facebook-btn"
          onClick={handleFacebookLogin}
        >
          Continue with Facebook
        </button>

        <div className="signup-link">
          <p>
            Don't have an account?{" "}
            <span
              onClick={() => navigate("/signup")}
              style={{ color: "blue", cursor: "pointer" }}
            >
              Sign up
            </span>
          </p>
        </div>
      </form>
      
    </div>
  );
}