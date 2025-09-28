package Utilities;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;

public class PDFViewer {

    /**
     * Abre un PDF en una nueva ventana con scroll, zoom y navegación por páginas.
     * @param pdfFile archivo PDF a mostrar
     */
    public static void open(File pdfFile) {
        Platform.runLater(() -> {
            try {
                PDDocument document = PDDocument.load(pdfFile);
                PDFRenderer renderer = new PDFRenderer(document);

                final int[] pageIndex = {0};
                final double[] zoom = {1.0};

                ImageView imageView = renderPage(renderer, pageIndex[0], zoom[0]);

                ScrollPane scrollPane = new ScrollPane(imageView);
                scrollPane.setPannable(true);

                Button prevBtn = new Button("←");
                Button nextBtn = new Button("→");
                Button zoomInBtn = new Button("+");
                Button zoomOutBtn = new Button("−");
                javafx.scene.control.Label pageCount = new javafx.scene.control.Label("Pagina 1");
                prevBtn.setOnAction(e -> {
                    if (pageIndex[0] > 0) {
                        pageIndex[0]--;
                        pageCount.setText("Página " + (pageIndex[0] + 1) + " / " + document.getNumberOfPages());
                        imageView.setImage(renderPage(renderer, pageIndex[0], zoom[0]).getImage());
                    }
                });

                nextBtn.setOnAction(e -> {
                    if (pageIndex[0] < document.getNumberOfPages() - 1) {
                        pageIndex[0]++;
                        pageCount.setText("Página " + (pageIndex[0] + 1) + " / " + document.getNumberOfPages());
                        imageView.setImage(renderPage(renderer, pageIndex[0], zoom[0]).getImage());
                    }
                });

                zoomInBtn.setOnAction(e -> {
                    zoom[0] *= 1.25;
                    imageView.setImage(renderPage(renderer, pageIndex[0], zoom[0]).getImage());
                });

                zoomOutBtn.setOnAction(e -> {
                    zoom[0] /= 1.25;
                    imageView.setImage(renderPage(renderer, pageIndex[0], zoom[0]).getImage());
                });

                HBox controls = new HBox(10, prevBtn, nextBtn, zoomOutBtn, zoomInBtn, pageCount);

                BorderPane root = new BorderPane();
                root.setCenter(scrollPane);
                root.setBottom(controls);

                Stage stage = new Stage();
                stage.setTitle("PDF Viewer");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();

                stage.setOnCloseRequest(e -> {
                    try {
                        document.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private static ImageView renderPage(PDFRenderer renderer, int pageIndex, double zoom) {
        try {
            BufferedImage bim = renderer.renderImage(pageIndex, (float) zoom);
            WritableImage fxImage = SwingFXUtils.toFXImage(bim, null);
            return new ImageView(fxImage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageView();
        }
    }
}
