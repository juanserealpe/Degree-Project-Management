package Utilities;

import java.time.LocalDateTime;

/**
 * Logger simple para la aplicación.
 *
 * Esta clase proporciona métodos estáticos para registrar mensajes
 * de información, advertencia y error en la consola, incluyendo
 * timestamp y el nombre de la clase origen.
 *
 * Ejemplo de uso:
 * Logger.info(MyClass.class, "Mensaje de información");
 * Logger.warn(MyClass.class, "Mensaje de advertencia");
 * Logger.error(MyClass.class, "Mensaje de error");
 *
 * No se permite instanciación.
 *
 * @author juanserealpe
 */
public class Logger {
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    // Constructor privado para evitar instanciación
    private Logger() {}

    // Obtiene timestamp actual en formato ISO-8601
    private static String timestamp() {
        return LocalDateTime.now().toString();
    }

    /**
     * Registra un mensaje de información.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void info(Class<?> source, String message) {
        System.out.println(ANSI_BLUE+"[INFO][" + timestamp() + "][" + source.getSimpleName() + "] "+ ANSI_RESET + message);
    }
    /**
     * Registra un mensaje de información.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void success(Class<?> source, String message) {
        System.out.println(ANSI_GREEN + "[SUCCESS]["+ timestamp() + "][" + source.getSimpleName() + "] "+ ANSI_RESET + message);
    }
    /**
     * Registra un mensaje de advertencia.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void warn(Class<?> source, String message) {
        System.out.println(ANSI_YELLOW +"[WARN][" + timestamp() + "][" + source.getSimpleName() + "] " + message + ANSI_RESET);
    }

    /**
     * Registra un mensaje de error.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void error(Class<?> source, String message) {
        System.out.println(ANSI_RED + "[ERROR][" + timestamp() + "][" + source.getSimpleName() + "] " + message + ANSI_RESET);
    }
}
