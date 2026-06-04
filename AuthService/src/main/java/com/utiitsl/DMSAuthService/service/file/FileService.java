package com.utiitsl.DMSAuthService.service.file;

import com.utiitsl.DMSAuthService.entity.file.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService  {

    String uploadFile(MultipartFile file);
    List<FileEntity> getUserFiles();
    FileEntity getFile(String id);
    void deleteFile(String id);

    void uploadChunk(MultipartFile file,int chunkIndex,int totalChunks,String fileName);
    String mergeFiles(String fileId,String fileName);

    Long findTotalFileUploadCount();
}
