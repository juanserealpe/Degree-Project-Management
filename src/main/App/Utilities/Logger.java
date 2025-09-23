package Utilities;

import java.time.LocalDateTime;

public class Logger {

    private Logger() {}

    private static String timestamp() {
        return LocalDateTime.now().toString();
    }

    public static void info(Class<?> source, String message) {
        System.out.println("[INFO][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }

    public static void warn(Class<?> source, String message) {
        System.out.println("[WARN][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }

    public static void error(Class<?> source, String message) {
        System.err.println("[ERROR][" + timestamp() + "][" + source.getSimpleName() + "] " + message);
    }

}
