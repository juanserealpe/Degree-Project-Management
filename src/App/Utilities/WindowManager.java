package Utilities;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Utilidad para manejar el comportamiento de maximizar/restaurar en todas las ventanas
 * de la aplicación de forma consistente y profesional.
 */
public class WindowManager {

    private static final double STANDARD_WIDTH = 1024;
    private static final double STANDARD_HEIGHT = 768;
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 600;

    /**
     * Configura una ventana con el comportamiento estándar de la aplicación
     *
     * @param stage La ventana a configurar
     * @param title El título de la ventana
     * @param startMaximized Si debe iniciar maximizada (true por defecto)
     */
    /**
     * Configura una ventana SOLO la primera vez que se crea (no al cambiar de escena).
     */
    public static void setupWindow(Stage stage, String title, boolean startMaximized,
                                   double standardWidth, double standardHeight) {

        stage.setTitle(title);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setResizable(true);

        Preferences prefs = Preferences.userNodeForPackage(WindowManager.class);

        // Solo si la ventana aún no tiene dimensiones definidas (primera vez)
        if (Double.isNaN(stage.getX()) || Double.isNaN(stage.getY()) || stage.getWidth() == 0 || stage.getHeight() == 0) {
            double x = prefs.getDouble(title + "_X", Double.NaN);
            double y = prefs.getDouble(title + "_Y", Double.NaN);
            double w = prefs.getDouble(title + "_W", standardWidth);
            double h = prefs.getDouble(title + "_H", standardHeight);

            if (!Double.isNaN(x) && !Double.isNaN(y)) {
                stage.setX(x);
                stage.setY(y);
                stage.setWidth(w);
                stage.setHeight(h);
            } else {
                centerWindowOnPrimaryScreen(stage, standardWidth, standardHeight);
            }
        }

        // Guardar al cerrar
        stage.setOnCloseRequest(e -> {
            prefs.putDouble(title + "_X", stage.getX());
            prefs.putDouble(title + "_Y", stage.getY());
            prefs.putDouble(title + "_W", stage.getWidth());
            prefs.putDouble(title + "_H", stage.getHeight());
        });

        // Listener para restaurar tamaño al salir de maximizado
        stage.maximizedProperty().addListener((obs, wasMax, isNowMax) -> {
            if (wasMax && !isNowMax) {
                restoreToStandardSizeOnCurrentScreen(stage, standardWidth, standardHeight);
            }
        });

        // Solo la primera vez se maximiza si corresponde
        if (startMaximized && stage.getScene() == null) {
            Platform.runLater(() -> stage.setMaximized(true));
        }

        System.out.println("Ventana configurada: " + title +
                " | Maximizada al inicio: " + startMaximized +
                " | Tamaño estándar: " + (int) standardWidth + "x" + (int) standardHeight);
    }

