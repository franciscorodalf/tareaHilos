package ies.puerto;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ejercicio7 {
    private final AtomicBoolean amenazaNeutralizada = new AtomicBoolean(false);
    private volatile String ganador;
    private final List<String> zonasSuperman = List.of("Norte", "Centro", "Este");
    private final List<String> zonasBatman = List.of("Oeste", "Sur");

    public void salvarCiudad() {
        Thread superman = new Thread(() -> protegerZonas("Superman", zonasSuperman, 200, 500));
        Thread batman = new Thread(() -> protegerZonas("Batman", zonasBatman, 300, 600));

        superman.start();
        batman.start();

        try {
            superman.join();
            batman.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void protegerZonas(String heroe, List<String> zonas, int minSleep, int maxSleep) {
        for (String zona : zonas) {
            if (amenazaNeutralizada.get()) {
                break;
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(minSleep, maxSleep + 1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println(heroe + " salvó " + zona);
        }

        if (amenazaNeutralizada.compareAndSet(false, true)) {
            ganador = heroe;
            System.out.println("Amenaza neutralizada por " + heroe);
        }
    }

    public boolean isAmenazaNeutralizada() {
        return amenazaNeutralizada.get();
    }

    public String getGanador() {
        return ganador;
    }
}
