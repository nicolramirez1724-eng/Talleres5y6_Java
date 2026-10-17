package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experiencia;
    private int pociones;
    private int pocionesGrandes;
    private int ultimoDano;
    private boolean ultimoCritico;

    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        if (vidaMaxima < 1) {
            this.vidaMaxima = 1;
        } else {
            this.vidaMaxima = vidaMaxima;
        }
        if (puntosAtaque < 0) {
            this.puntosAtaque = 0;
        } else {
            this.puntosAtaque = puntosAtaque;
        }
        if (oro < 0.0) {
            this.oro = 0.0;
        } else {
            this.oro = oro;
        }
        this.puntosVida = this.vidaMaxima;
        this.nivel = 1;
        this.experiencia = 0;
        this.pociones = 3;
        this.pocionesGrandes = 0;
        this.estaVivo = true;
    }

    public Personaje(String nombre) {
        this(nombre, 100, 10, 0.0);
    }

    public void atacar(Enemigo enemigo) {
        int dano = this.puntosAtaque;
        this.ultimoCritico = false;
        if (Math.random() < 0.15) {
            dano = dano * 2;
            this.ultimoCritico = true;
        }
        this.ultimoDano = dano;
        enemigo.recibirDano(dano);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (cantidadRestaurada <= 0) {
            return;
        }
        setPuntosVida(this.puntosVida + cantidadRestaurada);
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        setPuntosVida(this.puntosVida - cantidadDano);
    }

    public void ganarExperiencia(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        this.experiencia += cantidad;
        while (this.experiencia >= getExperienciaNecesaria()) {
            this.experiencia -= getExperienciaNecesaria();
            setNivel(this.nivel + 1);
            this.vidaMaxima += 10;
            this.puntosAtaque += 2;
            setPuntosVida(this.vidaMaxima);
        }
    }

    public void consumirPocion() {
        if (this.pociones > 0) {
            this.pociones--;
        }
    }

    public void consumirPocionGrande() {
        if (this.pocionesGrandes > 0) {
            this.pocionesGrandes--;
        }
    }

    public void agregarPocionGrande() {
        this.pocionesGrandes++;
    }

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public boolean setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente.");
            return false;
        }
        this.oro = oro;
        return true;
    }

    protected void setNivel(int nivel) {
        if (nivel < 1) {
            return;
        }
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public int getNivel() {
        return nivel;
    }

    public double getOro() {
        return oro;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getExperienciaNecesaria() {
        return this.nivel * 50;
    }

    public int getPociones() {
        return pociones;
    }

    public int getPocionesGrandes() {
        return pocionesGrandes;
    }

    public int getUltimoDano() {
        return ultimoDano;
    }

    public boolean isUltimoCritico() {
        return ultimoCritico;
    }
}
