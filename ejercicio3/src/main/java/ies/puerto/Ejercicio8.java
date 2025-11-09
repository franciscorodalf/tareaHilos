package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio8 {
    private final long durationMs;
    private final AtomicBoolean tiempoTerminado = new AtomicBoolean(false);
    private final AtomicInteger totalThor = new AtomicInteger(0);
    private final AtomicInteger totalHulk = new AtomicInteger(0);

    public Ejercicio8() {
        this(5000);
    }

    public Ejercicio8(long durationMs) {
        this.durationMs = durationMs;
    }

    public void competencia() {
        Thread temporizador = new Thread(new Temporizador());
        Thread thor = new Thread(new Levantador("Thor", totalThor));
        Thread hulk = new Thread(new Levantador("Hulk", totalHulk));

        temporizador.start();
        thor.start();
        hulk.start();

        try {
            temporizador.join();
            thor.join();
            hulk.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int thorTotal = totalThor.get();
        int hulkTotal = totalHulk.get();
        if (thorTotal > hulkTotal) {
            System.out.println("Thor gana con " + thorTotal + " vs " + hulkTotal);
        } else if (hulkTotal > thorTotal) {
            System.out.println("Hulk gana con " + hulkTotal + " vs " + thorTotal);
        } else {
            System.out.println("Empate: " + thorTotal);
        }
    }

    private class Temporizador implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(durationMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                tiempoTerminado.set(true);
                System.out.println("¡Tiempo!");
            }
        }
    }

    private class Levantador implements Runnable {
        private final String nombre;
        private final AtomicInteger total;

        private Levantador(String nombre, AtomicInteger total) {
            this.nombre = nombre;
            this.total = total;
        }

        @Override
        public void run() {
            while (!tiempoTerminado.get()) {
                total.addAndGet(ThreadLocalRandom.current().nextInt(5, 21));
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 121));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println(nombre + " total: " + total.get());
        }
    }

    public int getTotalThor() {
        return totalThor.get();
    }

    public int getTotalHulk() {
        return totalHulk.get();
    }
}
