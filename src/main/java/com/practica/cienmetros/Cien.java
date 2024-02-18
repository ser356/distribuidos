package com.practica.cienmetros;

public class Cien extends Carrera {
    
    // Constructor que simplemente llama al constructor de la superclase
    public Cien(Atleta[] atletas) {
        super(atletas);
    }

    // Puede incluir métodos específicos de la carrera de 100 metros si es necesario
    
    public static void main(String[] args) {
        // Crear un lock compartido para todos los atletas
        Object lock = new Object();

        // Crear atletas
        Atleta[] atletas = new Atleta[8];

        for (int i = 0; i < atletas.length; i++) {
            atletas[i] = new Atleta(lock, i + 1);
        }

        // Crear una instancia de la carrera de 100 metros
        Cien carreraCien = new Cien(atletas);

        try {
            carreraCien.pistoletazo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
