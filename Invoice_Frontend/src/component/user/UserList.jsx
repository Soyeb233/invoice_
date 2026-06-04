import React, { useEffect, useState } from 'react'
import API from '../../api/axios';
import EditUserDrawer from './EditUserDrawer';


export default function UserList() {

  const [userList, setUserList] = useState([]);

 
  
const [openEdit, setOpenEdit] = useState(false);
const [editUser, setEditUser] = useState(null);
const [formData, setFormData] = useState({});
const [message, setMessage] = useState("");

  const fetchUserList = () => {
    API.get("/api/v1/user/getAllUser")
      .then(response => {
        setUserList(response.data.data.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  useEffect(() => {
    fetchUserList();
  }, []);

  // Toggle user status
  const toggleUserStatus = (user, currentStatus) => {

  const status = !currentStatus;
   alert("STATUS :",status);
    API.post(`/api/v1/user/activateDeactivateUser/${user.username}/${status}`)
        .then(() => fetchUserList())
        .catch(err => console.error(err));
  };

  // Edit handler (you can open modal later)
const handleEdit = (user) => {
  setEditUser(user);
  setFormData(user);
  setOpenEdit(true);
};


const handleChange = (e) => {
  setFormData({
    ...formData,
    [e.target.name]: e.target.value
  });
};

const handleUpdate = () => {

  if (formData.password !== formData.confirmPassword) {
    setMessage({
      type: "error",
      text: "Passwords do not match ❌",
    });
    return;
  }

   

  API.put(`/api/v1/user/editUser/${editUser.id}`, formData)
    .then(() => {
      fetchUserList();

      setOpenEdit(false);     // close drawer
      setEditUser(null);      // clear selected user
 
      setMessage({
        type: "success",
        text: "User updated successfully ✅"
        });

      // auto hide message after 3 seconds
      setTimeout(() => {
        setMessage("");
      }, 3000);
    })
   .catch(err => {
    console.error(err);

    const errorMsg =
        err?.response?.data?.data ||
        err?.response?.data?.message ||
        "Update failed";

     
    setMessage({
        type: "error",
        text: errorMsg + " ❌"
    });

    setTimeout(() => {
        setMessage("");
    }, 3000);
    });
};

const styles = {
  toast: (type) => ({
    position: "fixed",
    top: "10px",
    right: "25px",
    background: type === "error" ? "#9c0000" : "#2e7d32",
    color: "#fff",
    padding: "12px 16px",
    borderRadius: "8px",
    boxShadow: "0 10px 25px rgba(0,0,0,0.3)",
    zIndex: 9999,
    animation: "fadeIn 0.2s ease-in-out"
  })
};


  return (
    <div>
      <h3>Users List</h3>

    {message && (
  <div style={styles.toast(message.type)}>
    {message.text}
  </div>
)}
       
       <div style={{margin: "10px"}}>
            <div className="table-wrapper">
             <table>
                <thead>
                    <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {userList.length > 0 ? (
                    userList.map((user) => (
                        <tr key={user.id}>
                        <td>{user.username}</td>
                        <td>{user.firstName}</td>
                        <td>{user.email}</td>
                        <td>{user.role}</td>

                        {/* STATUS */}
                        <td>
                            <span style={{
                            padding: "4px 10px",
                            borderRadius: "12px",
                            color: "white",
                            backgroundColor: user.activeStatus ? "green" : "red",
                            fontSize: "12px"
                            }}>
                            {user.activeStatus ? "Active" : "Inactive"}
                            </span>
                        </td>

                        {/* ACTIONS */}
                        <td style={{ display: "flex", gap: "10px" }}>

                            {/* EDIT */}
                            <button
                            onClick={() => handleEdit(user)}
                            style={{
                                background: "#1976d2",
                                color: "white",
                                border: "none",
                                padding: "6px 10px",
                                borderRadius: "6px",
                                cursor: "pointer"
                            }}
                            title="Edit User"
                            >
                            ✏️
                            </button>

                            {/* ACTIVATE / DEACTIVATE */}
                            <button
                            onClick={() => toggleUserStatus(user, user.activeStatus)}
                            style={{
                                background: user.active ? "#d32f2f" : "#2e7d32",
                                color: "white",
                                border: "none",
                                padding: "6px 10px",
                                borderRadius: "6px",
                                cursor: "pointer"
                            }}
                            title={user.activeStatus ? "Deactivate User" : "Activate User"}
                            >
                            {user.activeStatus ? "🚫" : "✅"}
                            </button>

                        </td>
                        </tr>
                    ))
                    ) : (
                    <tr>
                        <td colSpan="6" style={{ textAlign: "center" }}>
                        No users found
                        </td>
                    </tr>
                    )}
                </tbody>
                </table>
            </div>
                    
        <EditUserDrawer
  open={openEdit}
  user={editUser}
  formData={formData}
  onChange={handleChange}
  onSave={handleUpdate}
  onClose={() => setOpenEdit(false)}
/>
       </div>
      
    </div>
  );
}