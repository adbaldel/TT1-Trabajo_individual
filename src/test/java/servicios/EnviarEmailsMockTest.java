package servicios;

import com.tt1.mocks.EnviarEmailsMock;
import modelo.Destinatario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class EnviarEmailsMockTest
{
    private EnviarEmailsMock enviarEmailsMock;

    // --- Arrange Before/After each test -------------------------------------------------------------------

    @BeforeEach
    void setUp()
    {
        enviarEmailsMock = new EnviarEmailsMock(LoggerFactory.getLogger("EnviarEmailsTest"));
    }

    @AfterEach
    void tearDown()
    {
        enviarEmailsMock = null;
    }

    // --- --------------- -------------------------------------------------------------------
    // --- TESTS UNITARIOS -------------------------------------------------------------------
    // --- --------------- -------------------------------------------------------------------

    // --- Test enviarEmail -------------------------------------------------------------------

    @Test
    void enviarEmailExitoso()
    {
        Destinatario dest = new Destinatario();
        String email = "Hello World!";
        boolean enviado;

        enviado = enviarEmailsMock.enviarEmail(dest, email);

        assertTrue(enviado);
    }
}