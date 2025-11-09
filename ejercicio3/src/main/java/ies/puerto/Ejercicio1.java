package ies.puerto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ejercicio1 {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition turnoCambio = lock.newCondition();
    private volatile boolean juegoTerminado;
    private int hpPikachu = 100;
    private int hpCharmander = 100;
    private String turno = "Pikachu";

    private void atacar(String atacante) {
        int danio = ThreadLocalRandom.current().nextInt(5, 21);
        if ("Pikachu".equals(atacante)) {
            hpCharmander -= danio;
            System.out.println("Pikachu ataca con " + danio + " de daño. HP rival: " + hpCharmander);
            if (hpCharmander <= 0 && !juegoTerminado) {
                juegoTerminado = true;
                System.out.println("Pikachu ha ganado la batalla!");
            }
        } else {
            hpPikachu -= danio;
            System.out.println("Charmander ataca con " + danio + " de daño. HP rival: " + hpPikachu);
            if (hpPikachu <= 0 && !juegoTerminado) {
                juegoTerminado = true;
                System.out.println("Charmander ha ganado la batalla!");
            }
        }
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void iniciarBatalla() {
        Thread hiloPikachu = new Thread(() -> {
            while (!juegoTerminado) {
                lock.lock();
                try {
                    while (!"Pikachu".equals(turno) && !juegoTerminado) {
                        turnoCambio.await();
                    }
                    if (juegoTerminado) {
                        return;
                    }
                    atacar("Pikachu");
                    turno = "Charmander";
                    turnoCambio.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread hiloCharmander = new Thread(() -> {
            while (!juegoTerminado) {
                lock.lock();
                try {
                    while (!"Charmander".equals(turno) && !juegoTerminado) {
                        turnoCambio.await();
                    }
                    if (juegoTerminado) {
                        return;
                    }
                    atacar("Charmander");
                    turno = "Pikachu";
                    turnoCambio.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
            }
        });

        hiloPikachu.start();
        hiloCharmander.start();

        try {
            hiloPikachu.join();
            hiloCharmander.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public int getHpPikachu() {
        return hpPikachu;
    }

    public int getHpCharmander() {
        return hpCharmander;
    }
}
