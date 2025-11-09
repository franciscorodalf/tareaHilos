import ies.puerto.Ejercicio3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio3Test {

    @Test
    void fabricaDroids_registraOrdenYConteo() {
        Ejercicio3 fabrica = new Ejercicio3(5);
        String salida = TestUtils.captureOutput(fabrica::ejecutarFabrica);

        for (int k = 1; k <= fabrica.getCantidadDroids(); k++) {
            String ensamblado = "Ensamblado Droid-" + k;
            String activado = "Activado Droid-" + k;
            int idxE = salida.indexOf(ensamblado);
            int idxA = salida.indexOf(activado);
            assertTrue(idxE >= 0, "Debe registrarse ensamblaje para Droid-" + k);
            assertTrue(idxA >= 0, "Debe registrarse activación para Droid-" + k);
            assertTrue(idxE < idxA, "El ensamblaje debe ocurrir antes de la activación para Droid-" + k);
        }
        assertEquals(fabrica.getCantidadDroids(), fabrica.getActivados(), "Todos los droids deben activarse");
    }
}
