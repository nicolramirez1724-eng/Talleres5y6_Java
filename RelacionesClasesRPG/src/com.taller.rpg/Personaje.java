package co.edu.puj.rpg.relaciones;

public class Personaje {

    private String nombre;
    private int nivel;
    private Arma armaEquipada;
    private Gremio gremio;
    private CorazonRPG corazon;

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.armaEquipada = null;
        this.gremio = null;
        this.corazon = new CorazonRPG(100 + nivel * 20, 50 + nivel * 10);
    }

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No se puede equipar un arma nula.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.printf("%s equipó el arma %s%n", nombre, nuevaArma.getNombre());
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            System.out.printf("%s retiró el arma %s%n", nombre, armaEquipada.getNombre());
            this.armaEquipada = null;
        } else {
            System.out.printf("%s no tiene un arma para retirar%n", nombre);
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueBase = this.nivel * 5;
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }

    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        if (this.gremio != null) {
            abandonarGremio();
        }
        this.gremio = nuevoGremio;
        nuevoGremio.agregarMiembro(this);
    }

    public void abandonarGremio() {
        if (this.gremio == null) {
            System.out.printf("%s no pertenece a ningún gremio%n", nombre);
            return;
        }
        Gremio gremioAnterior = this.gremio;
        this.gremio = null;
        gremioAnterior.removerMiembro(this);
        System.out.printf("%s abandonó el gremio %s%n", nombre, gremioAnterior.getNombre());
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
    }

    public int getSalud() {
        return corazon.getSaludBase();
    }

    public int getMana() {
        return corazon.getManaBase();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personaje: ").append(nombre).append(" (Nivel ").append(nivel).append(")\n");
        sb.append("  Corazón RPG: ").append(corazon.toString()).append("\n");
        sb.append("  Arma: ");
        if (armaEquipada != null) {
            sb.append(armaEquipada.toString());
        } else {
            sb.append("Desarmado");
        }
        sb.append("\n  Gremio: ");
        if (gremio != null) {
            sb.append(gremio.getNombre());
        } else {
            sb.append("Sin gremio");
        }
        sb.append("\n  Ataque total: ").append(calcularAtaqueTotal());
        return sb.toString();
    }
}
