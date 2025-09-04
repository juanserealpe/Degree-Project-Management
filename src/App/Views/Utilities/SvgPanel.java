package App.Views.Utilities;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SvgPanel extends JPanel {

    private SVGDiagram diagram;

    // =====================================================
    // === CONSTRUCTOR: Carga el archivo SVG desde classpath
    // =====================================================
    public SvgPanel(String resourcePath) {
        URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("⚠ No se encontró el archivo SVG: " + resourcePath);
            return;
        }
        SVGUniverse universe = new SVGUniverse();
        try {
            diagram = universe.getDiagram(universe.loadSVG(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================
    // === RENDERIZADO: Escala y dibuja el SVG en el panel
    // =====================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (diagram != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                float docWidth = diagram.getWidth();
                float docHeight = diagram.getHeight();

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                double scaleX = panelWidth / docWidth;
                double scaleY = panelHeight / docHeight;
                double scale = Math.min(scaleX, scaleY);

                double tx = (panelWidth - docWidth * scale) / 2;
                double ty = (panelHeight - docHeight * scale) / 2;

                g2.translate(tx, ty);
                g2.scale(scale, scale);
                diagram.render(g2);

            } catch (SVGException e) {
                e.printStackTrace();
            } finally {
                g2.dispose();
            }
        }
    }

}
