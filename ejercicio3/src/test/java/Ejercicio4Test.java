import ies.puerto.Ejercicio4;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio4Test {

    @Test
    void quidditch_finalizaConSnitchAtrapada() {
        Ejercicio4 quidditch = new Ejercicio4();
        String salida = TestUtils.captureOutput(quidditch::disputarPartido);

        assertTrue(quidditch.isSnitchAtrapada(), "La snitch debe atraparse");
        assertTrue(salida.contains("¡Snitch dorada atrapada!"), "Debe anunciarse la captura de la snitch");
        assertTrue(salida.contains("Marcador final:"), "Debe imprimirse el marcador final");
    }
}
