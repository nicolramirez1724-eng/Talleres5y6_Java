package co.edu.puj.rpg.arena;

public class Enemigo extends Personaje {

    public Enemigo(String nombre, int vida, int ataqueBase, int posX, int posY) {
        super(nombre, vida, ataqueBase, posX, posY);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        System.out.printf("%s embiste a %s%n", nombre, objetivo.getNombre());
        objetivo.defender(ataqueBase);
    }
}
