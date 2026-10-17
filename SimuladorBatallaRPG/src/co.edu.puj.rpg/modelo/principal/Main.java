package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    private static final double COSTO_POCION_GRANDE = 40.0;
    private static final int CURA_POCION = 30;
    private static final int CURA_POCION_GRANDE = 70;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco", 90, 14, 35.5, 60);
        StringBuilder historial = new StringBuilder();
        boolean huyo = false;

        System.out.println("===== ARENA DE HÉROES =====");
        System.out.printf("%s se enfrenta a %s%n", heroe.getNombre(), enemigo.getNombre());
        historial.append("Comienza el combate entre ").append(heroe.getNombre())
                .append(" y ").append(enemigo.getNombre()).append("\n");

        do {
            mostrarEstado(heroe, enemigo);
            System.out.println("1. Atacar");
            System.out.println("2. Usar Poción");
            System.out.println("3. Ver Estado");
            System.out.println("4. Huir");
            System.out.println("5. Tienda");
            System.out.println("6. Verificar límites de los setters");
            System.out.print("Elige una opción: ");

            int opcion;
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                sc.next();
                opcion = -1;
            }

            boolean turnoGastado = false;
            int vidaPrevia = heroe.getPuntosVida();

            if (opcion < 1 || opcion > 6) {
                System.out.println("Opción inválida. Intenta de nuevo.");
            } else {
                switch (opcion) {
                    case 1:
                        heroe.atacar(enemigo);
                        if (heroe.isUltimoCritico()) {
                            System.out.printf("¡Golpe crítico! %s inflige %d de daño a %s%n",
                                    heroe.getNombre(), heroe.getUltimoDano(), enemigo.getNombre());
                            historial.append("¡Golpe crítico! ");
                        } else {
                            System.out.printf("%s inflige %d de daño a %s%n",
                                    heroe.getNombre(), heroe.getUltimoDano(), enemigo.getNombre());
                        }
                        historial.append(heroe.getNombre()).append(" inflige ")
                                .append(heroe.getUltimoDano()).append(" de daño a ")
                                .append(enemigo.getNombre()).append("\n");
                        turnoGastado = true;
                        break;
                    case 2:
                        if (heroe.getPociones() > 0) {
                            heroe.consumirPocion();
                            heroe.usarPocion(CURA_POCION);
                            turnoGastado = true;
                        } else if (heroe.getPocionesGrandes() > 0) {
                            heroe.consumirPocionGrande();
                            heroe.usarPocion(CURA_POCION_GRANDE);
                            turnoGastado = true;
                        } else {
                            System.out.println("No tienes pociones disponibles.");
                        }
                        if (turnoGastado) {
                            int curado = heroe.getPuntosVida() - vidaPrevia;
                            System.out.printf("%s recupera %d puntos de vida%n", heroe.getNombre(), curado);
                            historial.append(heroe.getNombre()).append(" recupera ")
                                    .append(curado).append(" puntos de vida\n");
                        }
                        break;
                    case 3:
                        mostrarEstadoDetallado(heroe, enemigo);
                        break;
                    case 4:
                        huyo = true;
                        System.out.printf("%s decide huir del combate%n", heroe.getNombre());
                        historial.append(heroe.getNombre()).append(" huye del combate\n");
                        break;
                    case 5:
                        tienda(sc, heroe, historial);
                        break;
                    case 6:
                        probarLimites();
                        break;
                }
            }

            if (turnoGastado && enemigo.isEstaVivo() && !huyo) {
                int danoEnemigo = enemigo.getPuntosAtaque();
                heroe.recibirDano(danoEnemigo);
                System.out.printf("%s ataca y causa %d de daño a %s%n",
                        enemigo.getNombre(), danoEnemigo, heroe.getNombre());
                historial.append(enemigo.getNombre()).append(" causa ")
                        .append(danoEnemigo).append(" de daño a ")
                        .append(heroe.getNombre()).append("\n");
            }

            System.out.println("\n--- Historial de batalla ---");
            System.out.println(historial.toString());
        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !huyo);

        if (huyo) {
            System.out.println("Escapaste de la arena.");
        } else if (!heroe.isEstaVivo()) {
            historial.append(heroe.getNombre()).append(" ha sido derrotado\n");
            System.out.printf("%s ha sido derrotado por %s. Fin del juego.%n",
                    heroe.getNombre(), enemigo.getNombre());
        } else {
            int nivelAnterior = heroe.getNivel();
            heroe.setOro(heroe.getOro() + enemigo.getRecompensaOro());
            heroe.ganarExperiencia(enemigo.getRecompensaExperiencia());
            historial.append(heroe.getNombre()).append(" derrota a ")
                    .append(enemigo.getNombre()).append("\n");
            System.out.printf("¡%s derrotó a %s!%n", heroe.getNombre(), enemigo.getNombre());
            System.out.printf("Recompensa: %.2f de oro y %d de experiencia%n",
                    enemigo.getRecompensaOro(), enemigo.getRecompensaExperiencia());
            historial.append("Recompensa obtenida: ").append(enemigo.getRecompensaOro())
                    .append(" de oro y ").append(enemigo.getRecompensaExperiencia())
                    .append(" de experiencia\n");
            if (heroe.getNivel() > nivelAnterior) {
                System.out.printf("¡%s sube al nivel %d!%n", heroe.getNombre(), heroe.getNivel());
                historial.append(heroe.getNombre()).append(" sube al nivel ")
                        .append(heroe.getNivel()).append("\n");
            }
        }

        System.out.println("\n--- Historial final ---");
        System.out.println(historial.toString());
        sc.close();
    }

    private static void mostrarEstado(Personaje heroe, Enemigo enemigo) {
        System.out.println("\n----- ESTADO DEL COMBATE -----");
        System.out.printf("%s | Nivel: %d | Vida: %d/%d | Oro: %.2f%n",
                heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(),
                heroe.getVidaMaxima(), heroe.getOro());
        System.out.printf("%s | Vida: %d/%d%n",
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima());
        System.out.println("------------------------------");
    }

    private static void mostrarEstadoDetallado(Personaje heroe, Enemigo enemigo) {
        String vivo;
        if (heroe.isEstaVivo()) {
            vivo = "Sí";
        } else {
            vivo = "No";
        }
        System.out.println("\n----- ESTADO DEL PERSONAJE -----");
        System.out.printf("Nombre: %s%n", heroe.getNombre());
        System.out.printf("Nivel: %d%n", heroe.getNivel());
        System.out.printf("Experiencia: %d/%d%n", heroe.getExperiencia(), heroe.getExperienciaNecesaria());
        System.out.printf("Vida: %d/%d%n", heroe.getPuntosVida(), heroe.getVidaMaxima());
        System.out.printf("Ataque: %d%n", heroe.getPuntosAtaque());
        System.out.printf("Oro: %.2f%n", heroe.getOro());
        System.out.printf("Pociones: %d | Pociones grandes: %d%n",
                heroe.getPociones(), heroe.getPocionesGrandes());
        System.out.printf("Vivo: %s%n", vivo);
        System.out.println("----- ENEMIGO -----");
        System.out.printf("Nombre: %s%n", enemigo.getNombre());
        System.out.printf("Vida: %d/%d%n", enemigo.getPuntosVida(), enemigo.getVidaMaxima());
        System.out.printf("Ataque: %d%n", enemigo.getPuntosAtaque());
    }

    private static void tienda(Scanner sc, Personaje heroe, StringBuilder historial) {
        int opcion;
        do {
            System.out.println("\n----- TIENDA -----");
            System.out.printf("Oro disponible: %.2f%n", heroe.getOro());
            System.out.printf("1. Poción Grande de Salud (restaura %d) - %.2f de oro%n",
                    CURA_POCION_GRANDE, COSTO_POCION_GRANDE);
            System.out.println("2. Volver al combate");
            System.out.print("Elige una opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                sc.next();
                opcion = 0;
            }

            if (opcion == 1) {
                double nuevoSaldo = heroe.getOro() - COSTO_POCION_GRANDE;
                if (heroe.setOro(nuevoSaldo)) {
                    heroe.agregarPocionGrande();
                    System.out.printf("Compraste una Poción Grande de Salud. Oro restante: %.2f%n", heroe.getOro());
                    historial.append(heroe.getNombre()).append(" compra una Poción Grande de Salud\n");
                } else {
                    System.out.println("Compra rechazada: no se entregó ninguna poción.");
                }
            } else if (opcion != 2) {
                System.out.println("Opción inválida. Intenta de nuevo.");
            }
        } while (opcion != 2);
    }

    private static void probarLimites() {
        Personaje prueba = new Personaje("Prueba", 100, 10, 0.0);
        int[] valores = {-10, 0, 100000};

        System.out.println("\n--- Prueba de límites: setPuntosVida ---");
        for (int valor : valores) {
            prueba.setPuntosVida(valor);
            System.out.printf("Valor %d -> vida: %d, vivo: %s%n",
                    valor, prueba.getPuntosVida(), prueba.isEstaVivo());
        }

        System.out.println("\n--- Prueba de límites: setOro ---");
        for (int valor : valores) {
            prueba.setOro(valor);
            System.out.printf("Valor %d -> oro: %.2f%n", valor, prueba.getOro());
        }
    }
}
