import ies.puerto.Ejercicio6;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio6Test {

    @Test
    void tardis_registraEraGanadora() {
        Ejercicio6 tardis = new Ejercicio6();
        String salida = TestUtils.captureOutput(tardis::viajar);

        assertTrue(tardis.isDestinoAlcanzado(), "Debe alcanzarse un destino");
        assertNotNull(tardis.getEraGanadora(), "Debe registrarse la era ganadora");
        int ocurrencias = salida.split("llegó primero", -1).length - 1;
        assertTrue(ocurrencias == 1, "Solo una era debe registrarse como ganadora");
    }
}
