package com.happyfeet.view;

import com.happyfeet.controller.MascotaController;
import java.util.Scanner;

/**
 * Menú para la gestión de mascotas
 */
public class MascotaMenu extends BaseMenu {
    private final MascotaController mascotaController;
    
    public MascotaMenu(Scanner scanner, MascotaController controller) {
        super(scanner);
        this.mascotaController = controller;
    }

    @Override
    public void mostrarMenu() {
        menuActivo = true;

        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE MASCOTAS ===");
            System.out.println("1. Registrar Nueva Mascota");
            System.out.println("2. Listar Todas las Mascotas");
            System.out.println("3. Buscar Mascota por ID");
            System.out.println("4. Buscar Mascotas por Nombre");
            System.out.println("5. Eliminar Mascota");
            System.out.println("6. Estadísticas de Mascotas");
            System.out.println("7. Buscar Mascotas por ID de Dueño");
            System.out.println("8. Cambiar Dueño de Mascota"); // NUEVA OPCIÓN
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    @Override
protected void procesarOpcion(int opcion) {
    switch (opcion) {
        case 1:
            registrarNuevaMascota();
            break;
        case 2:
            mascotaController.listarMascotas();
            break;
        case 3:
            buscarMascotaPorId();
            break;
        case 4:
            buscarMascotasPorNombre();
            break;
        case 5:
            eliminarMascota();
            break;
        case 6:
            mascotaController.mostrarEstadisticas();
            break;
        case 7:
            buscarMascotasPorDuenoId();
            break;
        case 8: 
            cambiarDuenoMascota();
            break;
        case 0:
            cerrarMenu();
            break;
        default:
            System.out.println("❌ Opción inválida.");
    }
}

private void cambiarDuenoMascota() {
    System.out.println("\n=== CAMBIAR DUEÑO DE MASCOTA ===");
    try {
        System.out.print("ID de la mascota: ");
        Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nuevo ID del dueño: ");
        Integer nuevoDuenoId = Integer.parseInt(scanner.nextLine().trim());

        // Mostrar información de confirmación
        System.out.println("\n--- Confirmación de Cambio ---");
        System.out.println("ID Mascota: " + mascotaId);
        System.out.println("Nuevo Dueño ID: " + nuevoDuenoId);
        System.out.print("¿Está seguro de realizar el cambio? (s/n): ");
        
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("s")) {
            boolean exito = mascotaController.cambiarDuenoMascota(mascotaId, nuevoDuenoId);
            if (exito) {
                System.out.println("✅ Cambio de dueño realizado exitosamente");
            }
        } else {
            System.out.println("❌ Cambio de dueño cancelado");
        }
    } catch (NumberFormatException e) {
        System.out.println("❌ Error: Los IDs deben ser números válidos.");
    } catch (Exception e) {
        System.out.println("❌ Error inesperado: " + e.getMessage());
    }
}

    private void registrarNuevaMascota() {
        System.out.println("\n=== REGISTRAR NUEVA MASCOTA ===");

        try {
            System.out.print("ID del dueño: ");
            Integer duenoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nombre de la mascota: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("ID de la raza: ");
            Integer razaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Sexo (Macho/Hembra): ");
            String sexo = scanner.nextLine().trim();

            System.out.print("¿Desea ingresar datos adicionales? (s/n): ");
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                String fechaNacimiento = scanner.nextLine().trim();

                System.out.print("Peso actual: ");
                Double pesoActual = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Microchip: ");
                String microchip = scanner.nextLine().trim();

                System.out.print("Alergias: ");
                String alergias = scanner.nextLine().trim();

                System.out.print("Condiciones preexistentes: ");
                String condicionesPreexistentes = scanner.nextLine().trim();

                mascotaController.registrarMascotaCompleta(duenoId, nombre, razaId, 
                                                          fechaNacimiento, sexo, pesoActual,
                                                          microchip, alergias, condicionesPreexistentes);
            } else {
                mascotaController.registrarMascota(duenoId, nombre, razaId, sexo);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarMascotaPorId() {
        System.out.print("\nIngrese el ID de la mascota a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            mascotaController.buscarMascotaPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarMascotasPorNombre() {
        System.out.print("\nIngrese el nombre o parte del nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        mascotaController.buscarMascotasPorNombre(nombre);
    }
    
    private void buscarMascotasPorDuenoId() {
        System.out.print("\nIngrese el ID del dueño: ");
        try {
            int duenoId = Integer.parseInt(scanner.nextLine().trim());
            mascotaController.buscarMascotasPorDuenoId(duenoId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void eliminarMascota() {
        System.out.print("\nIngrese el ID de la mascota a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar esta mascota? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                mascotaController.eliminarMascota(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}