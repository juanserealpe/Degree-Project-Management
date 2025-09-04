package App.Views.AuthViews;

import java.awt.*;
import javax.swing.*;

public class AuthView extends JPanel {

    private CardLayout cardLayout;
    private JPanel rightContainer;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public AuthView() {
        setLayout(new BorderLayout());

        // Panel izquierdo fijo (logo o diseño)
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBackground(new Color(30, 30, 80));
        leftPanel.add(new JLabel("Aquí podría ir tu logo grande"));
        add(leftPanel, BorderLayout.WEST);

        // Panel derecho con CardLayout
        cardLayout = new CardLayout();
        rightContainer = new JPanel(cardLayout);

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();

        rightContainer.add(loginPanel, "LOGIN");
        rightContainer.add(registerPanel, "REGISTER");

        add(rightContainer, BorderLayout.CENTER);

        // Listeners para cambiar entre vistas
        loginPanel.getLblRegister().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showRegister();
            }
        });

        registerPanel.getLblLoginLink().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showLogin();
            }
        });
    }

    public void showLogin() {
        cardLayout.show(rightContainer, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(rightContainer, "REGISTER");
    }
}
