package com.taller.poo;

import java.util.Locale;

public class Principal {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Vehiculo v1 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo v2 = new Vehiculo();
        Vehiculo v3 = new Vehiculo("Mazda", "CX-5", 32000.75, 200, false);

        Estudiante e1 = new Estudiante("2024001", "Laura Ramírez", 4.35, true);
        Estudiante e2 = new Estudiante("2024002", "Andrés Torres", 3.80, true);
        Estudiante e3 = new Estudiante("2024003", "Camila Rojas", 2.95, false);

        CuentaBancaria c1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        CuentaBancaria c2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria c3 = new CuentaBancaria();

        boolean d1 = c1.depositar(500.00);
        boolean r1 = c1.retirar(249.25);
        boolean d2 = c2.depositar(100.00);
        boolean r2 = c2.retirar(50.00);
        boolean d3 = c3.depositar(-20.00);
        boolean r3 = c3.retirar(30.00);

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        System.out.printf("[1] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v1.getMarca(), v1.getModelo(), v1.getKilometraje(), v1.getVelocidadMaxima(), v1.isEncendido());
        System.out.printf("[2] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v2.getMarca(), v2.getModelo(), v2.getKilometraje(), v2.getVelocidadMaxima(), v2.isEncendido());
        System.out.printf("[3] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v3.getMarca(), v3.getModelo(), v3.getKilometraje(), v3.getVelocidadMaxima(), v3.isEncendido());

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        System.out.printf("%s%n", e1.obtenerReporteAcademicos());
        System.out.printf("%s%n", e2.obtenerReporteAcademicos());
        System.out.printf("%s", e3.obtenerReporteAcademicos());

        System.out.printf("%n=== OPERACIONES BANCARIAS ===%n");
        System.out.printf("Cuenta %s -> depósito: %b | retiro: %b%n", c1.getNumeroCuenta(), d1, r1);
        System.out.printf("Cuenta %s -> depósito: %b | retiro: %b%n", c2.getNumeroCuenta(), d2, r2);
        System.out.printf("Cuenta %s -> depósito: %b | retiro: %b%n", c3.getNumeroCuenta(), d3, r3);

        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c1.getNumeroCuenta(), c1.getTitular(),
                ConversorMoneda.formatearMoneda(c1.getSaldo()), ConversorMoneda.formatearMoneda(c1.getLimiteCredito()));
        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c2.getNumeroCuenta(), c2.getTitular(),
                ConversorMoneda.formatearMoneda(c2.getSaldo()), ConversorMoneda.formatearMoneda(c2.getLimiteCredito()));
        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c3.getNumeroCuenta(), c3.getTitular(),
                ConversorMoneda.formatearMoneda(c3.getSaldo()), ConversorMoneda.formatearMoneda(c3.getLimiteCredito()));

        double euros = ConversorMoneda.convertirDolaresAEuros(250.00, 0.92);
        System.out.printf("%n=== CONVERSOR DE MONEDA ===%n");
        System.out.printf("%s USD = %s EUR%n", ConversorMoneda.formatearMoneda(250.00), ConversorMoneda.formatearMoneda(euros));
    }
}
