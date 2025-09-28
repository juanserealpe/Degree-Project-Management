package Models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploadModel {

    public FileUploadModel() {
    }

    /**
     * Simula la subida de un archivo. En este caso, lee el contenido del archivo
     * y lo devuelve como String. Puedes modificarlo para guardar en base de datos,
     * copiar a otra ubicación, etc.
     *
     * @param file Archivo seleccionado por el usuario
     * @return Contenido del archivo como String, o mensaje de error
     */
    public String uploadFile(File file) {
        if (file == null || !file.exists()) {
            return "Archivo no válido o inexistente.";
        }

        try {
            Path path = file.toPath();
            String content = new String(Files.readAllBytes(path));
            return "Archivo subido exitosamente:\n" + content;
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }
    }

    /**
     * Alternativa: solo devuelve la ruta del archivo (útil si no quieres leerlo)
     */
    public String getFilePath(File file) {
        return file != null ? file.getAbsolutePath() : "Ningún archivo seleccionado";
    }
}