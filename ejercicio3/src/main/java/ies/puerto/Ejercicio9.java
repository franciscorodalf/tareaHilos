package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio9 {
    private final AtomicBoolean fin = new AtomicBoolean(false);
    private final AtomicBoolean destruida = new AtomicBoolean(false);
    private final AtomicInteger velocidad = new AtomicInteger(0);
    private final AtomicInteger escudos = new AtomicInteger(100);
    private final long tiempoMisionMs;
    private volatile long inicio;

    public Ejercicio9() {
        this(4000);
    }

    public Ejercicio9(long tiempoMisionMs) {
        this.tiempoMisionMs = tiempoMisionMs;
    }

    public void iniciarMision() {
        inicio = System.currentTimeMillis();
        Thread hanSolo = new Thread(this::hanSolo);
        Thread chewbacca = new Thread(this::chewbacca);

        hanSolo.start();
        chewbacca.start();

        try {
            hanSolo.join();
            chewbacca.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!destruida.get()) {
            System.out.println(
                    "¡Han y Chewie escapan! Vel=" + velocidad.get() + ", Escudos=" + escudos.get());
        }
    }

    private void hanSolo() {
        while (!fin.get()) {
            velocidad.addAndGet(ThreadLocalRandom.current().nextInt(5, 16));
            if (ThreadLocalRandom.current().nextInt(1, 101) <= 5) {
                if (destruida.compareAndSet(false, true)) {
                    fin.set(true);
                    System.out.println("Fallo de hiperimpulsor. ¡La nave se destruye!");
                }
                break;
            }
            dormir(150);
            verificarTiempo();
        }
    }

    private void chewbacca() {
        while (!fin.get()) {
            escudos.addAndGet(ThreadLocalRandom.current().nextInt(-10, 6));
            if (escudos.get() <= 0) {
                if (destruida.compareAndSet(false, true)) {
                    fin.set(true);
                    System.out.println("¡Escudos agotados! La nave se destruye!");
                }
                break;
            }
            dormir(150);
            verificarTiempo();
        }
    }

    private void verificarTiempo() {
        if (!fin.get() && System.currentTimeMillis() - inicio >= tiempoMisionMs) {
            fin.set(true);
        }
    }

    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isFin() {
        return fin.get();
    }

    public boolean isDestruida() {
        return destruida.get();
    }

    public int getVelocidad() {
        return velocidad.get();
    }

    public int getEscudos() {
        return escudos.get();
    }
}
