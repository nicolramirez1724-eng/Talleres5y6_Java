package co.edu.puj.rpg.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int FILAS = 5;
    private static final int COLUMNAS = 5;
    private static final int COFRE_X = 2;
    private static final int COFRE_Y = 2;
    private static final char VACIO = '.';
    private static final char COFRE = 'C';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random azar = new Random();
        char[][] tablero = new char[FILAS][COLUMNAS];
        List<Personaje> heroes = new ArrayList<>();
        List<Personaje> enemigos = new ArrayList<>();
        Inventario<Pocion> inventario = new Inventario<>();

        heroes.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        heroes.add(new Mago("Gandalf", 80, 25, 0, 1, 50));

        enemigos.add(new Enemigo("Orco", 60, 10, 0, 4));
        enemigos.add(new Enemigo("Trasgo", 50, 12, 4, 0));
        enemigos.add(new Enemigo("Troll", 70, 14, 4, 4));

        inventario.agregar(new Pocion("Poción menor", 30));
        inventario.agregar(new Pocion("Poción menor", 30));
        inventario.agregar(new Pocion("Poción mayor", 60));

        Personaje heroePrincipal = heroes.get(0);
        boolean cofreAlcanzado = false;
        int ronda = 0;

        System.out.println("===== ARENA RPG =====");
        System.out.println("Elimina a todos los enemigos o llega al cofre [C] con " + heroePrincipal.getNombre());

        do {
            ronda++;
            System.out.printf("%n========== RONDA %d ==========%n", ronda);
            actualizarTablero(tablero, heroes, enemigos);
            mostrarTablero(tablero);
            mostrarEstadisticas(heroes, enemigos);

            for (Personaje heroe : heroes) {
                if (cofreAlcanzado || !hayVivos(enemigos)) {
                    break;
                }
                if (!heroe.estaVivo()) {
                    continue;
                }

                boolean accionHecha = false;
                do {
                    System.out.printf("%n--- Turno de %s ---%n", heroe.getNombre());
                    System.out.println("1. Atacar");
                    System.out.println("2. Usar Inventario");
                    System.out.println("3. Mover en Tablero");
                    System.out.print("Elige una opción: ");
                    int opcion = leerEntero(sc);

                    switch (opcion) {
                        case 1:
                            accionHecha = atacar(sc, heroe, enemigos);
                            break;
                        case 2:
                            accionHecha = usarInventario(sc, heroe, inventario);
                            break;
                        case 3:
                            accionHecha = mover(sc, heroe, tablero);
                            break;
                        default:
                            System.out.println("Opción inválida. Intenta de nuevo.");
                    }
                } while (!accionHecha);

                if (heroe == heroePrincipal && heroe.getPosicionX() == COFRE_X && heroe.getPosicionY() == COFRE_Y) {
                    cofreAlcanzado = true;
                    System.out.printf("¡%s llegó al cofre!%n", heroe.getNombre());
                }

                actualizarTablero(tablero, heroes, enemigos);
                mostrarTablero(tablero);
            }

            if (!cofreAlcanzado && hayVivos(enemigos)) {
                System.out.println("\n--- Turno de los enemigos ---");
                for (Personaje enemigo : enemigos) {
                    if (!enemigo.estaVivo()) {
                        continue;
                    }
                    if (!hayVivos(heroes)) {
                        break;
                    }
                    Personaje objetivo = elegirObjetivo(heroes, azar);
                    enemigo.realizarAtaqueEspecial(objetivo);
                }
                actualizarTablero(tablero, heroes, enemigos);
                mostrarTablero(tablero);
            }

            heroes.removeIf(p -> !p.estaVivo());
            enemigos.removeIf(p -> !p.estaVivo());
        } while (!heroes.isEmpty() && sumaVida(heroes) > 0 && !enemigos.isEmpty() && !cofreAlcanzado);

        System.out.println("\n========== FIN DEL COMBATE ==========");
        if (cofreAlcanzado) {
            System.out.println("¡VICTORIA! El héroe principal alcanzó el cofre.");
        } else if (enemigos.isEmpty()) {
            System.out.println("¡VICTORIA! Todos los enemigos fueron eliminados.");
        } else {
            System.out.println("DERROTA: el escuadrón de héroes ha caído.");
        }
        sc.close();
    }

    private static int leerEntero(Scanner sc) {
        if (sc.hasNextInt()) {
            return sc.nextInt();
        }
        sc.next();
        return -1;
    }

    private static boolean atacar(Scanner sc, Personaje heroe, List<Personaje> enemigos) {
        System.out.println("Enemigos:");
        for (int i = 0; i < enemigos.size(); i++) {
            Personaje enemigo = enemigos.get(i);
            if (enemigo.estaVivo()) {
                System.out.printf("%d. %s (Vida: %d)%n", i + 1, enemigo.getNombre(), enemigo.getVida());
            }
        }
        System.out.print("Elige el objetivo: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= enemigos.size() || !enemigos.get(indice).estaVivo()) {
            System.out.println("Objetivo inválido.");
            return false;
        }

        heroe.realizarAtaqueEspecial(enemigos.get(indice));
        return true;
    }

    private static boolean usarInventario(Scanner sc, Personaje heroe, Inventario<Pocion> inventario) {
        if (inventario.getItems().isEmpty()) {
            System.out.println("El inventario está vacío.");
            return false;
        }

        System.out.println("Inventario:");
        for (int i = 0; i < inventario.getItems().size(); i++) {
            Pocion pocion = inventario.obtener(i);
            System.out.printf("%d. %s (+%d de vida)%n", i + 1, pocion.getNombre(), pocion.getCuracion());
        }
        System.out.print("Elige una poción: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= inventario.getItems().size()) {
            System.out.println("Poción inválida.");
            return false;
        }

        Pocion pocion = inventario.obtener(indice);
        System.out.printf("%s usa %s%n", heroe.getNombre(), pocion.getNombre());
        heroe.curar(pocion.getCuracion());
        inventario.getItems().remove(indice);
        return true;
    }

    private static boolean mover(Scanner sc, Personaje heroe, char[][] tablero) {
        System.out.println("1. Arriba");
        System.out.println("2. Abajo");
        System.out.println("3. Izquierda");
        System.out.println("4. Derecha");
        System.out.print("Elige una dirección: ");
        int direccion = leerEntero(sc);

        int nuevaX = heroe.getPosicionX();
        int nuevaY = heroe.getPosicionY();

        switch (direccion) {
            case 1:
                nuevaX--;
                break;
            case 2:
                nuevaX++;
                break;
            case 3:
                nuevaY--;
                break;
            case 4:
                nuevaY++;
                break;
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        if (nuevaX < 0 || nuevaX >= tablero.length || nuevaY < 0 || nuevaY >= tablero[0].length) {
            System.out.println("Movimiento fuera del tablero.");
            return false;
        }

        char destino = tablero[nuevaX][nuevaY];
        if (destino != VACIO && destino != COFRE) {
            System.out.println("La celda está ocupada.");
            return false;
        }

        heroe.setPosicion(nuevaX, nuevaY);
        System.out.printf("%s se mueve a la posición (%d, %d)%n", heroe.getNombre(), nuevaX, nuevaY);
        return true;
    }

    private static Personaje elegirObjetivo(List<Personaje> heroes, Random azar) {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje heroe : heroes) {
            if (heroe.estaVivo()) {
                vivos.add(heroe);
            }
        }
        return vivos.get(azar.nextInt(vivos.size()));
    }

    private static boolean hayVivos(List<Personaje> lista) {
        for (Personaje p : lista) {
            if (p.estaVivo()) {
                return true;
            }
        }
        return false;
    }

    private static int sumaVida(List<Personaje> lista) {
        int total = 0;
        for (Personaje p : lista) {
            total += p.getVida();
        }
        return total;
    }

    private static void actualizarTablero(char[][] tablero, List<Personaje> heroes, List<Personaje> enemigos) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = VACIO;
            }
        }
        tablero[COFRE_X][COFRE_Y] = COFRE;

        for (Personaje heroe : heroes) {
            if (!heroe.estaVivo()) {
                continue;
            }
            if (heroe instanceof Guerrero) {
                tablero[heroe.getPosicionX()][heroe.getPosicionY()] = 'G';
            } else if (heroe instanceof Mago) {
                tablero[heroe.getPosicionX()][heroe.getPosicionY()] = 'M';
            }
        }

        for (Personaje enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                tablero[enemigo.getPosicionX()][enemigo.getPosicionY()] = 'E';
            }
        }
    }

    public static void mostrarTablero(char[][] matriz) {
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println("G: Guerrero | M: Mago | E: Enemigo | C: Cofre | .: Vacía");
    }

    private static void mostrarEstadisticas(List<Personaje> heroes, List<Personaje> enemigos) {
        System.out.println("\n--- Escuadrón de héroes ---");
        for (Personaje heroe : heroes) {
            if (heroe instanceof Mago) {
                Mago mago = (Mago) heroe;
                System.out.printf("%s | Vida: %d | Maná: %d | Posición: (%d, %d)%n",
                        mago.getNombre(), mago.getVida(), mago.getPuntosMana(),
                        mago.getPosicionX(), mago.getPosicionY());
            } else if (heroe instanceof Guerrero) {
                Guerrero guerrero = (Guerrero) heroe;
                System.out.printf("%s | Vida: %d | Escudo: %d | Posición: (%d, %d)%n",
                        guerrero.getNombre(), guerrero.getVida(), guerrero.getFuerzaEscudo(),
                        guerrero.getPosicionX(), guerrero.getPosicionY());
            }
        }

        System.out.println("\n--- Enemigos ---");
        for (Personaje enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                System.out.printf("%s | Vida: %d | Posición: (%d, %d)%n",
                        enemigo.getNombre(), enemigo.getVida(),
                        enemigo.getPosicionX(), enemigo.getPosicionY());
            }
        }
    }
}
