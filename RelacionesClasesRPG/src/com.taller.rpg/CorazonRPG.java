package co.edu.puj.rpg.relaciones;

public class CorazonRPG {

    private int saludBase;
    private int manaBase;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
    }

    public int getSaludBase() {
        return saludBase;
    }

    public int getManaBase() {
        return manaBase;
    }

    public void modificarSalud(int cambio) {
        saludBase += cambio;
        if (saludBase < 0) {
            saludBase = 0;
        }
    }

    public void modificarMana(int cambio) {
        manaBase += cambio;
        if (manaBase < 0) {
            manaBase = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Salud: ").append(saludBase).append(" | Maná: ").append(manaBase);
        return sb.toString();
    }
}
