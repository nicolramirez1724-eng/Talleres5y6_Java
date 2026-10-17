package com.taller.poo;

import java.util.Locale;
import java.util.Scanner;

public class SistemaGestionCuentas {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez");
        cuentas[2] = new CuentaBancaria("1003", "Luis Martínez", 750.50, 200.0);

        int opcion;
        do {
            System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
            System.out.printf("1. Depositar en una cuenta%n");
            System.out.printf("2. Retirar de una cuenta%n");
            System.out.printf("3. Ver reporte consolidado del sistema%n");
            System.out.printf("4. Salir%n");
            System.out.printf("Seleccione una opción: ");
            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    System.out.printf("Número de cuenta: ");
                    String numDeposito = scanner.nextLine().trim();
                    System.out.printf("Monto a depositar: ");
                    double montoDeposito = leerDecimal(scanner);
                    if (montoDeposito <= 0) {
                        System.out.printf("Error: el monto debe ser mayor a cero.%n");
                    } else {
                        CuentaBancaria cuentaDeposito = buscarCuenta(cuentas, numDeposito);
                        if (cuentaDeposito == null) {
                            System.out.printf("Error: la cuenta %s no existe.%n", numDeposito);
                        } else {
                            cuentaDeposito.depositar(montoDeposito);
                            System.out.printf("Depósito exitoso. Nuevo saldo: %s%n",
                                    ConversorMoneda.formatearMoneda(cuentaDeposito.getSaldo()));
                        }
                    }
                    break;
                case 2:
                    System.out.printf("Número de cuenta: ");
                    String numRetiro = scanner.nextLine().trim();
                    System.out.printf("Monto a retirar: ");
                    double montoRetiro = leerDecimal(scanner);
                    if (montoRetiro <= 0) {
                        System.out.printf("Error: el monto debe ser mayor a cero.%n");
                    } else {
                        CuentaBancaria cuentaRetiro = buscarCuenta(cuentas, numRetiro);
                        if (cuentaRetiro == null) {
                            System.out.printf("Error: la cuenta %s no existe.%n", numRetiro);
                        } else if (cuentaRetiro.retirar(montoRetiro)) {
                            System.out.printf("Retiro exitoso. Nuevo saldo: %s%n",
                                    ConversorMoneda.formatearMoneda(cuentaRetiro.getSaldo()));
                        } else {
                            System.out.printf("Error: fondos insuficientes.%n");
                        }
                    }
                    break;
                case 3:
                    mostrarReporte(cuentas);
                    break;
                case 4:
                    System.out.printf("Saliendo del sistema.%n");
                    break;
                default:
                    System.out.printf("Opción inválida. Ingrese un número entre 1 y 4.%n");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    private static void mostrarReporte(CuentaBancaria[] cuentas) {
        StringBuilder reporte = new StringBuilder();
        double total = 0.0;
        reporte.append(String.format("%n=== REPORTE CONSOLIDADO ===%n"));
        for (int i = 0; i < cuentas.length; i++) {
            reporte.append(String.format("Cuenta: %s | Titular: %s | Saldo: %s | Crédito: %s%n",
                    cuentas[i].getNumeroCuenta(), cuentas[i].getTitular(),
                    ConversorMoneda.formatearMoneda(cuentas[i].getSaldo()),
                    ConversorMoneda.formatearMoneda(cuentas[i].getLimiteCredito())));
            total += cuentas[i].getSaldo();
        }
        reporte.append(String.format("Saldo total acumulado: %s%n", ConversorMoneda.formatearMoneda(total)));
        System.out.printf("%s", reporte.toString());
    }

    private static int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double leerDecimal(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
