
import App.Services.ValidatorService;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorServiceTest {

    private final ValidatorService validator = ValidatorService.GetInstance();

    @Test
    public void testPasswordValida() {
        try {
            validator.isValidPassword("Abcdef1!");
        } catch (Exception e) {
            fail("No debería lanzar excepción para contraseña válida.");
        }
    }

    @Test
    public void testPasswordInvalida() {
        try {
            validator.isValidPassword("abcdef"); // No tiene mayúscula, número ni símbolo
            fail("Debería lanzar excepción para contraseña inválida.");
        } catch (Exception e) {
            assertEquals("Contraseña no valida", e.getMessage());
        }
    }

    @Test
    public void testEmailValido() {
        try {
            validator.isValidEmail("juan.perez@unicauca.edu.co");
        } catch (Exception e) {
            fail("No debería lanzar excepción para correo válido.");
        }
    }

    @Test
    public void testEmailInvalido() {
        try {
            validator.isValidEmail("juan.perez@gmail.com");
            fail("Debería lanzar excepción para correo inválido.");
        } catch (Exception e) {
            assertEquals("Correo inválido, recuerde finalizar con @unicauca.edu.co", e.getMessage());
        }
    }

    @Test
    public void testTelefonoValido() {
        try {
            validator.isValidTelephone("3104567890");
        } catch (Exception e) {
            fail("No debería lanzar excepción para teléfono válido.");
        }
    }

    @Test
    public void testTelefonoInvalido() {
        try {
            validator.isValidTelephone("1234567890a");
            fail("Debería lanzar excepción para teléfono inválido.");
        } catch (Exception e) {
            assertEquals("Telefono no valido", e.getMessage());
        }
    }
}
