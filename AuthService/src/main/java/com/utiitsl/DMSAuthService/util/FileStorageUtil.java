package com.utiitsl.DMSAuthService.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class FileStorageUtil {


    @Value("${file.upload.dir}")
    private String uploadDir;


    public String saveProfileImage(Long userId, MultipartFile image) throws IOException {

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image is empty");
        }

        Path uploadPath =
                Paths.get(uploadDir, "users", String.valueOf(userId));

        Files.createDirectories(uploadPath);

        String originalFileName =
                Optional.ofNullable(image.getOriginalFilename())
                        .orElse("profile.jpg");

        String extension = getExtension(originalFileName);

        validateImage(extension);

        String fileName = "profile." + extension;

        Path targetPath = uploadPath.resolve(fileName);

        Files.copy(
                image.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return "users/" + userId + "/" + fileName;
    }

    private String getExtension(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            throw new RuntimeException("Invalid file");
        }

        return fileName.substring(index + 1).toLowerCase();
    }

    private void validateImage(String extension) {

        List<String> allowedExtensions =
                List.of("jpg", "jpeg", "png", "webp");

        if (!allowedExtensions.contains(extension)) {
            throw new RuntimeException(
                    "Only JPG, JPEG, PNG and WEBP files are allowed"
            );
        }
    }
}
