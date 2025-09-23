package Repositories;

import Dtos.UserRegisterDTO;
import Enums.EnumProgram;
import Enums.EnumRole;
import Models.Account;
import Models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterRepositoryTest extends BaseRepositoryTest {

    @Test
    void testAddStudent() throws Exception {
        User user = new User("Juan", "Pérez", "301707343");
        Account account = new Account();
        account.setEmail("juanperez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(EnumRole.UNDERGRADUATE_STUDENT));

        UserRegisterDTO dto = new UserRegisterDTO("Student123?", user, account);

        UserRegisterRepository repo = new UserRegisterRepository(connection);

        assertDoesNotThrow(() -> repo.add(dto));
        assertTrue(account.getIdAccount() > 0);

        boolean userExist = existsInTable("SELECT 1 FROM User WHERE idUser = ?", account.getIdAccount());
        assertTrue(userExist, "El usuario debería estar en la BD");

        boolean roleExist = existsInTable(
                "SELECT 1 FROM Account_Role WHERE idAccount = ? AND idRole = ?",
                account.getIdAccount(), EnumRole.UNDERGRADUATE_STUDENT.getId()
        );
        assertTrue(roleExist, "El rol estudiante debería estar en la BD");
    }

    @Test
    void testAddCoordinator() throws Exception {
        User user = new User("Carlos", "Gómez", "987654321");
        Account account = new Account();
        account.setEmail("carlosgomez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(EnumRole.COORDINATOR));

        UserRegisterDTO dto = new UserRegisterDTO("Coord123?", user, account);

        UserRegisterRepository repo = new UserRegisterRepository(connection);

        assertDoesNotThrow(() -> repo.add(dto));
        assertTrue(account.getIdAccount() > 0);

        boolean roleExist = existsInTable(
                "SELECT 1 FROM Account_Role WHERE idAccount = ? AND idRole = ?",
                account.getIdAccount(), EnumRole.COORDINATOR.getId()
        );
        assertTrue(roleExist, "El rol Coordinador debería estar en la BD");
    }

    @Test
    void testAddDirector() throws Exception {
        User user = new User("Ana", "Martínez", "555123456");
        Account account = new Account();
        account.setEmail("anamartinez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(EnumRole.DIRECTOR));

        UserRegisterDTO dto = new UserRegisterDTO("Director123?", user, account);

        UserRegisterRepository repo = new UserRegisterRepository(connection);

        assertDoesNotThrow(() -> repo.add(dto));
        assertTrue(account.getIdAccount() > 0);

        boolean roleExist = existsInTable(
                "SELECT 1 FROM Account_Role WHERE idAccount = ? AND idRole = ?",
                account.getIdAccount(), EnumRole.DIRECTOR.getId()
        );
        assertTrue(roleExist, "El rol Director debería estar en la BD");
    }

    @Test
    void testAddJury() throws Exception {
        User user = new User("Luis", "Hernández", "777888999");
        Account account = new Account();
        account.setEmail("luishernandez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(EnumRole.JURY));

        UserRegisterDTO dto = new UserRegisterDTO("Jury123?", user, account);

        UserRegisterRepository repo = new UserRegisterRepository(connection);

        assertDoesNotThrow(() -> repo.add(dto));
        assertTrue(account.getIdAccount() > 0);

        boolean roleExist = existsInTable(
                "SELECT 1 FROM Account_Role WHERE idAccount = ? AND idRole = ?",
                account.getIdAccount(), EnumRole.JURY.getId()
        );
        assertTrue(roleExist, "El rol Jurado debería estar en la BD");
    }

    @Test
    void testAddAccountWithMultipleRoles() throws Exception {
        User user = new User("María", "Lopez", "111222333");
        Account account = new Account();
        account.setEmail("marialopez@unicauca.edu.co");
        account.setProgram(EnumProgram.ING_SISTEMAS);
        account.setRoles(List.of(
                EnumRole.COORDINATOR,
                EnumRole.DIRECTOR,
                EnumRole.JURY
        ));

        UserRegisterDTO dto = new UserRegisterDTO("MultiRole123?", user, account);

        UserRegisterRepository repo = new UserRegisterRepository(connection);

        assertDoesNotThrow(() -> repo.add(dto));
        assertTrue(account.getIdAccount() > 0);

        for (EnumRole role : account.getRoles()) {
            boolean roleExist = existsInTable(
                    "SELECT 1 FROM Account_Role WHERE idAccount = ? AND idRole = ?",
                    account.getIdAccount(), role.getId()
            );
            assertTrue(roleExist, "El rol " + role + " debería estar en la BD");
        }
    }
}
