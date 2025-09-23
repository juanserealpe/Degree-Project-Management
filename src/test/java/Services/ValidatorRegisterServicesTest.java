package Services;

import Enums.EnumRole;
import Exceptions.RegisterUserFailerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase {@link ValidatorRegisterServices}.
 *
 * Esta clase utiliza JUnit 5 para verificar que los métodos de validación
 * del servicio funcionen correctamente tanto para casos válidos como inválidos.
 *
 * Se prueban los siguientes aspectos:
 *  - Contraseña (password)
 *  - Email
 *  - Teléfono
 *  - Nombres
 *  - Apellidos
 *  - Confirmación de contraseña
 *  - Roles asignados al usuario
 */
class ValidatorRegisterServicesTest {

    /** Instancia del validador que será probada en los tests. */
    private ValidatorRegisterServices validator;

    /**
     * Inicializa la instancia del validador antes de cada test.
     */
    @BeforeEach
    void setUp() {
        validator = new ValidatorRegisterServices();
    }

    // ---------------- Contraseña ----------------

    /**
     * Test que valida que una contraseña correcta no lance excepción.
     */
    @Test
    void testValidPassword_Success() {
        assertDoesNotThrow(() -> validator.isValidPassword("Abcdef1!"));
    }

    /**
     * Test que valida que una contraseña demasiado corta lance la excepción correspondiente.
     */
    @Test
    void testValidPassword_TooShort() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidPassword("Ab1!"));
        assertEquals("Debe tener al menos 6 caracteres.", ex.getMessage());
    }

    /**
     * Test que valida que una contraseña sin mayúsculas lance la excepción correspondiente.
     */
    @Test
    void testValidPassword_NoUppercase() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidPassword("abcdef1!"));
        assertEquals("Debe contener al menos una letra mayúscula.", ex.getMessage());
    }

    /**
     * Test que valida que una contraseña sin números lance la excepción correspondiente.
     */
    @Test
    void testValidPassword_NoNumber() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidPassword("Abcdef!@"));
        assertEquals("Debe contener al menos un número.", ex.getMessage());
    }

    /**
     * Test que valida que una contraseña sin caracteres especiales lance la excepción correspondiente.
     */
    @Test
    void testValidPassword_NoSpecialChar() {
        RegisterUserFailerException ex = assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidPassword("Abcdef1"));
        assertEquals("Debe contener al menos un carácter especial.", ex.getMessage());
    }

    // ---------------- Email ----------------

    /**
     * Test que valida que un email correcto no lance excepción.
     */
    @Test
    void testValidEmail_Success() {
        assertDoesNotThrow(() -> validator.isValidEmail("juan@unicauca.edu.co"));
    }

    /**
     * Test que valida que un email con dominio incorrecto lance excepción.
     */
    @Test
    void testValidEmail_InvalidDomain() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidEmail("juan@gmail.com"));
    }

    /**
     * Test que valida que un email con números lance excepción.
     */
    @Test
    void testValidEmail_WithNumbers() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidEmail("juan1@unicauca.edu.co"));
    }

    /**
     * Test que valida que un email con caracteres especiales lance excepción.
     */
    @Test
    void testValidEmail_SpecialChars() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidEmail("ju!an@unicauca.edu.co"));
    }

    // ---------------- Teléfono ----------------

    /**
     * Test que valida un número de teléfono válido.
     */
    @Test
    void testValidTelephone_Success() {
        assertDoesNotThrow(() -> validator.isValidTelephone("1234567890"));
    }

    /**
     * Test que valida que un teléfono vacío no lance excepción (no obligatorio).
     */
    @Test
    void testValidTelephone_Empty() {
        assertDoesNotThrow(() -> validator.isValidTelephone(""));
    }

    /**
     * Test que valida que un teléfono inválido lance excepción.
     */
    @Test
    void testValidTelephone_Invalid() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidTelephone("12345"));
    }

    // ---------------- Nombres ----------------

    /**
     * Test que valida un nombre correcto.
     */
    @Test
    void testValidNames_Success() {
        assertDoesNotThrow(() -> validator.isValidNames("Juan"));
    }

    /**
     * Test que valida que un nombre con números lance excepción.
     */
    @Test
    void testValidNames_WithNumbers() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidNames("Juan1"));
    }

    /**
     * Test que valida que un nombre con caracteres especiales lance excepción.
     */
    @Test
    void testValidNames_SpecialChars() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidNames("Ju@n"));
    }

    /**
     * Test que valida que un nombre demasiado largo lance excepción.
     */
    @Test
    void testValidNames_TooLong() {
        String longName = "a".repeat(51);
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidNames(longName));
    }

    // ---------------- Apellidos ----------------

    /**
     * Test que valida un apellido correcto.
     */
    @Test
    void testValidLastNames_Success() {
        assertDoesNotThrow(() -> validator.isValidLastNames("Varona"));
    }

    /**
     * Test que valida que un apellido con números lance excepción.
     */
    @Test
    void testValidLastNames_WithNumbers() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidLastNames("Varon4"));
    }

    /**
     * Test que valida que un apellido con caracteres especiales lance excepción.
     */
    @Test
    void testValidLastNames_SpecialChars() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidLastNames("Var@na"));
    }

    /**
     * Test que valida que un apellido demasiado largo lance excepción.
     */
    @Test
    void testValidLastNames_TooLong() {
        String longLastName = "a".repeat(31);
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidLastNames(longLastName));
    }

    // ---------------- Confirmación de contraseña ----------------

    /**
     * Test que valida que la confirmación de contraseña correcta no lance excepción.
     */
    @Test
    void testValidateConfirmPassword_Success() {
        assertDoesNotThrow(() -> validator.validateConfirmPassword("Abcdef1!", "Abcdef1!"));
    }

    /**
     * Test que valida que una confirmación de contraseña incorrecta lance excepción.
     */
    @Test
    void testValidateConfirmPassword_Fail() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.validateConfirmPassword("Abcdef1!", "abcdef1!"));
    }

    // ---------------- Roles ----------------

    /**
     * Test que valida que asignar un rol válido no lance excepción.
     */
    @Test
    void testValidRole_Success() {
        assertDoesNotThrow(() -> validator.isValidRole(List.of(EnumRole.UNDERGRADUATE_STUDENT)));
    }

    /**
     * Test que valida que no asignar roles (lista vacía) lance excepción.
     */
    @Test
    void testValidRole_Fail() {
        assertThrows(RegisterUserFailerException.class,
                () -> validator.isValidRole(List.of()));
    }
}
