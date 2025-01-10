package com.securefileshare.controller;

import com.securefileshare.model.File;
import com.securefileshare.service.FileEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FileEncryptionService fileService;

    @InjectMocks
    private FileController fileController;

    private File file;

    @BeforeEach
    public void setUp() {
        file = new File("testfile.txt", "text/plain", 1024L, true);
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.txt", "text/plain", "test content".getBytes());

        when(fileService.saveFile(any())).thenReturn(file);

        mockMvc.perform(multipart("/files/upload")
                        .file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("testfile.txt"))
                .andExpect(jsonPath("$.fileType").value("text/plain"));
    }

    @Test
    public void testGetFileByName() throws Exception {
        when(fileService.getFileByName("testfile.txt")).thenReturn(java.util.Optional.of(file));

        mockMvc.perform(get("/files/testfile.txt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("testfile.txt"))
                .andExpect(jsonPath("$.fileType").value("text/plain"));
    }

    @Test
    public void testGetEncryptedFiles() throws Exception {
        when(fileService.getEncryptedFiles()).thenReturn(java.util.Arrays.asList(file));

        mockMvc.perform(get("/files/encrypted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileName").value("testfile.txt"))
                .andExpect(jsonPath("$[0].fileType").value("text/plain"));
    }

    @Test
    public void testUpdateEncryptionStatus() throws Exception {
        when(fileService.updateEncryptionStatus(1L, false)).thenReturn(file);

        mockMvc.perform(put("/files/1/encrypt?encrypted=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.encrypted").value(false));
    }

    @Test
    public void testDeleteFile() throws Exception {
        mockMvc.perform(delete("/files/1"))
                .andExpect(status().isNoContent());

        verify(fileService, times(1)).deleteFile(1L);
    }
}

