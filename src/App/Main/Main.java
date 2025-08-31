package App.Main;

import App.DataBase.AppContext;
import App.Views.LoginView;
import App.Views.MainView;

public class Main {

    public static void main(String[] args) throws Exception {

        AppContext.init();

                javax.swing.SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
        /*
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setAccountController(AppContext.accountController);
            loginView.setVisible(true);
        });
        */
    }
}
