package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al
     * declarar 'saldo' como double primitivo frente a 'limiteCredito' como Wrapper
     * Double?
     * R: 'saldo' es un primitivo, guarda el valor directo dentro del objeto (8 bytes) y por
     * defecto vale 0.0. 'limiteCredito' es una referencia a un objeto Double en el heap, por
     * eso gasta mas memoria, y por defecto vale null.
     *
     * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de
     * tipo Double provoca una NullPointerException, mientras que sobre un double
     * primitivo opera con 0.0?
     * R: Para operar un Double, Java hace unboxing y llama a doubleValue() sobre el objeto.
     * Si es null no hay objeto y lanza NullPointerException. El double primitivo no es un
     * objeto y siempre tiene valor (0.0), por eso si opera.
     */

    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean retirar(double monto) {
        if (monto > 0) {
            double credito = 0.0;
            if (limiteCredito != null) {
                credito = limiteCredito;
            }
            if (saldo + credito >= monto) {
                saldo -= monto;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }
}
