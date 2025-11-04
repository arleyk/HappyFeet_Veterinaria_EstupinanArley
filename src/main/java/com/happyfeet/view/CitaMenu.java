package com.happyfeet.view;

import com.happyfeet.controller.CitaController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Menú para la gestión de citas veterinarias
 */
public class CitaMenu extends BaseMenu {
    private final CitaController citaController;
    
    public CitaMenu(Scanner scanner, CitaController controller) {
        super(scanner);
        this.citaController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE CITAS ===");
            System.out.println("1. Registrar Nueva Cita");
            System.out.println("2. Listar Todas las Citas");
            System.out.println("3. Buscar Cita por ID");
            System.out.println("4. Buscar Citas por Mascota");
            System.out.println("5. Buscar Citas por Veterinario");
            System.out.println("6. Buscar Citas por Estado");
            System.out.println("7. Obtener Citas de Hoy");
            System.out.println("8. Eliminar Cita");
            System.out.println("9. Actualizar Estado de Cita");
            System.out.println("10. Estadísticas de Citas");
            System.out.println("11. Verificar Disponibilidad de Veterinario");
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
                registrarNuevaCita();
                break;
            case 2:
                citaController.listarCitas();
                break;
            case 3:
                buscarCitaPorId();
                break;
            case 4:
                buscarCitasPorMascota();
                break;
            case 5:
                buscarCitasPorVeterinario();
                break;
            case 6:
                buscarCitasPorEstado();
                break;
            case 7:
                citaController.obtenerCitasHoy();
                break;
            case 8:
                eliminarCita();
                break;
            case 9:
                actualizarEstadoCita();
                break;
            case 10:
                citaController.mostrarEstadisticas();
                break;
            case 11:
                verificarDisponibilidadVeterinario();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevaCita() {
        System.out.println("\n=== REGISTRAR NUEVA CITA ===");

        try {
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID del veterinario (opcional, presione Enter para omitir): ");
            String veterinarioInput = scanner.nextLine().trim();
            Integer veterinarioId = veterinarioInput.isEmpty() ? null : Integer.parseInt(veterinarioInput);

            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Motivo de la cita: ");
            String motivo = scanner.nextLine().trim();

            System.out.print("ID del estado (1=Programada, 2=Confirmada, 3=En Proceso, 4=Finalizada, 5=Cancelada, 6=No Asistió): ");
            Integer estadoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("¿Desea ingresar observaciones? (s/n): ");
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("Observaciones: ");
                String observaciones = scanner.nextLine().trim();
                citaController.registrarCitaCompleta(mascotaId, veterinarioId, fechaHora, 
                                                    motivo, estadoId, observaciones);
            } else {
                citaController.registrarCita(mascotaId, veterinarioId, fechaHora, motivo, estadoId);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarCitaPorId() {
        System.out.print("\nIngrese el ID de la cita a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            citaController.buscarCitaPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarCitasPorMascota() {
        System.out.print("\nIngrese el ID de la mascota: ");
        try {
            int mascotaId = Integer.parseInt(scanner.nextLine().trim());
            citaController.buscarCitasPorMascota(mascotaId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarCitasPorVeterinario() {
        System.out.print("\nIngrese el ID del veterinario: ");
        try {
            int veterinarioId = Integer.parseInt(scanner.nextLine().trim());
            citaController.buscarCitasPorVeterinario(veterinarioId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarCitasPorEstado() {
        System.out.print("\nIngrese el ID del estado (1-6): ");
        try {
            int estadoId = Integer.parseInt(scanner.nextLine().trim());
            citaController.buscarCitasPorEstado(estadoId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void eliminarCita() {
        System.out.print("\nIngrese el ID de la cita a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar esta cita? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                citaController.eliminarCita(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void actualizarEstadoCita() {
        System.out.print("\nIngrese el ID de la cita: ");
        try {
            int citaId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Ingrese el nuevo ID de estado (1-6): ");
            int nuevoEstadoId = Integer.parseInt(scanner.nextLine().trim());

            citaController.actualizarEstadoCita(citaId, nuevoEstadoId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void verificarDisponibilidadVeterinario() {
        System.out.print("\nIngrese el ID del veterinario: ");
        try {
            int veterinarioId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Fecha y hora a verificar (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            citaController.verificarDisponibilidad(veterinarioId, fechaHora);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        }
    }
}