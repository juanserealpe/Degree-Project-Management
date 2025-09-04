/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Views;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author juans
 */
public class BackgroundPanel extends JPanel {
    private Image background;

    public BackgroundPanel(String resourcePath) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            throw new IllegalArgumentException("No se encontró el recurso: " + resourcePath);
        }
        background = new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
