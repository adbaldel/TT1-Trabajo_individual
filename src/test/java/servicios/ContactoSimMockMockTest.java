package servicios;

import com.tt1.mocks.ContactoSimMock;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactoSimMockMockTest
{
    private ContactoSimMock contactoSimMock;

    // --- Arrange Before/After each test -------------------------------------------------------------------

    @BeforeEach
    void setUp()
    {
        contactoSimMock = new ContactoSimMock();
    }

    @AfterEach
    void tearDown()
    {
        contactoSimMock = null;
    }

    // --- --------------- -------------------------------------------------------------------
    // --- TESTS UNITARIOS -------------------------------------------------------------------
    // --- --------------- -------------------------------------------------------------------

    // --- Test solicitarSimulation -------------------------------------------------------------------

    @Test
    void solicitarSimulation()
    {
        Map<Integer, Integer> nums = new HashMap<>(); // Datos para los que se sabría el resultado
        nums.put(1, 2);
        nums.put(2, 3);
        DatosSolicitud sol = new DatosSolicitud(nums);
        int tok;

        tok = contactoSimMock.solicitarSimulation(sol);

        assertTrue(tok >= 0);
    }

    // --- Test descargarDatos -------------------------------------------------------------------

    @Test
    void descargarDatos()
    {
        int tok = 1;

        DatosSimulation sim;

        sim = contactoSimMock.descargarDatos(tok);

        assertNotNull(sim);
    }

    // --- Test getEntities -------------------------------------------------------------------

    @Test
    void getEntities()
    {
        List<Entidad> entities;

        entities = contactoSimMock.getEntities();

        assertEquals(2, entities.size());
    }

    // --- Test isValidEntityId -------------------------------------------------------------------

    @Test
    void isValidEntityId()
    {
        boolean valid1, valid2, valid0, valid3;

        valid1 = contactoSimMock.isValidEntityId(1);
        valid2 = contactoSimMock.isValidEntityId(2);
        valid0 = contactoSimMock.isValidEntityId(0);
        valid3 = contactoSimMock.isValidEntityId(3);

        assertTrue(valid1);
        assertTrue(valid2);
        assertFalse(valid0);
        assertFalse(valid3);
    }
}