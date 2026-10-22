package co.edu.puj.rpg.arena;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;
    private int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.vidaMaxima = vida;
    }

    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    @Override
    public void defender(int danoRecibido) {
        recibirDano(danoRecibido);
        System.out.printf("%s recibe %d de daño. Vida restante: %d%n", nombre, danoRecibido, vida);
        if (!estaVivo()) {
            System.out.printf("%s ha caído en combate%n", nombre);
        }
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int cantidad) {
        if (cantidad < 0) {
            return;
        }
        vida -= cantidad;
        if (vida < 0) {
            vida = 0;
        }
    }

    public void curar(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        int vidaAnterior = vida;
        vida += cantidad;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
        System.out.printf("%s recupera %d puntos de vida. Vida actual: %d%n", nombre, vida - vidaAnterior, vida);
    }

    public void setPosicion(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }
}
