package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    crearGremio(scanner, gremios, personajes);
                    break;
                case 3:
                    reclutarEnEscuadron(scanner, personajes, escuadrones);
                    break;
                case 4:
                    equiparArma(scanner, personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, escuadrones, gremios);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (scanner.hasNextInt()) {
            int valor = scanner.nextInt();
            scanner.nextLine();
            return valor;
        }
        scanner.nextLine();
        return -1;
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes creados.");
            return null;
        }
        System.out.println("Personajes disponibles:");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.printf("%d. %s (Nivel %d)%n", i + 1, personajes.get(i).getNombre(), personajes.get(i).getNivel());
        }
        int indice = leerEntero(scanner, "Seleccione un personaje: ") - 1;
        if (indice < 0 || indice >= personajes.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return personajes.get(indice);
    }

    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del personaje: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        int nivel = leerEntero(scanner, "Nivel del personaje: ");
        if (nivel < 1) {
            System.out.println("El nivel debe ser un número mayor o igual a 1.");
            return;
        }
        Personaje nuevo = new Personaje(nombre, nivel);
        personajes.add(nuevo);
        System.out.printf("Personaje %s creado con éxito%n", nuevo.getNombre());
    }

    private static void crearGremio(Scanner scanner, List<Gremio> gremios, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del gremio: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        Gremio nuevo = new Gremio(nombre);
        gremios.add(nuevo);
        System.out.printf("Gremio %s creado con éxito%n", nuevo.getNombre());

        if (!personajes.isEmpty()) {
            int respuesta = leerEntero(scanner, "¿Deseas unir un personaje a este gremio? (1. Sí / 2. No): ");
            if (respuesta == 1) {
                Personaje personaje = seleccionarPersonaje(scanner, personajes);
                if (personaje != null) {
                    personaje.unirseAGremio(nuevo);
                    System.out.printf("%s ahora pertenece al gremio %s%n", personaje.getNombre(), nuevo.getNombre());
                }
            }
        }
    }

    private static void reclutarEnEscuadron(Scanner scanner, List<Personaje> personajes, List<Escuadron> escuadrones) {
        Personaje personaje = seleccionarPersonaje(scanner, personajes);
        if (personaje == null) {
            return;
        }
        String nombreEscuadron = leerTexto(scanner, "Nombre del escuadrón: ");
        if (nombreEscuadron.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        Escuadron escuadron = buscarEscuadron(escuadrones, nombreEscuadron);
        if (escuadron == null) {
            escuadron = new Escuadron(nombreEscuadron);
            escuadrones.add(escuadron);
            System.out.printf("Escuadrón %s creado%n", escuadron.getNombre());
        }
        escuadron.reclutarPersonaje(personaje);
    }

    private static Escuadron buscarEscuadron(List<Escuadron> escuadrones, String nombre) {
        for (Escuadron e : escuadrones) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    private static void equiparArma(Scanner scanner, List<Personaje> personajes) {
        Personaje personaje = seleccionarPersonaje(scanner, personajes);
        if (personaje == null) {
            return;
        }
        System.out.println("1. Equipar arma");
        System.out.println("2. Retirar arma");
        int accion = leerEntero(scanner, "Seleccione una acción: ");

        if (accion == 1) {
            String nombreArma = leerTexto(scanner, "Nombre del arma: ");
            if (nombreArma.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }
            int danio = leerEntero(scanner, "Daño base del arma: ");
            if (danio < 0) {
                System.out.println("El daño base debe ser un número mayor o igual a 0.");
                return;
            }
            personaje.equiparArma(new Arma(nombreArma, danio));
        } else if (accion == 2) {
            personaje.desequiparArma();
        } else {
            System.out.println("Acción inválida.");
        }
    }

    private static void mostrarEstado(List<Personaje> personajes, List<Escuadron> escuadrones, List<Gremio> gremios) {
        if (personajes.isEmpty() && escuadrones.isEmpty() && gremios.isEmpty()) {
            System.out.println("No hay datos para mostrar todavía.");
            return;
        }

        System.out.println("\n--- PERSONAJES ---");
        System.out.printf("%-15s %-6s %-8s %-8s %-8s %-15s %-15s%n",
                "Nombre", "Nivel", "Salud", "Maná", "Ataque", "Arma", "Gremio");
        for (Personaje p : personajes) {
            String arma = "Ninguna";
            if (p.getArmaEquipada() != null) {
                arma = p.getArmaEquipada().getNombre();
            }
            String gremio = "Ninguno";
            if (p.getGremio() != null) {
                gremio = p.getGremio().getNombre();
            }
            System.out.printf("%-15s %-6d %-8d %-8d %-8d %-15s %-15s%n",
                    p.getNombre(), p.getNivel(), p.getSalud(), p.getMana(),
                    p.calcularAtaqueTotal(), arma, gremio);
        }

        System.out.println("\n--- ESCUADRONES ---");
        System.out.printf("%-15s %-10s%n", "Nombre", "Miembros");
        for (Escuadron e : escuadrones) {
            System.out.printf("%-15s %-10d%n", e.getNombre(), e.getMiembros().size());
        }

        System.out.println("\n--- GREMIOS ---");
        System.out.printf("%-15s %-10s%n", "Nombre", "Miembros");
        for (Gremio g : gremios) {
            System.out.printf("%-15s %-10d%n", g.getNombre(), g.getMiembros().size());
        }

        System.out.println("\n--- REPORTE DETALLADO ---");
        for (Personaje p : personajes) {
            System.out.println(p.toString());
        }
        for (Escuadron e : escuadrones) {
            System.out.println(e.toString());
        }
        for (Gremio g : gremios) {
            System.out.println(g.toString());
        }
    }
}
