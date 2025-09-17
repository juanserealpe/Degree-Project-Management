package Utilities;

import java.time.LocalDateTime;

public class Logger {

    private Logger() {}

    private static String timestamp() {
        return LocalDateTime.now().toString();
    }

    public static void info(String message) {
        System.out.println("[INFO][" + timestamp() + "] " + message);
    }

    public static void warn(String message) {
        System.out.println("[WARN][" + timestamp() + "] " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR][" + timestamp() + "] " + message);
    }
}
