package com.utiitsl.DMSAuthService.service.file;

import com.utiitsl.DMSAuthService.common.exceptionHandler.UserDefinedException;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.entity.file.FileEntity;
import com.utiitsl.DMSAuthService.repository.FileRepository;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImple implements FileService {

    @Value("${file.upload.storage}")
    private String uploadDir;

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username).get();
    }

    // ---------------- UPLOAD ----------------
    @Transactional
    public String uploadFile(MultipartFile file) {

        try {
            User user = getCurrentUser();

            String id = UUID.randomUUID().toString();

            String userFolder = uploadDir + "/" + user.getId();
            File dir = new File(userFolder);
            if (!dir.exists()) dir.mkdirs();

            String filePath = userFolder + "/" + id + "_" + file.getOriginalFilename();

            file.transferTo(new File(filePath));

            FileEntity entity = new FileEntity();
            entity.setId(id);
            entity.setFileName(file.getOriginalFilename());
            entity.setFilePath(filePath);
            entity.setFileType(file.getContentType());
            entity.setSize(file.getSize());
            entity.setCreatedAt(new Date());
            entity.setUser(user);

            fileRepository.save(entity);
            return id;
        }
        catch (IOException ex){
            throw new UserDefinedException("File Not Uploaded", HttpStatus.BAD_REQUEST);
        }

    }

    // ---------------- LIST ----------------
    public List<FileEntity> getUserFiles() {
        User user = getCurrentUser();
        return fileRepository.findByUser(user);
    }

    // ---------------- FETCH ----------------
    public FileEntity getFile(String id) {
        User user = getCurrentUser();

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return file;
    }

    // ---------------- DELETE ----------------
    public void deleteFile(String id) {

        User user = getCurrentUser();

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        new File(file.getFilePath()).delete();
        fileRepository.delete(file);
    }

    @Override
    public void uploadChunk(MultipartFile file,
                            int chunkIndex,
                            int totalChunks,
                            String fileId) {

        try {

            User user = getCurrentUser();

            String tempPath =
                    uploadDir + "/" + user.getId() + "/temp/" + fileId;

            File dir = new File(tempPath);
            if (!dir.exists()) dir.mkdirs();

            File chunkFile =
                    new File(tempPath + "/" + chunkIndex);

            file.transferTo(chunkFile);

        } catch (IOException ex) {
            throw new RuntimeException("Chunk upload failed");
        }
    }

    @Override
    @Transactional
    public String mergeFiles(String fileId, String fileName) {

        try {

            User user = getCurrentUser();

            String tempPath =
                    uploadDir + "/" + user.getId() + "/temp/" + fileId;

            String finalPathDir =
                    uploadDir + "/" + user.getId();

            File dir = new File(tempPath);

            if (!dir.exists()) {
                throw new RuntimeException("Temp folder not found");
            }

            File output = new File(finalPathDir,
                    fileId + "_" + fileName);

            if (!output.getParentFile().exists()) {
                output.getParentFile().mkdirs();
            }

            File[] chunks = dir.listFiles();

            if (chunks == null || chunks.length == 0) {
                throw new RuntimeException("No chunks found");
            }

            Arrays.sort(chunks,
                    Comparator.comparingInt(f -> Integer.parseInt(f.getName()))
            );

            try (FileOutputStream fos = new FileOutputStream(output, true)) {

                for (File chunk : chunks) {
                    Files.copy(chunk.toPath(), fos);
                }
            }

            // cleanup temp
            for (File chunk : chunks) {
                chunk.delete();
            }
            dir.delete();

            // ================= DB SAVE =================

            FileEntity entity = new FileEntity();
            entity.setId(fileId);
            entity.setFileName(fileName);
            entity.setFilePath(output.getAbsolutePath());
            entity.setFileType(Files.probeContentType(output.toPath()));
            entity.setSize(output.length());
            entity.setUser(user);
            entity.setCreatedAt(new Date());

            fileRepository.save(entity);

            return fileId;

        } catch (IOException ex) {
            throw new RuntimeException("Merge failed");
        }
    }

}
