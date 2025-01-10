package com.securefileshare.controller;

import com.securefileshare.model.File;
import com.securefileshare.service.FileEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileEncryptionService fileService;

    // Endpoint for uploading a file
    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File savedFile = fileService.saveFile(file);
        return ResponseEntity.ok(savedFile);
    }

    // Endpoint to get a file by name
    @GetMapping("/{fileName}")
    public ResponseEntity<File> getFileByName(@PathVariable String fileName) {
        Optional<File> file = fileService.getFileByName(fileName);
        return file.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all encrypted files
    @GetMapping("/encrypted")
    public ResponseEntity<Iterable<File>> getEncryptedFiles() {
        Iterable<File> encryptedFiles = fileService.getEncryptedFiles();
        return ResponseEntity.ok(encryptedFiles);
    }

    // Endpoint to update the encryption status of a file
    @PutMapping("/{fileId}/encrypt")
    public ResponseEntity<File> updateEncryptionStatus(@PathVariable Long fileId, @RequestParam boolean encrypted) {
        try {
            File updatedFile = fileService.updateEncryptionStatus(fileId, encrypted);
            return ResponseEntity.ok(updatedFile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a file by its ID
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

