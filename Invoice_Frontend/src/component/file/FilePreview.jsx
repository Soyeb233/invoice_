import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import API from "../../api/axios";

const FilePreview = () => {

  const { id } = useParams();
  const navigate = useNavigate();

  const [fileUrl, setFileUrl] = useState(null);
  const [contentType, setContentType] = useState("");
  const [fileName, setFileName] = useState("");

  useEffect(() => {

    loadFile();

    return () => {
      if (fileUrl) {
        URL.revokeObjectURL(fileUrl);
      }
    };

  }, []);

  const loadFile = async () => {

    try {

      const response = await API.get(
        `/api/v1/files/view/${id}`,
        {
          responseType: "blob"
        }
      );

      const blob = response.data;
      const url = URL.createObjectURL(blob);

      setFileUrl(url);

      setContentType(
        response.headers["content-type"] || ""
      );

      // Optional: if backend sends filename header
      const disposition =
        response.headers["content-disposition"];

      if (disposition) {
        const match =
          disposition.match(/filename="?(.+)"?/);

        if (match) {
          setFileName(match[1]);
        }
      }

    } catch (error) {
      console.error(error);
    }
  };

  if (!fileUrl) {
    return (
      <div style={{ padding: "30px" }}>
        Loading file...
      </div>
    );
  }

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#f5f7fb"
      }}
    >

      {/* Header */}
      <div
        style={{
          background: "#fff",
          padding: "15px 25px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
        }}
      >

        <div>
          <h2
            style={{
              margin: 0,
              color: "#1f2937"
            }}
          >
            File Preview
          </h2>

          {fileName && (
            <p
              style={{
                margin: "5px 0 0",
                color: "#6b7280"
              }}
            >
              {fileName}
            </p>
          )}
        </div>

        <button
          onClick={() => navigate(-1)}
          style={{
            background: "#2563eb",
            color: "#fff",
            border: "none",
            padding: "10px 18px",
            borderRadius: "8px",
            cursor: "pointer"
          }}
        >
          ← Back
        </button>

      </div>

      {/* Preview Content */}
      <div
        style={{
          padding: "20px"
        }}
      >

        {contentType.startsWith("image/") && (
          <div
            style={{
              textAlign: "center"
            }}
          >
            <img
              src={fileUrl}
              alt="Preview"
              style={{
                maxWidth: "100%",
                maxHeight: "85vh",
                borderRadius: "10px",
                boxShadow:
                  "0 4px 15px rgba(0,0,0,0.15)"
              }}
            />
          </div>
        )}

        {contentType === "application/pdf" && (
          <iframe
            src={fileUrl}
            width="100%"
            height="900px"
            title="PDF Preview"
            style={{
              border: "none",
              borderRadius: "10px"
            }}
          />
        )}

        {contentType.startsWith("text/") && (
          <iframe
            src={fileUrl}
            width="100%"
            height="900px"
            title="Text Preview"
            style={{
              border: "none",
              borderRadius: "10px"
            }}
          />
        )}

        {!contentType.startsWith("image/") &&
          contentType !== "application/pdf" &&
          !contentType.startsWith("text/") && (
            <div
              style={{
                background: "#fff",
                padding: "20px",
                borderRadius: "10px"
              }}
            >
              Preview not supported for this file type.
            </div>
          )}

      </div>

    </div>
  );
};

export default FilePreview;