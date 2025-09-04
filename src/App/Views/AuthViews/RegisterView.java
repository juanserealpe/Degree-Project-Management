package App.Views.AuthViews;

import App.Views.Utilities.SvgPanel;
import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

    // =========================
    // Atributos de la clase
    // =========================
    private JLabel lblUser, lblPass, lblUserError, lblPassError, lblError, infoUni, decoration;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private SvgPanel logo;
    private ImageIcon icon;

    public RegisterView() {
        setTitle("Login - Universidad del Cauca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =========================
        // Tamaño inicial 80% pantalla
        // =========================
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.8);
        int height = (int) (screenSize.height * 0.8);
        setSize(width, height);
        setLocationRelativeTo(null);

        // =========================
        // PANEL IZQUIERDO (solo fondo)
        // =========================
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente azul vertical
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(50, 100, 200),
                        0, getHeight(), new Color(30, 60, 150)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Color.decode("#DB141C")));

        // Imagen decorativa PNG
        icon = new ImageIcon(getClass().getResource("/img/PDD1.png"));
        Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        decoration = new JLabel(icon);
        decoration.setHorizontalAlignment(SwingConstants.CENTER);

        // Lema institucional
        infoUni = new JLabel("<html><center>"
                + "<span style='font-size:20px; font-weight:bold;'>Universidad del Cauca</span><br>"
                + "<span style='font-size:14px;'>Fundada el 24 de abril de 1827 por el presidente Francisco de Paula Santander</span><br>"
                + "<span style='font-size:14px;'>Rector: José Luis Diago Franco</span><br>"
                + "<span style='font-size:14px;'>Dirección: Cl 5 #4-70, Centro, Popayán, Cauca</span><br>"
                + "<span style='font-size:14px;'>Teléfono: (602) 8209900</span><br>"
                + "<span style='font-size:14px; font-style:italic;'>Lema: Que los que mueren den luz a la posteridad</span>"
                + "</center></html>");
        infoUni.setForeground(Color.WHITE);
        infoUni.setHorizontalAlignment(SwingConstants.CENTER);
        infoUni.setVerticalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(decoration, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(20, 10, 20, 10);
        leftPanel.add(infoUni, gbc);

        // =========================
        // PANEL DERECHO (Formulario Login + Logo)
        // =========================
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente vertical de azul oscuro a azul más intenso
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(25, 25, 112),
                        0, getHeight(), new Color(40, 40, 150)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        rightPanel.setLayout(new GridBagLayout());

        LoginPanel loginPanel = new LoginPanel();
        rightPanel.add(loginPanel, "LOGIN");

        // =========================
        // SPLIT PANE
        // =========================
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel
        );
        splitPane.setDividerLocation((int) (width * 0.65));
        splitPane.setResizeWeight(0.65);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }

    // Dentro de LoginView
    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JTextField getTxtUser() {
        return txtUser;
    }

    public JPasswordField getTxtPass() {
        return txtPass;
    }

    public JLabel getLblUserError() {
        return lblUserError;
    }

    public JLabel getLblPassError() {
        return lblPassError;
    }

    public JLabel getLblError() {
        return lblError;
    }
}
