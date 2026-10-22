package co.edu.puj.rpg.arena;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        int dano = ataqueBase * 2;
        System.out.printf("%s usa Golpe Demoledor contra %s%n", nombre, objetivo.getNombre());
        objetivo.defender(dano);
    }

    @Override
    public void defender(int danoRecibido) {
        int danoReducido = danoRecibido - fuerzaEscudo / 2;
        if (danoReducido < 0) {
            danoReducido = 0;
        }
        System.out.printf("El escudo de %s absorbe %d de daño%n", nombre, danoRecibido - danoReducido);
        super.defender(danoReducido);
    }

    public void atacar(Personaje p) {
        System.out.printf("%s ataca a %s con su espada%n", nombre, p.getNombre());
        p.defender(ataqueBase);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        if (modoFuria) {
            System.out.printf("%s ataca a %s en modo furia%n", nombre, p.getNombre());
            p.defender(ataqueBase * 2);
        } else {
            atacar(p);
        }
    }

    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }
}
