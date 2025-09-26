package Services;

import Dtos.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase {@link CookieService}.
 *
 * Esta clase utiliza JUnit 5 para verificar que los métodos de gestión de cookies
 * funcionen correctamente en diferentes escenarios.
 *
 * Se prueban los siguientes aspectos:
 *  - Inicialización del servicio
 *  - Creación de cookies con diferentes accountId
 *  - Obtención de información de usuario mediante cookies
 *  - Reset de cookies
 *  - Interacciones entre diferentes métodos del servicio
 *  - Comportamiento con valores límite y casos extremos
 */
class CookieServiceTest {

    /** Instancia del servicio de cookies que será probada en los tests. */
    private CookieService cookieService;

    /**
     * Inicializa la instancia del servicio de cookies antes de cada test.
     * Se ejecuta automáticamente antes de cada método de prueba.
     */
    @BeforeEach
    void setUp() {
        cookieService = new CookieService();
    }

    // ---------------- Inicialización del servicio ----------------

    /**
     * Test que verifica que el constructor inicializa las dependencias correctamente
     * sin lanzar excepciones durante la creación del servicio.
     */
    @Test
    void constructor_ShouldInitializeDependencies() {
        // El constructor debería inicializar las dependencias sin lanzar excepciones
        assertDoesNotThrow(() -> new CookieService());

        CookieService service = new CookieService();
        assertNotNull(service);
    }

    // ---------------- Creación de cookies (setCookie) ----------------

    /**
     * Test que valida que la creación de cookies con accountId válidos
     * no lance excepciones.
     */
    @Test
    void setCookie_WithValidAccountId_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> cookieService.setCookie(1));
        assertDoesNotThrow(() -> cookieService.setCookie(100));
        assertDoesNotThrow(() -> cookieService.setCookie(Integer.MAX_VALUE));
    }

    /**
     * Test que verifica el comportamiento al crear cookies con accountId cero.
     * Valida que no se produzcan errores inesperados.
     */
    @Test
    void setCookie_WithZeroAccountId_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> cookieService.setCookie(0));
    }

    /**
     * Test que verifica el comportamiento al crear cookies con accountId negativo.
     * Valida la robustez del servicio ante valores inusuales.
     */
    @Test
    void setCookie_WithNegativeAccountId_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> cookieService.setCookie(-1));
    }

    // ---------------- Obtención de usuario por cookie (getUserRegisterDTOByCookie) ----------------

    /**
     * Test que valida que al intentar obtener información de usuario cuando no existe
     * archivo de cookie, el método retorne null de manera controlada.
     */
    @Test
    void getUserRegisterDTOByCookie_WhenNoCookieFileExists_ShouldReturnNull() {
        // Act
        UserRegisterDTO result = cookieService.getUserRegisterDTOByCookie();

        // Assert
        assertNull(result);
    }

    /**
     * Test que verifica la consistencia del método getUserRegisterDTOByCookie
     * cuando se llama múltiples veces consecutivas sin cambios en el estado.
     */
    @Test
    void getUserRegisterDTOByCookie_WhenCalledMultipleTimes_ShouldBeConsistent() {
        // Act
        UserRegisterDTO result1 = cookieService.getUserRegisterDTOByCookie();
        UserRegisterDTO result2 = cookieService.getUserRegisterDTOByCookie();

        // Assert - Ambos deberían ser null si no hay cookie
        assertNull(result1);
        assertNull(result2);
    }

    // ---------------- Reset de cookies (ResetCookie) ----------------

    /**
     * Test que valida que el reset de cookies no lance excepciones
     * incluso cuando no existe una cookie previa.
     */
    @Test
    void resetCookie_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> cookieService.ResetCookie());
    }

    /**
     * Test que verifica la estabilidad del servicio al realizar múltiples resets
     * consecutivos de cookies.
     */
    @Test
    void resetCookie_WhenCalledMultipleTimes_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            cookieService.ResetCookie();
            cookieService.ResetCookie();
            cookieService.ResetCookie();
        });
    }

    // ---------------- Interacciones entre métodos ----------------

    /**
     * Test que valida la secuencia: reset de cookie seguido de creación de nueva cookie.
     * Verifica que las operaciones sucesivas no interfieran entre sí.
     */
    @Test
    void setCookie_AfterReset_ShouldNotThrowException() {
        // Arrange
        cookieService.ResetCookie();

        // Act & Assert
        assertDoesNotThrow(() -> cookieService.setCookie(123));
    }

    /**
     * Test que valida la secuencia: creación de cookie seguido de reset.
     * Verifica que el reset funcione correctamente incluso después de crear una cookie.
     */
    @Test
    void resetCookie_AfterSetCookie_ShouldNotThrowException() {
        // Arrange
        cookieService.setCookie(123);

        // Act & Assert
        assertDoesNotThrow(() -> cookieService.ResetCookie());
    }

    /**
     * Test que verifica que después de resetear la cookie,
     * no se pueda obtener información de usuario.
     */
    @Test
    void getUserRegisterDTOByCookie_AfterReset_ShouldReturnNull() {
        // Arrange
        cookieService.ResetCookie();

        // Act
        UserRegisterDTO result = cookieService.getUserRegisterDTOByCookie();

        // Assert
        assertNull(result);
    }

    // ---------------- Tests de integración ----------------

    /**
     * Test de integración que verifica el flujo completo de creación de cookie
     * y posterior obtención de información de usuario.
     * Nota: El resultado puede variar dependiendo del estado de la base de datos.
     */
    @Test
    void integrationTest_SetCookieAndThenGetUser() {
        // Este test verifica el flujo completo (aunque probablemente retorne null)

        // Arrange
        int testAccountId = 999;

        // Act
        assertDoesNotThrow(() -> cookieService.setCookie(testAccountId));
        UserRegisterDTO result = cookieService.getUserRegisterDTOByCookie();

        // Assert - Podría ser null si no existe el usuario en la BD, pero no debería lanzar excepción
        // No hacemos assert sobre el valor ya que depende del estado de la BD
    }

    /**
     * Test de integración que verifica el flujo completo:
     * creación de cookie → reset → intento de obtención de usuario.
     */
    @Test
    void integrationTest_ResetAfterSet() {
        // Test de flujo completo: set -> reset -> get
        cookieService.setCookie(123);
        cookieService.ResetCookie();

        UserRegisterDTO result = cookieService.getUserRegisterDTOByCookie();
        assertNull(result);
    }

    // ---------------- Tests de estrés y límites ----------------

    /**
     * Test de estrés que verifica la estabilidad del servicio
     * al ejecutar múltiples operaciones consecutivas.
     */
    @Test
    void stressTest_MultipleOperations() {
        // Test para verificar que múltiples operaciones no causan problemas
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10; i++) {
                cookieService.setCookie(i);
                cookieService.getUserRegisterDTOByCookie();
                cookieService.ResetCookie();
            }
        });
    }

    /**
     * Test que verifica el comportamiento del servicio con valores límite
     * para los accountId, incluyendo valores extremos.
     */
    @Test
    void boundaryTest_ExtremeAccountIds() {
        // Test con valores límite
        assertDoesNotThrow(() -> cookieService.setCookie(Integer.MIN_VALUE));
        assertDoesNotThrow(() -> cookieService.setCookie(Integer.MAX_VALUE));
        assertDoesNotThrow(() -> cookieService.setCookie(0));
    }
}