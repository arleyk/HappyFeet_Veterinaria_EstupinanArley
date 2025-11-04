package com.happyfeet.view;

import com.happyfeet.controller.AdopcionController;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Menú para la gestión de adopciones
 */
public class AdopcionMenu extends BaseMenu {
    private final AdopcionController controller;
    
    public AdopcionMenu(Scanner scanner) {
        super(scanner);
        try {
            this.controller = new AdopcionController();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el controlador de adopciones", e);
        }
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE ADOPCIONES ===");
            System.out.println("1. Registrar adopción");
            System.out.println("2. Listar todas las adopciones");
            System.out.println("3. Buscar adopción por ID");
            System.out.println("4. Buscar adopciones por dueño");
            System.out.println("5. Programar seguimiento");
            System.out.println("6. Eliminar adopción");
            System.out.println("7. Mostrar estadísticas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    @Override
    protected void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarAdopcion();
                break;
            case 2:
                controller.listarAdopciones();
                break;
            case 3:
                buscarAdopcionPorId();
                break;
            case 4:
                buscarAdopcionesPorDueno();
                break;
            case 5:
                programarSeguimiento();
                break;
            case 6:
                eliminarAdopcion();
                break;
            case 7:
                controller.mostrarEstadisticas();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente nuevamente.");
        }
    }
    
    private void registrarAdopcion() {
        System.out.println("\n--- Registrar Adopción ---");
        
        System.out.print("ID de la mascota en adopción: ");
        Integer mascotaAdopcionId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("ID del dueño: ");
        Integer duenoId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Texto del contrato: ");
        String contratoTexto = scanner.nextLine();
        
        System.out.print("Condiciones especiales: ");
        String condicionesEspeciales = scanner.nextLine();
        
        boolean exito = controller.registrarAdopcion(mascotaAdopcionId, duenoId, 
                                                    contratoTexto, condicionesEspeciales);
        if (exito) {
            System.out.println("Adopción registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la adopción.");
        }
    }
    
    private void buscarAdopcionPorId() {
        System.out.print("Ingrese el ID de la adopción: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarAdopcionPorId(id);
    }
    
    private void buscarAdopcionesPorDueno() {
        System.out.print("Ingrese el ID del dueño: ");
        int duenoId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarAdopcionesPorDueno(duenoId);
    }
    
    private void programarSeguimiento() {
        System.out.print("Ingrese el ID de la adopción: ");
        int adopcionId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Fecha del seguimiento (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();
        
        try {
            LocalDate fechaSeguimiento = LocalDate.parse(fechaStr);
            boolean exito = controller.programarSeguimiento(adopcionId, fechaSeguimiento);
            if (exito) {
                System.out.println("Seguimiento programado exitosamente.");
            } else {
                System.out.println("Error al programar el seguimiento.");
            }
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido. Use yyyy-mm-dd.");
        }
    }
    
    private void eliminarAdopcion() {
        System.out.print("Ingrese el ID de la adopción a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("¿Está seguro de que desea eliminar esta adopción? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            boolean exito = controller.eliminarAdopcion(id);
            if (exito) {
                System.out.println("Adopción eliminada exitosamente.");
            } else {
                System.out.println("Error al eliminar la adopción.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
}