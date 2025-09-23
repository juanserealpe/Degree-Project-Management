package Main;

import DataBase.DbConnection;
import Dtos.UserRegisterDTO;
import Enums.EnumProgram;
import Enums.EnumRole;
import Interfaces.IAuthService;
import Models.Account;
import Models.User;
import Repositories.CredentialRepository;
import Services.ServiceFactory;
import Services.UserRegisterServices;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Connection conn = DbConnection.getConnection();
        ServiceFactory factory = new ServiceFactory(conn);

        IAuthService authService = factory.getAuthService();
        UserRegisterServices userService = factory.getUserService();


        User user = new User("LUIS", "HernAndez", "3045656556");
        Account account = new Account();
        account.setEmail("luishernsandsez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(EnumRole.JURY));

        UserRegisterDTO dto = new UserRegisterDTO("Jury123?", user, account);
    /*
        try {
            userService.registerUser(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        UserRegisterDTO resultValid = authService.isLoginValid("luishernsandsez@unicauca.edu.co", "Jury123?");
        System.out.println(resultValid.toString());
    }

}
