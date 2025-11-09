package ies.puerto;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio3 {
    private final BlockingQueue<String> ensamblados = new LinkedBlockingQueue<>();
    private final int cantidadDroids;
    private final AtomicInteger activados = new AtomicInteger(0);

    public Ejercicio3() {
        this(10);
    }

    public Ejercicio3(int cantidadDroids) {
        this.cantidadDroids = cantidadDroids;
    }

    public void ejecutarFabrica() {
        Thread ensamblador = new Thread(new Ensamblador());
        Thread activador = new Thread(new Activador());

        ensamblador.start();
        activador.start();

        try {
            ensamblador.join();
            activador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class Ensamblador implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= cantidadDroids; i++) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100, 301));
                    String droid = "Droid-" + i;
                    System.out.println("Ensamblado " + droid);
                    ensamblados.put(droid);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private class Activador implements Runnable {
        @Override
        public void run() {
            int cuenta = 0;
            while (cuenta < cantidadDroids) {
                try {
                    String droid = ensamblados.take();
                    System.out.println("Activado " + droid);
                    activados.incrementAndGet();
                    cuenta++;
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 151));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public int getCantidadDroids() {
        return cantidadDroids;
    }

    public int getActivados() {
        return activados.get();
    }
}
