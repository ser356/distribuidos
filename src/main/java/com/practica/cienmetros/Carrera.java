package com.practica.cienmetros;

public class Carrera {
    private final Atleta[] atletas;

    public Carrera(Atleta[] atletas) {
        this.atletas = atletas;
    }

    public void pistoletazo() throws InterruptedException {
        for (Atleta atleta : atletas) {
            atleta.start();
        }

        Thread.sleep(1000);
        System.out.println("Preparados");

        Thread.sleep(1000);
        System.out.println("Listos");

        Thread.sleep(1000);

        System.out.println("¡Ya!");

        for (Atleta atleta : atletas) {
            atleta.empezar();
        }

        for (Atleta atleta : atletas) {
            atleta.doCarrera(); 

            if (!atleta.isAlive()) {
                System.out.println(atleta.getDorsal() + " ha terminado la carrera");
            }
            
           
        }


    }

    
}

class Atleta extends Thread {
    private final Object lock;
    private int dorsal;


    public Atleta(Object lock, int dorsal) {
        this.lock = lock;
        this.dorsal = dorsal;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println(getName() + " en la línea de salida");
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }

    }

    public void empezar() {
        try {
            System.out.println(getName() + " comienza a correr");
            synchronized (lock) {
                lock.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void doCarrera() {

        try {
            sleep((long) (Math.random() * 2000 + 9000));
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}