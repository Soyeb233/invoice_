import React, { useState } from "react";

import Sidebar from "../Sidebar";
export default function UsersList() {


   // Dummy 50 records
      const generateData = Array.from({ length: 50 }, (_, i) => ({
        id: i + 1,
        name: `User ${i + 1}`,
        email: `user${i + 1}@mail.com`,
        role: i % 2 === 0 ? "Admin" : "User",
      }));
  
      const [page, setPage] = useState(1);
      const perPage = 5;
  
      const totalPages = Math.ceil(generateData.length / perPage);
  
      const start = (page - 1) * perPage;
      const end = start + perPage;
  
      const currentData = generateData.slice(start, end);
  
      // Pagination
      const nextPage = () => {
        if (page < totalPages) setPage(page + 1);
      };
  
      const prevPage = () => {
        if (page > 1) setPage(page - 1);
      };
  
      // Actions
      const handleView = (item) => {
        alert(`Viewing: ${item.name}`);
      };
  
      const handleEdit = (item) => {
        alert(`Editing: ${item.name}`);
      };
  
      const handleDelete = (id) => {
        alert(`Deleting ID: ${id}`);
      };
  
  
      const totalUserHandler=()=>{
        
      }

  const users = Array.from({ length: 20 }, (_, i) => ({
    id: i + 1,
    name: `User ${i + 1}`,
    email: `user${i + 1}@mail.com`,
  }));

  return (
    <div>
      <h2>Users List</h2>

      {/* TABLE SECTION */}
          <div className="table-container">
            <h3>Users List</h3>

            <table className="data-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Action</th>
                </tr>
              </thead>

              <tbody>
                {currentData.map((item) => (
                  <tr key={item.id}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.email}</td>
                    <td>{item.role}</td>
                    <td className="action-buttons">
                      <button onClick={() => handleView(item)}>View</button>
                      <button onClick={() => handleEdit(item)}>Edit</button>
                      <button onClick={() => handleDelete(item.id)}>Delete</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {/* Pagination */}
            <div className="pagination">
              <button onClick={prevPage} disabled={page === 1}>
                Prev
              </button>

              <span>
                Page {page} of {totalPages}
              </span>

              <button onClick={nextPage} disabled={page === totalPages}>
                Next
              </button>
            </div>
          </div>
    </div>
  );
}