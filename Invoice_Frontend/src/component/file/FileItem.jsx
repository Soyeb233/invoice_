import { useNavigate } from "react-router-dom";
import API from "../../api/axios";

import "../../style/FileItem.css";

const FileItem = ({ file, onRefresh }) => {

  const navigate = useNavigate();  

  const download = async () => {
    try {
      const res = await API.get(`/api/v1/files/fetch/${file.id}`, {
        responseType: "blob",
      });

      const url = window.URL.createObjectURL(new Blob([res.data]));

      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", file.fileName);

      document.body.appendChild(link);
      link.click();
    } catch (err) {
      console.log(err);
    }
  };

  const remove = async () => {
    try {
      await API.delete(`/api/v1/files/${file.id}`);
      onRefresh();
    } catch (err) {
      console.log(err);
    }
  };


  const viewFile = () => {

  const fileName = file.fileName.toLowerCase();

  const viewableExtensions = [
    ".jpg",
    ".jpeg",
    ".png",
    ".gif",
    ".webp",
    ".pdf",
    ".txt"
  ];

  const canView = viewableExtensions.some(ext =>
    fileName.endsWith(ext)
  );

  if (!canView) {
    alert("Preview not supported");
    return;
  }

  navigate(`/files/preview/${file.id}`);
};

  return ( 

    <div className="file-item">

        <div className="file-info">
            <h4>{file.fileName}</h4>
            <p>
            {(file.size / 1024 / 1024).toFixed(2)} MB
            </p>
        </div>

        

        <div className="file-actions">

            <button
                 className="view-btn"
                 onClick={viewFile}
             >
                 View
            </button>

            <button
            className="download-btn"
            onClick={download}
            >
            Download
            </button>

            <button
            className="delete-btn"
            onClick={remove}
            >
            Delete
            </button>
        </div>

     </div>
  );
};

export default FileItem;