package com.happyfeet.view;

import com.happyfeet.controller.HistorialMedicoController;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Menú para la gestión del historial médico
 * Extiende BaseMenu para funcionalidad común
 */
public class HistorialMedicoMenu extends BaseMenu {
    private final HistorialMedicoController historialController;
    
    public HistorialMedicoMenu(Scanner scanner) throws VeterinariaException {
        super(scanner);
        this.historialController = new HistorialMedicoController();
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE HISTORIAL MÉDICO ===");
            System.out.println("1. Registrar historial médico");
            System.out.println("2. Listar todos los historiales");
            System.out.println("3. Buscar historial por ID");
            System.out.println("4. Buscar historiales por mascota");
            System.out.println("5. Buscar historiales por tipo de evento");
            System.out.println("6. Buscar historiales por rango de fechas");
            System.out.println("7. Eliminar historial médico");
            System.out.println("8. Estadísticas");
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
                registrarHistorial();
                break;
            case 2:
                historialController.listarHistoriales();
                break;
            case 3:
                buscarHistorialPorId();
                break;
            case 4:
                buscarHistorialesPorMascota();
                break;
            case 5:
                buscarHistorialesPorEventoTipo();
                break;
            case 6:
                buscarHistorialesPorRangoFechas();
                break;
            case 7:
                eliminarHistorial();
                break;
            case 8:
                historialController.mostrarEstadisticas();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente nuevamente.");
        }
    }
    
    private void registrarHistorial() {
        try {
            System.out.println("\n--- REGISTRO DE HISTORIAL MÉDICO ---");
            
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Fecha del evento (AAAA-MM-DD): ");
            LocalDate fechaEvento = LocalDate.parse(scanner.nextLine());
            
            System.out.print("ID del tipo de evento: ");
            Integer eventoTipoId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();
            
            System.out.print("Diagnóstico: ");
            String diagnostico = scanner.nextLine();
            
            System.out.print("Tratamiento recomendado: ");
            String tratamientoRecomendado = scanner.nextLine();
            
            System.out.print("ID del veterinario (opcional, presione Enter para omitir): ");
            String veterinarioIdStr = scanner.nextLine();
            Integer veterinarioId = veterinarioIdStr.isEmpty() ? null : Integer.parseInt(veterinarioIdStr);
            
            System.out.print("ID de la consulta (opcional, presione Enter para omitir): ");
            String consultaIdStr = scanner.nextLine();
            Integer consultaId = consultaIdStr.isEmpty() ? null : Integer.parseInt(consultaIdStr);
            
            System.out.print("ID del procedimiento (opcional, presione Enter para omitir): ");
            String procedimientoIdStr = scanner.nextLine();
            Integer procedimientoId = procedimientoIdStr.isEmpty() ? null : Integer.parseInt(procedimientoIdStr);
            
            boolean exito = historialController.registrarHistorial(
                mascotaId, fechaEvento, eventoTipoId, descripcion, diagnostico,
                tratamientoRecomendado, veterinarioId, consultaId, procedimientoId
            );
            
            if (exito) {
                System.out.println("Historial médico registrado exitosamente.");
            } else {
                System.out.println("Error al registrar el historial médico.");
            }
            
        } catch (Exception e) {
            System.err.println("Error en el registro: " + e.getMessage());
        }
    }
    
    private void buscarHistorialPorId() {
        System.out.print("Ingrese el ID del historial médico: ");
        try {
            Integer id = Integer.parseInt(scanner.nextLine());
            historialController.buscarHistorialPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    private void buscarHistorialesPorMascota() {
        System.out.print("Ingrese el ID de la mascota: ");
        try {
            Integer mascotaId = Integer.parseInt(scanner.nextLine());
            historialController.buscarHistorialesPorMascota(mascotaId);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    private void buscarHistorialesPorEventoTipo() {
        System.out.print("Ingrese el ID del tipo de evento: ");
        try {
            Integer eventoTipoId = Integer.parseInt(scanner.nextLine());
            historialController.buscarHistorialesPorEventoTipo(eventoTipoId);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
    
    private void buscarHistorialesPorRangoFechas() {
        try {
            System.out.print("Fecha de inicio (AAAA-MM-DD): ");
            LocalDate fechaInicio = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Fecha de fin (AAAA-MM-DD): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine());
            
            historialController.buscarHistorialesPorRangoFechas(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido. Use AAAA-MM-DD.");
        }
    }
    
    private void eliminarHistorial() {
        System.out.print("Ingrese el ID del historial médico a eliminar: ");
        try {
            Integer id = Integer.parseInt(scanner.nextLine());
            boolean exito = historialController.eliminarHistorial(id);
            if (exito) {
                System.out.println("Historial médico eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el historial médico.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}