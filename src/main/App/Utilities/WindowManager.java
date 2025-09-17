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

public class WindowManager {

    private static final double STANDARD_WIDTH = 800;
    private static final double STANDARD_HEIGHT = 600;
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 600;

    public static void setupWindow(Stage stage, String title, boolean startMaximized,
                                   double standardWidth, double standardHeight) {

        stage.setTitle(title);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setResizable(true);

        Preferences prefs = Preferences.userNodeForPackage(WindowManager.class);

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

        stage.setOnCloseRequest(e -> {
            prefs.putDouble(title + "_X", stage.getX());
            prefs.putDouble(title + "_Y", stage.getY());
            prefs.putDouble(title + "_W", stage.getWidth());
            prefs.putDouble(title + "_H", stage.getHeight());
        });

        stage.maximizedProperty().addListener((obs, wasMax, isNowMax) -> {
            if (wasMax && !isNowMax) {
                restoreToStandardSizeOnCurrentScreen(stage, standardWidth, standardHeight);
            }
        });

        if (startMaximized && stage.getScene() == null) {
            Platform.runLater(() -> stage.setMaximized(true));
        }
    }

    public static void changeScene(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(fxmlPath));
        Scene newScene = new Scene(loader.load());
        stage.setScene(newScene);
        stage.setTitle(title);
    }

    public static void setupWindow(Stage stage, String title) {
        setupWindow(stage, title, true, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

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

    private static void restoreToStandardSizeOnCurrentScreen(Stage stage, double standardWidth, double standardHeight) {
        double currentX = stage.getX();
        double currentY = stage.getY();

        Screen currentScreen = getCurrentScreen(currentX, currentY);

        if (currentScreen == null) {
            currentScreen = Screen.getPrimary();
        }

        Rectangle2D screenBounds = currentScreen.getVisualBounds();

        double optimalWidth = standardWidth;
        double optimalHeight = standardHeight;

        if (screenBounds.getWidth() < standardWidth) {
            optimalWidth = screenBounds.getWidth() * 0.9;
        }
        if (screenBounds.getHeight() < standardHeight) {
            optimalHeight = screenBounds.getHeight() * 0.9;
        }

        double centerX = screenBounds.getMinX() + (screenBounds.getWidth() - optimalWidth) / 2;
        double centerY = screenBounds.getMinY() + (screenBounds.getHeight() - optimalHeight) / 2;

        System.out.println("Restaurando a: " +
                (int)optimalWidth + "x" + (int)optimalHeight +
                " en posición (" + (int)centerX + "," + (int)centerY + ")");

        applyDimensionsWithRetry(stage, centerX, centerY, optimalWidth, optimalHeight);
    }

    private static Screen getCurrentScreen(double windowX, double windowY) {
        List<Screen> screens = Screen.getScreens();

        for (Screen screen : screens) {
            Rectangle2D bounds = screen.getVisualBounds();

            if (windowX >= bounds.getMinX() && windowX < bounds.getMaxX() &&
                    windowY >= bounds.getMinY() && windowY < bounds.getMaxY()) {

                return screen;
            }
        }

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

    private static void applyDimensionsWithRetry(Stage stage, double x, double y, double width, double height) {
        stage.setX(x);
        stage.setY(y);
        stage.setWidth(width);
        stage.setHeight(height);

        Platform.runLater(() -> {
            stage.setX(x);
            stage.setY(y);
            stage.setWidth(width);
            stage.setHeight(height);
        });

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
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

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