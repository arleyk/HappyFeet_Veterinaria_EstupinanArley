package com.happyfeet.view;

import com.happyfeet.controller.RegistroJornadaVacunacionController;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Menú para la gestión de registros de jornadas de vacunación
 */
public class RegistroJornadaVacunacionMenu extends BaseMenu {
    private final RegistroJornadaVacunacionController controller;
    
    public RegistroJornadaVacunacionMenu(Scanner scanner) {
        super(scanner);
        try {
            this.controller = new RegistroJornadaVacunacionController();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el controlador de registros de vacunación", e);
        }
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE REGISTROS DE VACUNACIÓN ===");
            System.out.println("1. Registrar vacunación en jornada");
            System.out.println("2. Listar todos los registros");
            System.out.println("3. Buscar registro por ID");
            System.out.println("4. Buscar registros por jornada");
            System.out.println("5. Buscar registros por mascota");
            System.out.println("6. Programar próxima dosis");
            System.out.println("7. Eliminar registro");
            System.out.println("8. Mostrar estadísticas generales");
            System.out.println("9. Mostrar estadísticas de jornada");
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
                registrarVacunacion();
                break;
            case 2:
                controller.listarRegistros();
                break;
            case 3:
                buscarRegistroPorId();
                break;
            case 4:
                buscarRegistrosPorJornada();
                break;
            case 5:
                buscarRegistrosPorMascota();
                break;
            case 6:
                programarProximaDosis();
                break;
            case 7:
                eliminarRegistro();
                break;
            case 8:
                controller.mostrarEstadisticas();
                break;
            case 9:
                mostrarEstadisticasJornada();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente nuevamente.");
        }
    }
    
    private void registrarVacunacion() {
        System.out.println("\n--- Registrar Vacunación en Jornada ---");
        
        System.out.print("ID de la jornada: ");
        Integer jornadaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("ID de la mascota: ");
        Integer mascotaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("ID del dueño: ");
        Integer duenoId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("ID de la vacuna: ");
        Integer vacunaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Fecha y hora (yyyy-mm-ddTHH:MM): ");
        String fechaHoraStr = scanner.nextLine();
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);
        
        System.out.print("Lote de la vacuna: ");
        String loteVacuna = scanner.nextLine();
        
        System.out.print("ID del veterinario (opcional, enter para omitir): ");
        String veterinarioIdStr = scanner.nextLine();
        Integer veterinarioId = veterinarioIdStr.isEmpty() ? null : Integer.parseInt(veterinarioIdStr);
        
        System.out.print("Observaciones (opcional): ");
        String observaciones = scanner.nextLine();
        
        boolean exito = controller.registrarVacunacion(jornadaId, mascotaId, duenoId, vacunaId, fechaHora, loteVacuna, veterinarioId, observaciones);
        if (exito) {
            System.out.println("Vacunación registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la vacunación.");
        }
    }
    
    private void buscarRegistroPorId() {
        System.out.print("Ingrese el ID del registro: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarRegistroPorId(id);
    }
    
    private void buscarRegistrosPorJornada() {
        System.out.print("Ingrese el ID de la jornada: ");
        int jornadaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarRegistrosPorJornada(jornadaId);
    }
    
    private void buscarRegistrosPorMascota() {
        System.out.print("Ingrese el ID de la mascota: ");
        int mascotaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarRegistrosPorMascota(mascotaId);
    }
    
    private void programarProximaDosis() {
        System.out.print("Ingrese el ID del registro: ");
        int registroId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Fecha de la próxima dosis (yyyy-mm-dd): ");
        String proximaDosisStr = scanner.nextLine();
        java.time.LocalDate proximaDosis = java.time.LocalDate.parse(proximaDosisStr);
        
        boolean exito = controller.programarProximaDosis(registroId, proximaDosis);
        if (exito) {
            System.out.println("Próxima dosis programada exitosamente.");
        } else {
            System.out.println("Error al programar la próxima dosis.");
        }
    }
    
    private void eliminarRegistro() {
        System.out.print("Ingrese el ID del registro a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("¿Está seguro de que desea eliminar este registro? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            boolean exito = controller.eliminarRegistro(id);
            if (exito) {
                System.out.println("Registro eliminado exitosamente.");
            } else {
                System.out.println("Error al eliminar el registro.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
    
    private void mostrarEstadisticasJornada() {
        System.out.print("Ingrese el ID de la jornada: ");
        int jornadaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.mostrarEstadisticasJornada(jornadaId);
    }
}