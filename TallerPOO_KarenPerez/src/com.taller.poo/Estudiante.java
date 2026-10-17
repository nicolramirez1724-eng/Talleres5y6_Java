package com.taller.poo;

public class Estudiante {
    private String identificacion;
    private String nombreCompleto;
    private double promedioCalificaciones;
    private boolean estaMatriculado;

    public Estudiante() {
        this.identificacion = "Sin Identificación";
        this.nombreCompleto = "Sin Nombre";
        this.promedioCalificaciones = 0.0;
        this.estaMatriculado = false;
    }

    public Estudiante(String identificacion, String nombreCompleto, double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    public String obtenerReporteAcademicos() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Identificación: %s%n", identificacion));
        sb.append(String.format("Estudiante: %s%n", nombreCompleto));
        sb.append(String.format("Promedio: %.2f%n", promedioCalificaciones));
        sb.append(String.format("Matriculado: %b%n", estaMatriculado));
        return sb.toString();
    }
}
