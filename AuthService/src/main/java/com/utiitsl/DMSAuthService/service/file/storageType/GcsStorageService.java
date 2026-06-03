package com.utiitsl.DMSAuthService.service.file.storageType;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Profile("uat")
public class GcsStorageService implements StorageService {


    @Override
    public void saveChunk(String path, byte[] data) {

    }

    @Override
    public void createFile(String path) {

    }

    @Override
    public void appendToFile(String path, byte[] data) {

    }

    @Override
    public void delete(String path) {

    }

    @Override
    public byte[] read(String path) {
        return new byte[0];
    }
}
