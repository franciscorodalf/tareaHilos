import ies.puerto.Ejercicio1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio1Test {

    @Test
    void batallaPokemon_debeTenerGanador() {
        Ejercicio1 batalla = new Ejercicio1();
        String salida = TestUtils.captureOutput(batalla::iniciarBatalla);

        assertTrue(salida.contains("ha ganado la batalla!"), "Debe anunciarse un ganador");
        assertTrue(batalla.isJuegoTerminado(), "El juego debe marcarse como terminado");
        assertTrue(batalla.getHpPikachu() <= 0 || batalla.getHpCharmander() <= 0,
                "Uno de los Pokémon debe haber llegado a 0 HP");
    }
}
