import ies.puerto.Ejercicio5;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio5Test {

    @Test
    void exploradoresJedi_tienenUnSoloGanador() {
        Ejercicio5 exploracion = new Ejercicio5();
        String salida = TestUtils.captureOutput(exploracion::iniciarBusqueda);

        assertTrue(exploracion.isPistaEncontrada(), "Debe hallarse una pista");
        assertNotNull(exploracion.getGanador(), "Debe registrarse un ganador");
        int ocurrencias = salida.split("halló una pista", -1).length - 1;
        assertEquals(1, ocurrencias, "Solo debe anunciarse una pista encontrada");
    }
}
