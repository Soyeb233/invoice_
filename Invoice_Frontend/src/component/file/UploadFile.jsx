import { useState } from "react"; 
import API from "../../api/axios";

const UploadFile = ({ onUpload }) => {

  const [file, setFile] = useState(null);

  const upload = async () => {
    if (!file) return alert("Select file");

    const formData = new FormData();
    formData.append("file", file);

    try {
      await API.post("/api/v1/files/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      });

      setFile(null);
      onUpload();
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div style={{ marginBottom: "20px" }}>

      <input type="file"
        onChange={(e) => setFile(e.target.files[0])}
      />

      <button onClick={upload}>
        Upload
      </button>

    </div>
  );
};

export default UploadFile;