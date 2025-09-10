package App.Services;

import App.Interfaces.IFileService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileService implements IFileService {
    public static final double MB = 1024 * 1024;
    private static final List<String> DANGEROUS_CHARS = Arrays.asList("../", "\0", "%00", ":", ";", "*", "?");
    private boolean fileAlreadyUploaded = false;
    private static final String UPLOAD_DIRECTORY = "uploads/";

    @Override
    public FileResult UploadFile() {
        FileResult tempFile = new FileResult();
        try {
            if (fileAlreadyUploaded) {
                tempFile.setFileStatus(FileStatus.ALREADY_UPLOADED);
                return tempFile;
            }

            File file = OpenFileDialog();
            if (file.length() == 0) {
                tempFile.setFileStatus(FileStatus.EMPTY_FILE);
                return tempFile;
            }

            if (hasDangerousChars(file.getName())) {
                tempFile.setFileStatus(FileStatus.DANGEROUS_FILENAME);
                return tempFile;
            }

            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                tempFile.setFileStatus(FileStatus.INCORRECT_EXTENSION);
                return tempFile;
            }

            byte[] content = Files.readAllBytes(file.toPath());
            if (exceedsMaxSize(content)) {
                tempFile.setFileStatus(FileStatus.MAX_CAPACITY);
                return tempFile;
            }

            if (!isPDF(content)) {
                tempFile.setFileStatus(FileStatus.INCORRECT_MAGIC_BYTES);
                return tempFile;
            }

            createUploadDirectory();
            String currentDir = System.getProperty("user.dir")+"\\";

            String safeFileName = generateSafeFileName(file.getName());
            Path destinationPath = Paths.get(currentDir+ UPLOAD_DIRECTORY + safeFileName);

            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            tempFile.setPath(destinationPath.toString());
            tempFile.setFileStatus(FileStatus.SUCCESS);
            fileAlreadyUploaded = true;
            System.out.println(destinationPath.toString());
        } catch (IllegalArgumentException e) {
            tempFile.setFileStatus(FileStatus.FILE_NO_SELECTED);
        } catch (IOException e) {
            tempFile.setFileStatus(FileStatus.FILE_NOT_FOUND);
        } catch (Exception e) {
            tempFile.setFileStatus(FileStatus.INVALID_FILE);
        }
        return tempFile;
    }
    private void createUploadDirectory() throws IOException {
        String currentDir = System.getProperty("user.dir")+"\\";

        Path uploadPath = Paths.get(currentDir + UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    private String generateSafeFileName(String originalName) {
        // Extraer la extensión del archivo original
        String extension = "";
        int lastDotIndex = originalName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalName.substring(lastDotIndex);
        }

        // Generar nombre seguro usando UUID + extensión original
        return UUID.randomUUID().toString() + extension;
    }

    private boolean hasDangerousChars(String filename) {
        return DANGEROUS_CHARS.stream().anyMatch(filename::contains);
    }

    boolean isPDF(byte[] content) {
        if (content.length < 5) return false;
        return content[0] == '%' && content[1] == 'P' && content[2] == 'D' && content[3] == 'F' && content[4] == '-';
    }

    boolean exceedsMaxSize(byte[] content) {
        return content.length > 5 * MB;
    }

    private File OpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo PDF");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
        fileChooser.setMultiSelectionEnabled(false);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        throw new IllegalArgumentException("No se selecciono el archivo PDF");
    }

    @Override
    public FileResult DownloadFile() {
        return null;
    }

    @Override
    public void DeleteFile(String Path) {

    }
}
