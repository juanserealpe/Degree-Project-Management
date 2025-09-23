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
        System.out.println("[INFO][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }

    /**
     * Registra un mensaje de advertencia.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void warn(Class<?> source, String message) {
        System.out.println("[WARN][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }

    /**
     * Registra un mensaje de error.
     *
     * @param source Clase origen del mensaje.
     * @param message Mensaje a registrar.
     */
    public static void error(Class<?> source, String message) {
        System.err.println("[ERROR][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }
}
