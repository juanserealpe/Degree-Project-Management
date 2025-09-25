package Exceptions;

public class FileException extends AppException {

    public FileException(String message) {
        super(message);
    }

    public static FileException alreadyUploaded() {
        return new FileException("El archivo ya fue subido.");
    }

    public static FileException emptyFile() {
        return new FileException("El archivo está vacío.");
    }

    public static FileException dangerousFilename() {
        return new FileException("El nombre del archivo contiene caracteres peligrosos.");
    }

    public static FileException incorrectExtension() {
        return new FileException("La extensión del archivo no es válida. Se requiere .pdf.");
    }

    public static FileException maxCapacity() {
        return new FileException("El archivo excede el tamaño máximo permitido.");
    }

    public static FileException incorrectMagicBytes() {
        return new FileException("El archivo no tiene la firma correcta de PDF.");
    }

    public static FileException fileNotFound() {
        return new FileException("No se pudo encontrar o leer el archivo.");
    }

    public static FileException unknownError() {
        return new FileException("Error desconocido al procesar el archivo.");
    }
}