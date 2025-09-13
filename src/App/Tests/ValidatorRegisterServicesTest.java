package Tests;

import Exceptions.RegisterUserFailerException;
import Models.Role;
import Services.ValidatorRegisterServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorRegisterServicesTest {

    private ValidatorRegisterServices validator;

    @BeforeEach
    void setUp() {
        validator = new ValidatorRegisterServices();
    }

    @Test
    void testValidPassword() {
        assertDoesNotThrow(() -> validator.isValidPassword("Abcde1!"));
    }

    @Test
    void testPasswordTooShort() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidPassword("Ab1!"));
        System.out.println(ex.getMessage());
    }

    @Test
    void testInvalidEmail() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidEmail("user123@unicauca.edu.co"));
        System.out.println(ex.getMessage());
    }

    @Test
    void testValidTelephone() {
        assertDoesNotThrow(() -> validator.isValidTelephone("3101234567"));
    }

    @Test
    void testInvalidTelephone() {
        assertThrows(RegisterUserFailerException.class, () -> validator.isValidTelephone("1234"));
    }

    @Test
    void testValidNames() {
        assertDoesNotThrow(() -> validator.isValidNames("Juan Sebastián"));
    }

    @Test
    void testNumbersInNames() {
        assertThrows(RegisterUserFailerException.class, () -> validator.isValidNames("Juan2"));
    }

    @Test
    void testValidateConfirmPassword() {
        assertDoesNotThrow(() -> validator.validateConfirmPassword("Abcde1!", "Abcde1!"));
    }

    @Test
    void testInvalidConfirmPassword() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.validateConfirmPassword("Abcde1!", "Xyz123!"));
    }

    @Test
    void testValidRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "Admin"));
        assertDoesNotThrow(() -> validator.isValidRole(roles));
    }

    @Test
    void testNoRoleSelected() {
        List<Role> roles = new ArrayList<>();
        assertThrows(RegisterUserFailerException.class, () -> validator.isValidRole(roles));
    }
}

