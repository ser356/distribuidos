package com.practica.carrera;

import java.util.Random;

public class Carrera {
    public static Object testigo = new Object();
    public static int turnoActual = 0;
    public static Atleta[] atletas;

    public static void main(String[] args) {
        atletas = new Atleta[4];
        for (int i = 0; i < atletas.length; i++) {
            atletas[i] = new Atleta(i);
        }

        iniciarCarrera(atletas, testigo);
    }

    public static void iniciarCarrera(Atleta[] atletas, Object testigo) {
        for (int i = 0; i < atletas.length; i++) {
            new Thread(atletas[i], String.valueOf(i)).start();
        }
    }

    public static void pasarTestigo() {
        synchronized (testigo) {
            turnoActual++;
            testigo.notifyAll();
        }
    }
}

class Atleta implements Runnable {
    private static Random rand = new Random();
    private int numeroAtleta;
    private double timer = 0;

    public Atleta(int numero) {
        this.numeroAtleta = numero;
    }

    @Override
    public void run() {
        synchronized (Carrera.testigo) {
            while (Carrera.turnoActual != numeroAtleta) {
                try {
                    Carrera.testigo.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            try {
                timer = System.currentTimeMillis();
                Thread.sleep((9 + rand.nextInt(3)) * 1000);
                timer = (System.currentTimeMillis() - timer) / 1000;
                System.out.println(
                        "Atleta " + Thread.currentThread().getName() + " ha terminado en " + timer + " segundos");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Carrera.pasarTestigo();
        }
    }
}
