package Services;

import Exceptions.FileException;
import Interfaces.IFileService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
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
    public String readFile(String path, String filename) throws IOException {
        File file = new File(path + filename);
        if (!file.exists()) {
            throw new FileNotFoundException("Archivo no encontrado: " + file.getAbsolutePath());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        }
    }

    @Override
    public String saveFile(String path, String filename, String content) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(path + filename);
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            Runtime.getRuntime().exec("attrib +H " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("No se pudo ocultar el archivo: " + e.getMessage());
        }

        return file.getAbsolutePath();
    }
    @Override
    public String uploadFile(String path) throws FileException {
        try {
            if (fileAlreadyUploaded) {
                throw FileException.alreadyUploaded();
            }

            File file = new File(path);
            if (file.length() == 0) {
                throw FileException.emptyFile();
            }

            if (hasDangerousChars(file.getName())) {
                throw FileException.dangerousFilename();
            }

            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                throw FileException.incorrectExtension();
            }

            byte[] content = Files.readAllBytes(file.toPath());
            if (exceedsMaxSize(content)) {
                throw FileException.maxCapacity();
            }

            if (!isPDF(content)) {
                throw FileException.incorrectMagicBytes();
            }

            createUploadDirectory();
            String currentDir = System.getProperty("user.dir") + "\\";
            String safeFileName = generateSafeFileName(file.getName());
            Path destinationPath = Paths.get(currentDir + UPLOAD_DIRECTORY + safeFileName);

            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            fileAlreadyUploaded = true;

            return safeFileName;

        } catch (IOException e) {
            throw FileException.fileNotFound();
        } catch (FileException e) {
            throw e;
        } catch (Exception e) {
            throw FileException.unknownError();
        }
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

    public boolean isPDF(byte[] content) {
        if (content.length < 5) return false;
        return content[0] == '%' && content[1] == 'P' && content[2] == 'D' && content[3] == 'F' && content[4] == '-';
    }

    public boolean exceedsMaxSize(byte[] content) {
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
    public String DownloadFile() {
        return null;
    }

    @Override
    public void DeleteFile(String Path) {

    }
}