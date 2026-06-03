package com.utiitsl.DMSAuthService.service.file.storageType;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService  {

    void saveChunk(String path, byte[] data);

    void createFile(String path);

    void appendToFile(String path, byte[] data);

    void delete(String path);

    byte[] read(String path);

}
