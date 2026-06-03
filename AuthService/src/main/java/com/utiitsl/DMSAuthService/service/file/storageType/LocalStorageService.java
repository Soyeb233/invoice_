package com.utiitsl.DMSAuthService.service.file.storageType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Repository
public class LocalStorageService implements StorageService{

    @Value("${file.upload.storage}")
    private String uploadDir;

    @Override
    public void saveChunk(String path, byte[] data) {

        try {

            File file = new File(uploadDir + "/" + path);
            file.getParentFile().mkdirs();

            Files.write(file.toPath(), data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createFile(String path) {

        try {

            File file = new File(uploadDir + "/" + path);
            file.getParentFile().mkdirs();

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void appendToFile(String path, byte[] data) {

        try {

            File file = new File(uploadDir + "/" + path);

            try (FileOutputStream fos =
                         new FileOutputStream(file, true)) {
                fos.write(data);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] read(String path) {

        try {
            File file = new File(uploadDir + "/" + path);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String path) {

        File file = new File(uploadDir + "/" + path);
        file.delete();
    }

}
