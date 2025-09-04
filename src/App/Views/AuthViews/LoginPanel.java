package App.Views.AuthViews;

import App.Views.Utilities.SvgPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblUserErrorIcon, lblPassErrorIcon;
    private JLabel lblUserErrorMsg, lblPassErrorMsg, lblGeneralError;
    private JLabel lblRegister;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(15, 15, 15, 15);

        // Logo SVG
        SvgPanel logo = new SvgPanel("/img/Logo.svg");
        logo.setPreferredSize(new Dimension(250, 250));
        logo.setOpaque(false);

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(10, 10, 30, 10);
        add(logo, gbc2);

        // Label Email
        JLabel lblUser = new JLabel("Email:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc2.gridy = 1;
        gbc2.gridx = 0;
        gbc2.gridwidth = 1;
        gbc2.insets = new Insets(5, 15, 5, 15);
        add(lblUser, gbc2);

        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc2.gridx = 1;
        add(txtUser, gbc2);

        lblUserErrorIcon = new JLabel("(!)");
        lblUserErrorIcon.setForeground(Color.RED);
        lblUserErrorIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUserErrorIcon.setVisible(false);
        gbc2.gridx = 2;
        gbc2.insets = new Insets(5, 2, 5, 15);
        add(lblUserErrorIcon, gbc2);

        // Msg error Email
        lblUserErrorMsg = new JLabel("El email es obligatorio");
        lblUserErrorMsg.setForeground(Color.RED);
        lblUserErrorMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserErrorMsg.setVisible(false);
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(2, 15, 10, 15);
        add(lblUserErrorMsg, gbc2);

        // Label Contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 1;
        gbc2.insets = new Insets(5, 15, 5, 15);
        add(lblPass, gbc2);

        txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc2.gridx = 1;
        add(txtPass, gbc2);

        lblPassErrorIcon = new JLabel("(!)");
        lblPassErrorIcon.setForeground(Color.RED);
        lblPassErrorIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassErrorIcon.setVisible(false);
        gbc2.gridx = 2;
        gbc2.insets = new Insets(5, 2, 5, 15);
        add(lblPassErrorIcon, gbc2);

        // Msg error Contraseña
        lblPassErrorMsg = new JLabel("La contraseña es obligatoria");
        lblPassErrorMsg.setForeground(Color.RED);
        lblPassErrorMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassErrorMsg.setVisible(false);
        gbc2.gridx = 0;
        gbc2.gridy = 4;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(2, 15, 10, 15);
        add(lblPassErrorMsg, gbc2);

        // Error general arriba del botón
        lblGeneralError = new JLabel("(!) Debe de llenar los campos requeridos");
        lblGeneralError.setForeground(Color.RED);
        lblGeneralError.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblGeneralError.setVisible(false);
        gbc2.gridx = 0;
        gbc2.gridy = 5;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(5, 15, 5, 15);
        add(lblGeneralError, gbc2);

        // Botón login
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc2.gridy = 6;
        gbc2.gridx = 0;
        gbc2.gridwidth = 3;
        add(btnLogin, gbc2);

        // Enlace registro
        lblRegister = new JLabel("¿No tienes cuenta? Regístrate aquí");
        lblRegister.setForeground(Color.CYAN.darker());
        lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc2.gridy = 7;
        gbc2.gridx = 0;
        gbc2.gridwidth = 3;
        gbc2.insets = new Insets(10, 15, 10, 15);
        add(lblRegister, gbc2);

        // Listener para validar
        btnLogin.addActionListener(e -> validateForm());
    }

    private void validateForm() {
        String email = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        boolean hasError = false;

        if (email.isEmpty()) {
            lblUserErrorIcon.setVisible(true);
            lblUserErrorMsg.setVisible(true);
            hasError = true;
        } else {
            lblUserErrorIcon.setVisible(false);
            lblUserErrorMsg.setVisible(false);
        }

        if (password.isEmpty()) {
            lblPassErrorIcon.setVisible(true);
            lblPassErrorMsg.setVisible(true);
            hasError = true;
        } else {
            lblPassErrorIcon.setVisible(false);
            lblPassErrorMsg.setVisible(false);
        }

        lblGeneralError.setVisible(hasError);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(25, 25, 112),
                0, getHeight(), new Color(40, 40, 150)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    // Getters para el controlador
    public JTextField getTxtUser() {
        return txtUser;
    }

    public JPasswordField getTxtPass() {
        return txtPass;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JLabel getLblRegister() {
        return lblRegister;
    }
}
