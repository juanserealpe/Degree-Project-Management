package App.Views.AuthViews;

import App.Views.Utilities.SvgPanel;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    // =========================
    // Atributos de la clase
    // =========================
    private JLabel lblUser, lblPass, lblUserError, lblPassError, lblError, infoUni, decoration;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private SvgPanel logo;
    private ImageIcon icon;

    public LoginView() {
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

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(15, 15, 15, 15);

        // Logo SVG en panel derecho
        logo = new SvgPanel("/img/Logo.svg");
        logo.setPreferredSize(new Dimension(250, 250));
        logo.setMinimumSize(new Dimension(250, 250));
        logo.setMaximumSize(new Dimension(250, 250));
        logo.setOpaque(false);

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 2;
        gbc2.insets = new Insets(10, 10, 30, 10);
        rightPanel.add(logo, gbc2);

        // Etiquetas y campos
        lblUser = new JLabel("Email:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc2.gridy = 1;
        gbc2.gridx = 0;
        gbc2.gridwidth = 1;
        gbc2.insets = new Insets(10, 15, 10, 15);
        rightPanel.add(lblUser, gbc2);

        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc2.gridx = 1;
        rightPanel.add(txtUser, gbc2);

        lblUserError = new JLabel("(!)");
        lblUserError.setForeground(Color.RED);
        lblUserError.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUserError.setVisible(false);
        gbc2.gridx = 2;
        gbc2.insets = new Insets(10, 2, 10, 15);
        rightPanel.add(lblUserError, gbc2);

        lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.insets = new Insets(10, 15, 10, 15);
        rightPanel.add(lblPass, gbc2);

        txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc2.gridx = 1;
        rightPanel.add(txtPass, gbc2);

        lblPassError = new JLabel("(!)");
        lblPassError.setForeground(Color.RED);
        lblPassError.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassError.setVisible(false);
        gbc2.gridx = 2;
        gbc2.insets = new Insets(10, 2, 10, 15);
        rightPanel.add(lblPassError, gbc2);

        lblError = new JLabel("(!) Debe de llenar los campos requeridos");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblError.setVisible(false);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(5, 15, 5, 15);
        rightPanel.add(lblError, gbc2);

        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc2.gridy = 4;
        gbc2.gridx = 0;
        gbc2.gridwidth = 3;
        rightPanel.add(btnLogin, gbc2);

        // =========================
// Botón de registro (enlace estilo)
// =========================
        JLabel lblRegister = new JLabel("¿No tienes cuenta? Regístrate aquí");
        lblRegister.setForeground(Color.CYAN.darker());
        lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc2.gridy = 5;
        gbc2.gridx = 0;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(10, 15, 10, 15);
        rightPanel.add(lblRegister, gbc2);

// Agrego un listener para que actúe como enlace
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                
            }
        });

        btnLogin.addActionListener(e -> {
            String email = txtUser.getText().trim();
            String password = new String(txtPass.getPassword()).trim();

            boolean hasError = false;

            if (email.isEmpty()) {
                lblUserError.setVisible(true);
                hasError = true;
            } else {
                lblUserError.setVisible(false);
            }

            if (password.isEmpty()) {
                lblPassError.setVisible(true);
                hasError = true;
            } else {
                lblPassError.setVisible(false);
            }

            if (hasError) {
                lblError.setVisible(true);
            }
            rightPanel.revalidate();
            rightPanel.repaint();
        });

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