import ies.puerto.Ejercicio8;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ejercicio8Test {

    @Test
    void fuerzaThorHulk_finalizaPorTiempo() {
        Ejercicio8 competencia = new Ejercicio8(800);
        String salida = TestUtils.captureOutput(competencia::competencia);

        assertTrue(salida.contains("¡Tiempo!"), "Debe anunciarse el final del tiempo");
        assertTrue(salida.contains("gana") || salida.contains("Empate"),
                "Debe declararse un ganador o empate");
        assertTrue(competencia.getTotalThor() > 0 || competencia.getTotalHulk() > 0,
                "Alguno debe haber acumulado peso");
    }
}
