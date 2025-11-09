package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Ejercicio4 {
    private final AtomicBoolean snitchAtrapada = new AtomicBoolean(false);
    private final ReentrantLock marcadorLock = new ReentrantLock();
    private int puntosEquipoA;
    private int puntosEquipoB;

    public void disputarPartido() {
        Thread cazadorA = new Thread(new Cazador("A"));
        Thread cazadorB = new Thread(new Cazador("B"));
        Thread buscador = new Thread(new Buscador());

        cazadorA.start();
        cazadorB.start();
        buscador.start();

        try {
            cazadorA.join();
            cazadorB.join();
            buscador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Marcador final: A=" + puntosEquipoA + " B=" + puntosEquipoB);
    }

    private class Cazador implements Runnable {
        private final String equipo;

        private Cazador(String equipo) {
            this.equipo = equipo;
        }

        @Override
        public void run() {
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(200, 501));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (ThreadLocalRandom.current().nextInt(0, 2) == 1 && !snitchAtrapada.get()) {
                    marcadorLock.lock();
                    try {
                        if ("A".equals(equipo)) {
                            puntosEquipoA += 10;
                            System.out.println("Equipo A anota 10. Total A=" + puntosEquipoA);
                        } else {
                            puntosEquipoB += 10;
                            System.out.println("Equipo B anota 10. Total B=" + puntosEquipoB);
                        }
                    } finally {
                        marcadorLock.unlock();
                    }
                }
            }
        }
    }

    private class Buscador implements Runnable {
        @Override
        public void run() {
            long inicio = System.currentTimeMillis();
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(300, 701));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                int probabilidad = ThreadLocalRandom.current().nextInt(1, 101);
                if (probabilidad <= 15 || System.currentTimeMillis() - inicio >= 5000) {
                    if (snitchAtrapada.compareAndSet(false, true)) {
                        System.out.println("¡Snitch dorada atrapada!");
                    }
                }
            }
        }
    }

    public boolean isSnitchAtrapada() {
        return snitchAtrapada.get();
    }

    public int getPuntosEquipoA() {
        return puntosEquipoA;
    }

    public int getPuntosEquipoB() {
        return puntosEquipoB;
    }
}
