package com.utiitsl.DMSAuthService.controller.file;

import com.utiitsl.DMSAuthService.dto.MergeRequest;
import com.utiitsl.DMSAuthService.entity.file.FileEntity;
import com.utiitsl.DMSAuthService.repository.FileRepository;
import com.utiitsl.DMSAuthService.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // ---------------- UPLOAD ----------------
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String id = fileService.uploadFile(file);
        return ResponseEntity.ok(id);
    }

    // ---------------- LIST ----------------
    @GetMapping
    public ResponseEntity<List<FileEntity>> getFiles() {
        return ResponseEntity.ok(fileService.getUserFiles());
    }

    // ---------------- DOWNLOAD ----------------
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) throws Exception {

        FileEntity file = fileService.getFile(id);

        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(file.getFilePath()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    private final FileRepository fileRepository;
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> fetchFile(
            @PathVariable String id) throws IOException {

        FileEntity fileEntity =
                fileRepository.findById(id)
                        .orElseThrow();

        Path path =
                Paths.get(fileEntity.getFilePath());

        Resource resource =
                new UrlResource(path.toUri());

        String contentType =
                Files.probeContentType(path);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .body(resource);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping("/upload-chunk")
    public ResponseEntity<?> uploadChunk(
            @RequestParam MultipartFile file,
            @RequestParam int chunkIndex,
            @RequestParam int totalChunks,
            @RequestParam String fileId
    ) {
        fileService.uploadChunk(file, chunkIndex, totalChunks, fileId);
        return ResponseEntity.ok("Chunk uploaded");
    }

//    @PostMapping("/merge")
//    public ResponseEntity<?> merge(@RequestParam String fileName) throws IOException {
//
//        String fileId="101";
//        fileService.mergeiles(fileId,fileName);
//        return ResponseEntity.ok("File merged successfully");
//    }


    @PostMapping("/merge")
    public ResponseEntity<?> merge(@RequestBody MergeRequest req) throws IOException {


        fileService.mergeFiles(req.getFileId(),req.getFileName());
        return ResponseEntity.ok("File merged successfully");
    }
}