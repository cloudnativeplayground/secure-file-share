package com.securefileshare.service;

import com.securefileshare.model.File;
import com.securefileshare.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileEncryptionService {

    @Autowired
    private FileRepository fileRepository;

    // Method to save a file and its metadata
    public File saveFile(MultipartFile file) throws IOException {
        // Create a new File object to store metadata
        File fileMetadata = new File();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFileType(file.getContentType());
        fileMetadata.setFileSize(file.getSize());

        // Encrypt the file (you can replace this with actual encryption logic)
        fileMetadata.setEncrypted(true);

        // Save file metadata in the database
        return fileRepository.save(fileMetadata);
    }

    // Method to retrieve a file by its name
    public Optional<File> getFileByName(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    // Method to retrieve all encrypted files
    public Iterable<File> getEncryptedFiles() {
        return fileRepository.findByEncrypted(true);
    }

    // Method to update the encryption status of a file
    public File updateEncryptionStatus(Long fileId, boolean encrypted) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            file.setEncrypted(encrypted);
            return fileRepository.save(file);
        }
        throw new IllegalArgumentException("File not found");
    }

    // Method to delete a file by its ID
    public void deleteFile(Long fileId) {
        if (fileRepository.existsById(fileId)) {
            fileRepository.deleteById(fileId);
        } else {
            throw new IllegalArgumentException("File not found");
        }
    }
}

