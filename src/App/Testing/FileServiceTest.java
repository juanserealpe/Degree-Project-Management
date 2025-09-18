package App.Testing;

import App.Exceptions.FileException;
import App.Services.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    private FileService fileService;
    private Path testFile;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUploadFile_Success(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("test.pdf");
        Files.write(testFile, "%PDF-1234".getBytes());

        String result = assertDoesNotThrow(() -> fileService.uploadFile(testFile.toString()));
        assertTrue(result.endsWith(".pdf"));
        assertTrue(result.matches("[a-f0-9-]+\\.pdf"));
    }

    @Test
    void testUploadFile_AlreadyUploaded(@TempDir Path tempDir) throws IOException, FileException {
        testFile = tempDir.resolve("test.pdf");
        Files.write(testFile, "%PDF-1234".getBytes());

        fileService.uploadFile(testFile.toString());
        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("File already uploaded", exception.getMessage());
    }

    @Test
    void testUploadFile_EmptyFile(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("empty.pdf");
        Files.createFile(testFile);

        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void testUploadFile_DangerousFilename(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("../evil.pdf");
        Files.write(testFile, "%PDF-1234".getBytes());

        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("Invalid filename", exception.getMessage());
    }

    @Test
    void testUploadFile_WrongExtension(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("test.txt");
        Files.write(testFile, "%PDF-1234".getBytes());

        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("File must be a PDF", exception.getMessage());
    }

    @Test
    void testUploadFile_ExceedsSize(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("large.pdf");
        byte[] largeContent = new byte[(int)(5.1 * 1024 * 1024)]; // 5.1 MB
        Files.write(testFile, largeContent);

        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("File exceeds maximum size", exception.getMessage());
    }

    @Test
    void testUploadFile_InvalidPDF(@TempDir Path tempDir) throws IOException {
        testFile = tempDir.resolve("fake.pdf");
        Files.write(testFile, "FAKE_CONTENT".getBytes());

        FileException exception = assertThrows(FileException.class,
                () -> fileService.uploadFile(testFile.toString()));
        assertEquals("Invalid PDF format", exception.getMessage());
    }

    @Test
    void testIsPDF_Valid() {
        byte[] validPDF = "%PDF-1.4".getBytes();
        assertTrue(fileService.isPDF(validPDF));
    }

    @Test
    void testIsPDF_Invalid() {
        byte[] invalidPDF = "INVALID".getBytes();
        assertFalse(fileService.isPDF(invalidPDF));
    }

    @Test
    void testExceedsMaxSize_WithinLimit() {
        byte[] content = new byte[(int)(4.9 * 1024 * 1024)]; // 4.9 MB
        assertFalse(fileService.exceedsMaxSize(content));
    }

    @Test
    void testExceedsMaxSize_OverLimit() {
        byte[] content = new byte[(int)(5.1 * 1024 * 1024)]; // 5.1 MB
        assertTrue(fileService.exceedsMaxSize(content));
    }
}