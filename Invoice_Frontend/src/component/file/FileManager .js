import { useEffect, useState } from "react"; 
import FileList from "./FileList"; 
import API from "../../api/axios";
import UploadChunk from "./UploadChunk";
import "../../style/FileManager.css";
const FileManager = () => {

  const [files, setFiles] = useState([]);

  const loadFiles = async () => {
    try {
      const res = await API.get("/api/v1/files");
      setFiles(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    loadFiles();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>📁 My Files</h2>

      {/* <UploadFile onUpload={loadFiles} /> */}

        {/* Upload Component */}
      <UploadChunk onUpload={loadFiles}/>

      <FileList files={files} onRefresh={loadFiles} />


    </div>
  );
};

export default FileManager;