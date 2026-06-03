import FileItem from "./FileItem";

 

const FileList = ({ files, onRefresh }) => {
  return (
    <div>

      {files.length === 0 && <p>No files uploaded</p>}

      {files.map((file) => (
        <FileItem
          key={file.id}
          file={file}
          onRefresh={onRefresh}
        />
      ))}

    </div>
  );
};

export default FileList;