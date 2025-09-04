package App.Views.AuthViews;

import java.awt.*;
import javax.swing.*;

public class RegisterPanel extends JPanel {

    private JTextField txtNombres, txtApellidos, txtEmail, txtTelefono;
    private JComboBox<String> cbRol, cbPrograma;
    private JPasswordField txtPass;
    private JButton btnRegister;
    private JLabel lblLoginLink;

    public RegisterPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Registro");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Campos de ejemplo
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Nombres:"), gbc);
        txtNombres = new JTextField(15);
        gbc.gridx = 1;
        add(txtNombres, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField(15);
        gbc.gridx = 1;
        add(txtApellidos, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(15);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Rol:"), gbc);
        cbRol = new JComboBox<>(new String[]{"1: Estudiante", "2: Director", "3: Coordinador"});
        gbc.gridx = 1;
        add(cbRol, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Programa:"), gbc);
        cbPrograma = new JComboBox<>(new String[]{"1: Ingeniería de sistemas", "2: Otra carrera"});
        gbc.gridx = 1;
        add(cbPrograma, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Contraseña:"), gbc);
        txtPass = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtPass, gbc);

        // Botón registrar
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        btnRegister = new JButton("Registrar");
        btnRegister.setBackground(new Color(0, 150, 100));
        btnRegister.setForeground(Color.WHITE);
        add(btnRegister, gbc);

        // Enlace volver a login
        lblLoginLink = new JLabel("¿Ya tienes cuenta? Inicia sesión");
        lblLoginLink.setForeground(Color.CYAN.darker());
        lblLoginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy++;
        add(lblLoginLink, gbc);
    }

    public JLabel getLblLoginLink() {
        return lblLoginLink;
    }
}
