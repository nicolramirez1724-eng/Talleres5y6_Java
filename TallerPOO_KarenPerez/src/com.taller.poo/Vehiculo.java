package com.taller.poo;

/*
 * Si se usan Wrappers (Double, Integer, Boolean) en lugar de primitivos, cada valor es un
 * objeto en el heap y ocupa mas memoria, porque el atributo solo guarda una referencia.
 * Ademas los Wrappers pueden valer null, mientras que los primitivos siempre tienen un valor
 * (0.0, 0, false). En esta clase todos los datos son obligatorios, por eso se usan primitivos.
 */
public class Vehiculo {
    private String marca;
    private String modelo;
    // double primitivo: el kilometraje siempre tiene valor y no necesita ser null
    private double kilometraje;
    // int primitivo: la velocidad maxima es un entero obligatorio, ocupa menos que Integer
    private int velocidadMaxima;
    // boolean primitivo: el motor solo esta encendido o apagado, no hace falta un tercer estado null
    private boolean encendido;

    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }
}
