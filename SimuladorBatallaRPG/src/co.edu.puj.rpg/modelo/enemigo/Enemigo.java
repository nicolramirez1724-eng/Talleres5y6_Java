package co.edu.puj.rpg.modelo.enemigo;

public class Enemigo {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private boolean estaVivo;
    private double recompensaOro;
    private int recompensaExperiencia;

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, double recompensaOro, int recompensaExperiencia) {
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
        if (recompensaOro < 0.0) {
            this.recompensaOro = 0.0;
        } else {
            this.recompensaOro = recompensaOro;
        }
        if (recompensaExperiencia < 0) {
            this.recompensaExperiencia = 0;
        } else {
            this.recompensaExperiencia = recompensaExperiencia;
        }
        this.puntosVida = this.vidaMaxima;
        this.estaVivo = true;
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano <= 0) {
            return;
        }
        setPuntosVida(this.puntosVida - cantidadDano);
    }

    private void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
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

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public double getRecompensaOro() {
        return recompensaOro;
    }

    public int getRecompensaExperiencia() {
        return recompensaExperiencia;
    }
}
