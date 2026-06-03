import { useState } from "react";
import API from "../../api/axios";
import "../../style/UploadChunk.css";

const CHUNK_SIZE = 5 * 1024 * 1024; // 5MB

const UploadChunk = ({ onUpload }) => {

  const [files, setFiles] = useState([]);
  const [uploading, setUploading] = useState(false);
  const [progress, setProgress] = useState(0);

  const uploadFile = async () => {

    if (files.length === 0) {
      alert("Please select file(s)");
      return;
    }

    setUploading(true);
    setProgress(0);

    try {

      let completedFiles = 0;

      for (const file of files) {

        const fileId = crypto.randomUUID();

        const totalChunks = Math.ceil(
          file.size / CHUNK_SIZE
        );

        console.log(
          `Uploading ${file.name} (${totalChunks} chunks)`
        );

        // Upload all chunks
        for (let i = 0; i < totalChunks; i++) {

          const chunk = file.slice(
            i * CHUNK_SIZE,
            (i + 1) * CHUNK_SIZE
          );

          const formData = new FormData();

          formData.append("file", chunk);
          formData.append("chunkIndex", i);
          formData.append("totalChunks", totalChunks);
          formData.append("fileId", fileId);
          formData.append("fileName", file.name);

          await API.post(
            "/api/v1/files/upload-chunk",
            formData
          );
        }

        // Merge uploaded chunks
        await API.post(
          "/api/v1/files/merge",
          {
            fileId,
            fileName: file.name
          }
        );

        completedFiles++;

        setProgress(
          Math.round(
            (completedFiles / files.length) * 100
          )
        );
      }

      alert("All files uploaded successfully");

      setFiles([]);

      if (onUpload) {
        onUpload();
      }

    } catch (err) {
      console.error(err);
      alert("Upload failed");
    }

    setUploading(false);
  };

  return (
    <div className="upload-container">

      <input
        type="file"
        multiple
        className="file-input"
        onChange={(e) =>
          setFiles(Array.from(e.target.files))
        }
      />

      {/* Selected Files */}
      {files.length > 0 && (
        <div className="selected-files">

          <h4>
            Selected Files ({files.length})
          </h4>

          {files.map((file, index) => (
            <div
              key={index}
              className="selected-file"
            >
              📄 {file.name}
              {" - "}
              {(file.size / 1024 / 1024).toFixed(2)}
              MB
            </div>
          ))}

        </div>
      )}

      <button
        className="upload-btn"
        onClick={uploadFile}
        disabled={uploading}
      >
        {uploading
          ? "Uploading..."
          : `Upload ${files.length || ""} File(s)`}
      </button>

      {/* Progress */}
      {uploading && (

        <div className="progress-wrapper">

          <div className="progress-text">
            {progress}% Uploaded
          </div>

          <div className="progress-bar">
            <div
              className="progress-fill"
              style={{
                width: `${progress}%`
              }}
            />
          </div>

        </div>

      )}

    </div>
  );
};

export default UploadChunk;