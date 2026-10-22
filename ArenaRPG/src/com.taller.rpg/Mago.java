package co.edu.puj.rpg.arena;

public class Mago extends Personaje {

    private static final int COSTO_MANA = 10;

    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        int dano;
        if (puntosMana >= COSTO_MANA) {
            puntosMana -= COSTO_MANA;
            dano = ataqueBase * 2;
            System.out.printf("%s lanza una Bola de Fuego contra %s (Maná restante: %d)%n",
                    nombre, objetivo.getNombre(), puntosMana);
        } else {
            dano = ataqueBase / 2;
            System.out.printf("%s no tiene maná y golpea con su bastón a %s%n", nombre, objetivo.getNombre());
        }
        objetivo.defender(dano);
    }

    public void atacar(Personaje p) {
        System.out.printf("%s lanza un hechizo menor a %s%n", nombre, p.getNombre());
        p.defender(ataqueBase / 2);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        if (modoFuria) {
            System.out.printf("%s libera una descarga arcana sobre %s%n", nombre, p.getNombre());
            p.defender(ataqueBase);
        } else {
            atacar(p);
        }
    }

    public int getPuntosMana() {
        return puntosMana;
    }
}
