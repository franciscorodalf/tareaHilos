import ies.puerto.Ejercicio7;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio7Test {

    @Test
    void ciudadEnPeligro_unSoloHéroeNeutraliza() {
        Ejercicio7 simulacion = new Ejercicio7();
        String salida = TestUtils.captureOutput(simulacion::salvarCiudad);

        assertTrue(simulacion.isAmenazaNeutralizada(), "La amenaza debe neutralizarse");
        assertTrue(List.of("Superman", "Batman").contains(simulacion.getGanador()),
                "Ganador debe ser Superman o Batman");
        int ocurrencias = salida.split("Amenaza neutralizada", -1).length - 1;
        assertEquals(1, ocurrencias, "Solo debe anunciarse una neutralización");
    }
}
