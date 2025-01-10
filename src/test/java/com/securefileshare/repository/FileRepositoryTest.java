package com.securefileshare.repository;

import com.securefileshare.model.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    private File file;

    @BeforeEach
    public void setUp() {
        file = new File("testfile.txt", "text/plain", 1024L, true);
        fileRepository.save(file);
    }

    @Test
    public void testFindByFileName() {
        Optional<File> foundFile = fileRepository.findByFileName("testfile.txt");
        assertTrue(foundFile.isPresent());
        assertEquals("testfile.txt", foundFile.get().getFileName());
    }

    @Test
    public void testFindByEncrypted() {
        Iterable<File> encryptedFiles = fileRepository.findByEncrypted(true);
        assertTrue(encryptedFiles.iterator().hasNext());
    }

    @Test
    public void testFileNotFound() {
        Optional<File> foundFile = fileRepository.findByFileName("nonexistent.txt");
        assertFalse(foundFile.isPresent());
    }
}

