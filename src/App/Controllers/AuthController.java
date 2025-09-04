package App.Controllers;

import App.Services.AuthService;
import App.Views.AuthViews.LoginView;

public class AuthController {

    private AuthService service;
    private LoginView loginView;

    public AuthController(AuthService service, LoginView loginView) {
        this.service = service;
        this.loginView = loginView;
        loginView.getBtnLogin().addActionListener(e -> handleLogin());
    }

    public void show() {
        loginView.setVisible(true);
    }

    private void handleLogin() {

        String email = loginView.getTxtUser().getText();
        String password = new String(loginView.getTxtPass().getPassword());

        boolean hasError = false;

        if (email.isEmpty()) {
            loginView.getLblUserError().setVisible(true);
            hasError = true;
        } else {
            loginView.getLblUserError().setVisible(false);
        }

        if (password.isEmpty()) {
            loginView.getLblPassError().setVisible(true);
            hasError = true;
        } else {
            loginView.getLblPassError().setVisible(false);
        }

        if (hasError) {
            loginView.getLblError().setText("(!) Debe llenar los campos requeridos");
            loginView.getLblError().setVisible(true);
            return;
        }

        try {
            service.isLoginValid(email, password);
            loginView.getLblError().setVisible(false);
            loginView.dispose();
            System.out.println("Login exitoso");

        } catch (Exception ex) {
            loginView.getLblError().setText("(!) " + ex.getMessage());
            loginView.getLblError().setVisible(true);
        }

    }

    public AuthService getService() {
        return service;
    }

    public void setService(AuthService service) {
        this.service = service;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

}
