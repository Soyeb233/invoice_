import React from "react";



const styles = {
  overlay: {
    position: "fixed",
    inset: 0,
    background: "rgba(0,0,0,0.5)"
  },

  drawer: {
    position: "absolute",
    right: 0,
    top: 0,
    height: "100%",
    width: "420px",
    background: "#fff",
    display: "flex",
    flexDirection: "column",
    boxShadow: "-10px 0 30px rgba(0,0,0,0.2)"
  },

  header: {
    padding: "16px",
    borderBottom: "1px solid #eee",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center"
  },

  body: {
    padding: "16px",
    flex: 1,
    overflowY: "auto"
  },

  section: {
    marginBottom: "20px"
  },

  input: {
    width: "100%",
    padding: "10px",
    marginTop: "8px",
    marginBottom: "10px",
    borderRadius: "8px",
    border: "1px solid #ddd",
    outline: "none"
  },

  footer: {
    padding: "16px",
    borderTop: "1px solid #eee",
    display: "flex",
    justifyContent: "flex-end",
    gap: "10px"
  },

  saveBtn: {
    background: "#4f46e5",
    color: "#fff",
    border: "none",
    padding: "10px 14px",
    borderRadius: "8px",
    cursor: "pointer"
  },

  cancelBtn: {
    background: "#e5e7eb",
    border: "none",
    padding: "10px 14px",
    borderRadius: "8px",
    cursor: "pointer"
  },

  closeBtn: {
    border: "none",
    background: "transparent",
    fontSize: "18px",
    cursor: "pointer"
  }
};

export default function EditUserDrawer({
  open,
  onClose,
  formData,
  onChange,
  onSave
}) {
  if (!open) return null;

  return (
    <div style={styles.overlay}>
      <div style={styles.drawer}>

        {/* Header */}
        <div style={styles.header}>
          <h2>✏️ Edit User</h2>
          <button onClick={onClose} style={styles.closeBtn}>✕</button>
        </div>

        {/* Form */}
        <div style={styles.body}>

          <div style={styles.section}>
            <h4>Personal Info</h4>

            <input
              name="firstName"
              value={formData.firstName}
              onChange={onChange}
              placeholder="First Name"
              style={styles.input}
            />

            <input
              name="lastName"
              value={formData.lastName}
              onChange={onChange}
              placeholder="Last Name"
              style={styles.input}
            />
          </div>

          <div style={styles.section}>
            <h4>Account</h4>

            <input
              name="email"
              value={formData.email}
              onChange={onChange}
              placeholder="Email"
              style={styles.input}
            />

            <select
              name="role"
              value={formData.role}
              onChange={onChange}
              style={styles.input}
            >
              <option value="ADMIN">Admin</option>
              <option value="USER">User</option>
            </select>
          </div>

          <div style={styles.section}>
            <h4>Change Password</h4>

            <input
              type="password"
              name="password"
              value={formData.password || ""}
              onChange={onChange}
              placeholder="New Password"
              style={styles.input}a
            />

            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword || ""}
              onChange={onChange}
              placeholder="Confirm Password"
              style={styles.input}
            />
          </div>

        </div>

        {/* Footer */}
        <div style={styles.footer}>
          <button onClick={onClose} style={styles.cancelBtn}>
            Cancel
          </button>

          <button onClick={onSave} style={styles.saveBtn}>
            Save Changes
          </button>
        </div>

      </div>
    </div>
  );
}