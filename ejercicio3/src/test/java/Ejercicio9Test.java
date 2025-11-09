import ies.puerto.Ejercicio9;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio9Test {

    @Test
    void milleniumFalcon_termineConEscapeODestruccion() {
        Ejercicio9 mision = new Ejercicio9(1000);
        String salida = TestUtils.captureOutput(mision::iniciarMision);

        boolean destruccion = salida.contains("se destruye");
        boolean escape = salida.contains("escapan");
        assertTrue(destruccion ^ escape, "Debe haber destrucción o escape, pero no ambos");
        assertTrue(mision.isFin(), "La misión debe marcarse como finalizada");
    }
}