    /**
     * Cambiar de escena sin modificar tamaño/posición.
     */
    public static void changeScene(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(fxmlPath));
        Scene newScene = new Scene(loader.load());
        stage.setScene(newScene);
        stage.setTitle(title);
    }


    /**
     * Configura una ventana con el comportamiento estándar de la aplicación
     *
     * @param stage La ventana a configurar
     * @param title El título de la ventana
     */
    public static void setupWindow(Stage stage, String title) {
        setupWindow(stage, title, true, STANDARD_WIDTH, STANDARD_HEIGHT);
    }


    /**
     * Centra una ventana en la pantalla principal
     */
    private static void centerWindowOnPrimaryScreen(Stage stage, double width, double height) {
        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D screenBounds = primaryScreen.getVisualBounds();

        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - width) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - height) / 2;

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(centerX);
        stage.setY(centerY);
    }

    /**
     * Restaura la ventana al tamaño estándar centrada en la pantalla donde se encuentra actualmente
     */
    private static void restoreToStandardSizeOnCurrentScreen(Stage stage, double standardWidth, double standardHeight) {
        System.out.println("\n=== RESTAURANDO VENTANA: " + stage.getTitle() + " ===");

        // Obtener la posición actual de la ventana
        double currentX = stage.getX();
        double currentY = stage.getY();

        // Encontrar en qué pantalla está actualmente la ventana
        Screen currentScreen = getCurrentScreen(currentX, currentY);

        if (currentScreen == null) {
            System.out.println("No se pudo determinar la pantalla actual, usando pantalla principal");
            currentScreen = Screen.getPrimary();
        }

        Rectangle2D screenBounds = currentScreen.getVisualBounds();

        System.out.println("Pantalla detectada: " +
                (int)screenBounds.getWidth() + "x" + (int)screenBounds.getHeight() +
                " en posición (" + (int)screenBounds.getMinX() + "," + (int)screenBounds.getMinY() + ")");

        // Calcular el tamaño óptimo para esta pantalla
        double optimalWidth = standardWidth;
        double optimalHeight = standardHeight;

        // Si la pantalla es más pequeña que nuestro tamaño estándar, ajustar
        if (screenBounds.getWidth() < standardWidth) {
            optimalWidth = screenBounds.getWidth() * 0.9; // 90% del ancho de pantalla
        }
        if (screenBounds.getHeight() < standardHeight) {
            optimalHeight = screenBounds.getHeight() * 0.9; // 90% del alto de pantalla
        }

        // Calcular posición centrada en esta pantalla
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - optimalWidth) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - optimalHeight) / 2;

        System.out.println("Restaurando a: " +
                (int)optimalWidth + "x" + (int)optimalHeight +
                " en posición (" + (int)centerX + "," + (int)centerY + ")");

        // Aplicar las nuevas dimensiones
        applyDimensionsWithRetry(stage, centerX, centerY, optimalWidth, optimalHeight);
    }

    /**
     * Encuentra en qué pantalla está actualmente la ventana basándose en su posición
     */
    private static Screen getCurrentScreen(double windowX, double windowY) {
        List<Screen> screens = Screen.getScreens();

        for (Screen screen : screens) {
            Rectangle2D bounds = screen.getVisualBounds();

            // Verificar si la posición de la ventana está dentro de esta pantalla
            if (windowX >= bounds.getMinX() && windowX < bounds.getMaxX() &&
                    windowY >= bounds.getMinY() && windowY < bounds.getMaxY()) {

                return screen;
            }
        }

        // Si no se encuentra, buscar la pantalla más cercana
        Screen closestScreen = Screen.getPrimary();
        double minDistance = Double.MAX_VALUE;

        for (Screen screen : screens) {
            Rectangle2D bounds = screen.getVisualBounds();
            double centerX = bounds.getMinX() + bounds.getWidth() / 2;
            double centerY = bounds.getMinY() + bounds.getHeight() / 2;

            double distance = Math.sqrt(Math.pow(windowX - centerX, 2) + Math.pow(windowY - centerY, 2));

            if (distance < minDistance) {
                minDistance = distance;
                closestScreen = screen;
            }
        }

        return closestScreen;
    }

    /**
     * Aplica las dimensiones con varios intentos para asegurar que funcione
     */
    private static void applyDimensionsWithRetry(Stage stage, double x, double y, double width, double height) {
        // Primer intento inmediato
        stage.setX(x);
        stage.setY(y);
        stage.setWidth(width);
        stage.setHeight(height);

        // Segundo intento con delay
        Platform.runLater(() -> {
            stage.setX(x);
            stage.setY(y);
            stage.setWidth(width);
            stage.setHeight(height);
        });

        // Tercer intento con delay mayor
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(50);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            stage.setX(x);
            stage.setY(y);
            stage.setWidth(width);
            stage.setHeight(height);

            System.out.println("Dimensiones aplicadas correctamente para: " + stage.getTitle());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Centra una ventana manualmente en la pantalla actual
     *
     * @param stage La ventana a centrar
     */
    public static void centerWindow(Stage stage) {
        Screen currentScreen = getCurrentScreen(stage.getX(), stage.getY());
        if (currentScreen == null) {
            currentScreen = Screen.getPrimary();
        }

        Rectangle2D screenBounds = currentScreen.getVisualBounds();
        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - stage.getHeight()) / 2;

        stage.setX(centerX);
        stage.setY(centerY);
    }

    /**
     * Configuraciones predefinidas para diferentes tipos de ventanas
     */
    public static class WindowTypes {
        public static void setupMainWindow(Stage stage) {
            setupWindow(stage, "Sistema de Gestión de Proyectos de Grado", true, 1200, 800);
        }

        public static void setupDialogWindow(Stage stage, String title) {
            setupWindow(stage, title, false, 600, 400);
        }

        public static void setupReportWindow(Stage stage, String title) {
            setupWindow(stage, title, true, 1000, 700);
        }

        public static void setupSettingsWindow(Stage stage) {
            setupWindow(stage, "Configuración", false, 800, 600);
        }
    }
}