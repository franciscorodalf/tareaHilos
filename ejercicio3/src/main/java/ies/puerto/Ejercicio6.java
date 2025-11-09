package ies.puerto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ejercicio6 {
    private final AtomicBoolean destinoAlcanzado = new AtomicBoolean(false);
    private volatile String eraGanadora;
    private final List<String> eras = List.of("Roma Antigua", "Futuro Lejano", "Era Victoriana", "Año 3000");

    public void viajar() {
        List<Thread> hilos = new ArrayList<>();
        for (String era : eras) {
            Thread hilo = new Thread(new Viaje(era));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private class Viaje implements Runnable {
        private final String era;

        private Viaje(String era) {
            this.era = era;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (destinoAlcanzado.compareAndSet(false, true)) {
                eraGanadora = era;
                System.out.println("La TARDIS llegó primero a " + era);
            }
        }
    }

    public boolean isDestinoAlcanzado() {
        return destinoAlcanzado.get();
    }

    public String getEraGanadora() {
        return eraGanadora;
    }
}
