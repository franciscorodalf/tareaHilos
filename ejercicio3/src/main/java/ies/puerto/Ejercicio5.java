package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ejercicio5 {
    private final AtomicBoolean pistaEncontrada = new AtomicBoolean(false);
    private volatile String ganador;

    public void iniciarBusqueda() {
        Thread kenobi = new Thread(new Jedi("Kenobi", "Tatooine"));
        Thread skywalker = new Thread(new Jedi("Skywalker", "Dagobah"));

        kenobi.start();
        skywalker.start();

        try {
            kenobi.join();
            skywalker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class Jedi implements Runnable {
        private final String nombre;
        private final String planeta;

        private Jedi(String nombre, String planeta) {
            this.nombre = nombre;
            this.planeta = planeta;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(400, 1501));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (pistaEncontrada.compareAndSet(false, true)) {
                ganador = nombre;
                System.out.println(nombre + " halló una pista en " + planeta + ". Fin de búsqueda.");
            }
        }
    }

    public boolean isPistaEncontrada() {
        return pistaEncontrada.get();
    }

    public String getGanador() {
        return ganador;
    }
}
