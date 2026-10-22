package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.List;

public class Escuadron {

    private String nombre;
    private List<Personaje> miembros;

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void reclutarPersonaje(Personaje p) {
        if (p == null) {
            System.out.println("No se puede reclutar un personaje nulo.");
            return;
        }
        if (miembros.contains(p)) {
            System.out.printf("%s ya pertenece al escuadrón %s%n", p.getNombre(), nombre);
            return;
        }
        miembros.add(p);
        System.out.printf("%s fue reclutado en el escuadrón %s%n", p.getNombre(), nombre);
    }

    public void expulsarPersonaje(Personaje p) {
        if (p != null && miembros.remove(p)) {
            System.out.printf("%s fue expulsado del escuadrón %s%n", p.getNombre(), nombre);
        } else {
            System.out.println("El personaje no pertenece a este escuadrón.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getMiembros() {
        return miembros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escuadrón: ").append(nombre).append(" [").append(miembros.size()).append(" miembros]");
        if (!miembros.isEmpty()) {
            sb.append(" -> ");
            for (int i = 0; i < miembros.size(); i++) {
                sb.append(miembros.get(i).getNombre());
                if (i < miembros.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
