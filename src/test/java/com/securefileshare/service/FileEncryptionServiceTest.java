package com.securefileshare.service;

import com.securefileshare.model.File;
import com.securefileshare.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class FileEncryptionServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileEncryptionService fileService;

    private File file;

    @BeforeEach
    public void setUp() {
        file = new File("testfile.txt", "text/plain", 1024L, true);
    }

    @Test
    public void testSaveFile() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("testfile.txt");
        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(multipartFile.getSize()).thenReturn(1024L);

        when(fileRepository.save(any(File.class))).thenReturn(file);

        File savedFile = fileService.saveFile(multipartFile);

        assertNotNull(savedFile);
        assertEquals("testfile.txt", savedFile.getFileName());
        assertTrue(savedFile.isEncrypted());
    }

    @Test
    public void testGetFileByName() {
        when(fileRepository.findByFileName("testfile.txt")).thenReturn(Optional.of(file));

        Optional<File> foundFile = fileService.getFileByName("testfile.txt");

        assertTrue(foundFile.isPresent());
        assertEquals("testfile.txt", foundFile.get().getFileName());
    }

    @Test
    public void testUpdateEncryptionStatus() {
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        when(fileRepository.save(any(File.class))).thenReturn(file);

        File updatedFile = fileService.updateEncryptionStatus(1L, false);

        assertNotNull(updatedFile);
        assertFalse(updatedFile.isEncrypted());
    }

    @Test
    public void testDeleteFile() {
        when(fileRepository.existsById(1L)).thenReturn(true);

        fileService.deleteFile(1L);

        verify(fileRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteFileNotFound() {
        when(fileRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> fileService.deleteFile(1L));
    }
}

