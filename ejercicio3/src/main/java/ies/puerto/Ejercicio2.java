package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ejercicio2 {

    public static void main(String[] args) {
        new CazaHorrocruxes().iniciarBusqueda();
    }

    public static class CazaHorrocruxes {
        private final AtomicBoolean horrocruxEncontrado = new AtomicBoolean(false);
        private volatile String ganador;

        private class Buscador implements Runnable {
            private final String nombre;
            private final String ubicacion;

            private Buscador(String nombre, String ubicacion) {
                this.nombre = nombre;
                this.ubicacion = ubicacion;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2001));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (horrocruxEncontrado.compareAndSet(false, true)) {
                    ganador = nombre;
                    System.out.println(
                            nombre + " encontró un Horrocrux en " + ubicacion + ". ¡Búsqueda terminada!");
                } else {
                    System.out.println(
                            nombre + " buscó en " + ubicacion + " pero " + ganador + " ya ganó la búsqueda.");
                }
            }
        }

        public void iniciarBusqueda() {
            Thread harry = new Thread(new Buscador("Harry", "Bosque Prohibido"));
            Thread hermione = new Thread(new Buscador("Hermione", "Biblioteca Antigua"));
            Thread ron = new Thread(new Buscador("Ron", "Mazmorras del castillo"));

            harry.start();
            hermione.start();
            ron.start();

            try {
                harry.join();
                hermione.join();
                ron.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (ganador == null) {
                System.out.println("Nadie encontró el Horrocrux. Revisa la búsqueda.");
            }
        }

        public boolean isHorrocruxEncontrado() {
            return horrocruxEncontrado.get();
        }

        public String getGanador() {
            return ganador;
        }
    }
}
