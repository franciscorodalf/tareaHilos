import ies.puerto.Ejercicio2;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio2Test {

    @Test
    void cazaHorrocruxes_unSoloHallazgo() {
        Ejercicio2.CazaHorrocruxes juego = new Ejercicio2.CazaHorrocruxes();
        String salida = TestUtils.captureOutput(juego::iniciarBusqueda);

        assertTrue(juego.isHorrocruxEncontrado(), "Debe encontrarse un Horrocrux");
        assertTrue(List.of("Harry", "Hermione", "Ron").contains(juego.getGanador()),
                "Ganador debe ser uno de los participantes");
        int ocurrencias = salida.split("encontró un Horrocrux", -1).length - 1;
        assertEquals(1, ocurrencias, "Solo debe anunciarse un hallazgo");
    }
}
