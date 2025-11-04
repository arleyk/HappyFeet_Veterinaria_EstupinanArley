package com.happyfeet.view;

import com.happyfeet.controller.ProcedimientoEspecialController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Menú para la gestión de procedimientos especiales
 */
public class ProcedimientoEspecialMenu extends BaseMenu {
    private final ProcedimientoEspecialController procedimientoEspecialController;
    
    public ProcedimientoEspecialMenu(Scanner scanner, ProcedimientoEspecialController controller) {
        super(scanner);
        this.procedimientoEspecialController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE PROCEDIMIENTOS ESPECIALES ===");
            System.out.println("1. Registrar Nuevo Procedimiento Especial");
            System.out.println("2. Registrar Procedimiento Especial Completo");
            System.out.println("3. Listar Todos los Procedimientos Especiales");
            System.out.println("4. Buscar Procedimiento por ID");
            System.out.println("5. Buscar Procedimientos por Mascota");
            System.out.println("6. Buscar Procedimientos por Veterinario");
            System.out.println("7. Buscar Procedimientos por Estado");
            System.out.println("8. Buscar Procedimientos por Tipo");
            System.out.println("9. Actualizar Estado de Procedimiento");
            System.out.println("10. Estadísticas de Procedimientos");
            System.out.println("11. Eliminar Procedimiento");
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
                registrarNuevoProcedimientoEspecial();
                break;
            case 2:
                registrarProcedimientoEspecialCompleto();
                break;
            case 3:
                procedimientoEspecialController.listarProcedimientosEspeciales();
                break;
            case 4:
                buscarProcedimientoPorId();
                break;
            case 5:
                buscarProcedimientosPorMascota();
                break;
            case 6:
                buscarProcedimientosPorVeterinario();
                break;
            case 7:
                buscarProcedimientosPorEstado();
                break;
            case 8:
                buscarProcedimientosPorTipo();
                break;
            case 9:
                actualizarEstadoProcedimiento();
                break;
            case 10:
                procedimientoEspecialController.mostrarEstadisticas();
                break;
            case 11:
                eliminarProcedimiento();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevoProcedimientoEspecial() {
        System.out.println("\n=== REGISTRAR NUEVO PROCEDIMIENTO ESPECIAL ===");

        try {
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID del veterinario: ");
            Integer veterinarioId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Tipo de procedimiento: ");
            String tipoProcedimiento = scanner.nextLine().trim();

            System.out.print("Nombre del procedimiento: ");
            String nombreProcedimiento = scanner.nextLine().trim();

            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Detalle del procedimiento: ");
            String detalleProcedimiento = scanner.nextLine().trim();

            boolean exito = procedimientoEspecialController.registrarProcedimientoEspecial(
                mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, detalleProcedimiento);

            if (exito) {
                System.out.println("✅ Procedimiento especial registrado exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar el procedimiento especial");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void registrarProcedimientoEspecialCompleto() {
        System.out.println("\n=== REGISTRAR PROCEDIMIENTO ESPECIAL COMPLETO ===");

        try {
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID del veterinario: ");
            Integer veterinarioId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Tipo de procedimiento: ");
            String tipoProcedimiento = scanner.nextLine().trim();

            System.out.print("Nombre del procedimiento: ");
            String nombreProcedimiento = scanner.nextLine().trim();

            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Duración estimada en minutos: ");
            Integer duracionEstimadaMinutos = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Información preoperatoria: ");
            String informacionPreoperatoria = scanner.nextLine().trim();

            System.out.print("Detalle del procedimiento: ");
            String detalleProcedimiento = scanner.nextLine().trim();

            System.out.print("Complicaciones (opcional): ");
            String complicaciones = scanner.nextLine().trim();

            System.out.print("Seguimiento postoperatorio: ");
            String seguimientoPostoperatorio = scanner.nextLine().trim();

            System.out.print("Próximo control (YYYY-MM-DD HH:MM, opcional): ");
            String proximoControlStr = scanner.nextLine().trim();
            LocalDateTime proximoControl = proximoControlStr.isEmpty() ? null : 
                LocalDateTime.parse(proximoControlStr + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Estado del procedimiento: ");
            String estado = scanner.nextLine().trim();

            System.out.print("Costo del procedimiento: ");
            Double costoProcedimiento = Double.parseDouble(scanner.nextLine().trim());

            boolean exito = procedimientoEspecialController.registrarProcedimientoEspecialCompleto(
                mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, 
                duracionEstimadaMinutos, informacionPreoperatoria, detalleProcedimiento, 
                complicaciones, seguimientoPostoperatorio, proximoControl, estado, costoProcedimiento);

            if (exito) {
                System.out.println("✅ Procedimiento especial registrado exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar el procedimiento especial");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarProcedimientoPorId() {
        System.out.print("\nIngrese el ID del procedimiento a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            procedimientoEspecialController.buscarProcedimientoEspecialPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarProcedimientosPorMascota() {
        System.out.print("\nIngrese el ID de la mascota: ");
        try {
            int mascotaId = Integer.parseInt(scanner.nextLine().trim());
            procedimientoEspecialController.buscarProcedimientosPorMascota(mascotaId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarProcedimientosPorVeterinario() {
        System.out.print("\nIngrese el ID del veterinario: ");
        try {
            int veterinarioId = Integer.parseInt(scanner.nextLine().trim());
            procedimientoEspecialController.buscarProcedimientosPorVeterinario(veterinarioId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarProcedimientosPorEstado() {
        System.out.print("\nIngrese el estado a buscar: ");
        String estado = scanner.nextLine().trim();
        procedimientoEspecialController.buscarProcedimientosPorEstado(estado);
    }
    
    private void buscarProcedimientosPorTipo() {
        System.out.print("\nIngrese el tipo de procedimiento a buscar: ");
        String tipoProcedimiento = scanner.nextLine().trim();
        procedimientoEspecialController.buscarProcedimientosPorTipo(tipoProcedimiento);
    }
    
    private void actualizarEstadoProcedimiento() {
        System.out.print("\nIngrese el ID del procedimiento: ");
        try {
            int procedimientoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Ingrese el nuevo estado: ");
            String nuevoEstado = scanner.nextLine().trim();

            boolean exito = procedimientoEspecialController.actualizarEstadoProcedimiento(procedimientoId, nuevoEstado);
            if (exito) {
                System.out.println("✅ Estado del procedimiento actualizado exitosamente");
            } else {
                System.out.println("❌ No se pudo actualizar el estado del procedimiento");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void eliminarProcedimiento() {
        System.out.print("\nIngrese el ID del procedimiento a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar este procedimiento? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                boolean exito = procedimientoEspecialController.eliminarProcedimiento(id);
                if (!exito) {
                    System.out.println("❌ No se pudo eliminar el procedimiento");
                }
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}