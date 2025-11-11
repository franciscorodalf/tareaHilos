package ies.puerto;

public class Impresora {

    private boolean turnoA = true;
    private static int copiasA = 1;
    private static int copiasB = 1;

    public void imprimir(String nombreEmpleado) {
        if (nombreEmpleado.equals("A")) {
            synchronized (this) {
                while (!turnoA) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Copias Empleado A: " + copiasA++);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turnoA = false;
                notify();
            }
        } else {
            synchronized (this) {
                while (turnoA) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Copias Empleado B: " + copiasB++);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turnoA = true;
                notify();
            }
        }
    }

    public static void main(String[] args) {

        Impresora impresora = new Impresora();

        Thread empleadoA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    impresora.imprimir("A");
                }
            }
        });

        Thread empleadoB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    impresora.imprimir("B");
                }
            }
        });

        empleadoA.start();
        empleadoB.start();

    }
}