import React, { useState } from "react";
import "../../style/Signup.css";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);

  // FORM STATE
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    mobileNo: "",
    username: "",
    password: "",
    role: "USER",
    termsAccepted: false,
  });

  const [loading, setLoading] = useState(false);

  // HANDLE INPUT CHANGE
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // HANDLE SUBMIT
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.termsAccepted) {
      alert("Please accept Terms & Conditions");
      return;
    }

    try {
      setLoading(true);

      const payload = {
        email: formData.email,
        firstName: formData.firstName,
        lastName: formData.lastName,
        mobileNo: formData.mobileNo,
        username: formData.username,
        password: formData.password,
        role: formData.role,
      };

      const response = await fetch(
        "http://localhost:8282/authService/api/v1/auth/create",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        }
      );

      const data = await response.json();

      if (response.ok) {
        alert("Account Created Successfully");

        console.log(data);

        // RESET FORM
        setFormData({
          firstName: "",
          lastName: "",
          email: "",
          mobileNo: "",
          username: "",
          password: "",
          role: "USER",
          termsAccepted: false,
        });

        navigate("/login");
      } else {
        alert(data.message || "Something went wrong");
      }
    } catch (error) {
      console.error(error);
      alert("Server Error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-card">

        {/* LEFT SECTION */}
        <div className="signup-left">
          <div className="overlay"></div>

          <div className="left-content">
            <h1>Create Account</h1>

            <p>
              Join our platform and start your amazing journey
              with a modern and secure experience.
            </p>

            <div className="features">
              <div className="feature">
                <span>✓</span> Secure Authentication
              </div>

              <div className="feature">
                <span>✓</span> Fast & Responsive
              </div>

              <div className="feature">
                <span>✓</span> Beautiful UI Experience
              </div>
            </div>
          </div>
        </div>

        {/* RIGHT SECTION */}
        <div className="signup-right">
          <h2>Sign Up</h2>

          <form onSubmit={handleSubmit}>

            {/* NAME ROW */}
            <div className="row">

              <div className="input-group">
                <label>First Name</label>

                <input
                  type="text"
                  name="firstName"
                  placeholder="John"
                  value={formData.firstName}
                  onChange={handleChange}
                />
              </div>

              <div className="input-group">
                <label>Last Name</label>

                <input
                  type="text"
                  name="lastName"
                  placeholder="Doe"
                  value={formData.lastName}
                  onChange={handleChange}
                />
              </div>

            </div>

            {/* USERNAME */}
            <div className="input-group">
              <label>Username</label>

              <input
                type="text"
                name="username"
                placeholder="john_doe"
                value={formData.username}
                onChange={handleChange}
              />
            </div>

            {/* EMAIL */}
            <div className="input-group">
              <label>Email Address</label>

              <input
                type="email"
                name="email"
                placeholder="john@example.com"
                value={formData.email}
                onChange={handleChange}
              />
            </div>

            {/* MOBILE */}
            <div className="input-group">
              <label>Mobile Number</label>

              <input
                type="text"
                name="mobileNo"
                placeholder="+91 9876543210"
                value={formData.mobileNo}
                onChange={handleChange}
              />
            </div>

            {/* PASSWORD */}
            <div className="input-group">
              <label>Password</label>

              <div className="password-box">

                <input
                  type={showPassword ? "text" : "password"}
                  name="password"
                  placeholder="Create password"
                  value={formData.password}
                  onChange={handleChange}
                />

                <span
                  className="toggle-btn"
                  onClick={() =>
                    setShowPassword(!showPassword)
                  }
                >
                  {showPassword ? "Hide" : "Show"}
                </span>

              </div>
            </div>

            {/* ROLE */}
            <div className="input-group">
              <label>Role</label>

              <select
                name="role"
                value={formData.role}
                onChange={handleChange}
              >
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </div>

            {/* TERMS */}
            <div className="terms">

              <input
                type="checkbox"
                name="termsAccepted"
                checked={formData.termsAccepted}
                onChange={handleChange}
              />

              <p>
                I agree to the
                <span> Terms & Conditions</span>
              </p>

            </div>

            {/* BUTTON */}
            <button
              type="submit"
              className="signup-btn"
              disabled={loading}
            >
              {loading ? "Creating..." : "Create Account"}
            </button>

            {/* LOGIN */}
            <p className="signin-text">
              Already have an account?

              <span onClick={() => navigate("/login")}>
                Login
              </span>
            </p>

          </form>
        </div>
      </div>
    </div>
  );
}