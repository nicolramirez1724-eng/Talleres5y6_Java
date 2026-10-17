package com.taller.poo;

public class PruebaEstudiante {
    public static void main(String[] args) {
        Estudiante estudiante1 = new Estudiante("2024001", "Laura Ramírez", 4.35, true);

        String reporte = estudiante1.obtenerReporteAcademicos();
        System.out.println(reporte);
    }
}
