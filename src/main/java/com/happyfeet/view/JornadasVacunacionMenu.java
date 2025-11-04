package com.happyfeet.view;

import com.happyfeet.controller.JornadasVacunacionController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Menú para la gestión de jornadas de vacunación
 */
public class JornadasVacunacionMenu extends BaseMenu {
    private final JornadasVacunacionController controller;
    
    public JornadasVacunacionMenu(Scanner scanner) {
        super(scanner);
        try {
            this.controller = new JornadasVacunacionController();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el controlador de jornadas de vacunación", e);
        }
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE JORNADAS DE VACUNACIÓN ===");
            System.out.println("1. Registrar jornada de vacunación");
            System.out.println("2. Listar todas las jornadas");
            System.out.println("3. Listar jornadas planificadas");
            System.out.println("4. Buscar jornada por ID");
            System.out.println("5. Buscar jornadas por estado");
            System.out.println("6. Cambiar estado de jornada");
            System.out.println("7. Eliminar jornada");
            System.out.println("8. Mostrar estadísticas");
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
                registrarJornada();
                break;
            case 2:
                controller.listarJornadas();
                break;
            case 3:
                controller.listarJornadasPlanificadas();
                break;
            case 4:
                buscarJornadaPorId();
                break;
            case 5:
                buscarJornadasPorEstado();
                break;
            case 6:
                cambiarEstadoJornada();
                break;
            case 7:
                eliminarJornada();
                break;
            case 8:
                controller.mostrarEstadisticas();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente nuevamente.");
        }
    }
    
    private void registrarJornada() {
        System.out.println("\n--- Registrar Jornada de Vacunación ---");
        
        System.out.print("Nombre de la jornada: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr);
        
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        
        System.out.println("Estados disponibles:");
        System.out.println("1. Planificada");
        System.out.println("2. En Curso");
        System.out.println("3. Finalizada");
        System.out.println("4. Cancelada");
        System.out.print("Seleccione el estado: ");
        int estadoOp = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String estado;
        switch (estadoOp) {
            case 1: estado = "Planificada"; break;
            case 2: estado = "En Curso"; break;
            case 3: estado = "Finalizada"; break;
            case 4: estado = "Cancelada"; break;
            default: 
                System.out.println("Opción de estado inválida.");
                return;
        }
        
        System.out.print("Capacidad máxima: ");
        Integer capacidadMaxima = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Hora de inicio (HH:MM) [opcional, enter para omitir]: ");
        String horaInicioStr = scanner.nextLine();
        LocalTime horaInicio = horaInicioStr.isEmpty() ? null : LocalTime.parse(horaInicioStr);
        
        System.out.print("Hora de fin (HH:MM) [opcional, enter para omitir]: ");
        String horaFinStr = scanner.nextLine();
        LocalTime horaFin = horaFinStr.isEmpty() ? null : LocalTime.parse(horaFinStr);
        
        boolean exito = controller.registrarJornada(nombre, fecha, ubicacion, estado, capacidadMaxima, descripcion, horaInicio, horaFin);
        if (exito) {
            System.out.println("Jornada registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la jornada.");
        }
    }
    
    private void buscarJornadaPorId() {
        System.out.print("Ingrese el ID de la jornada: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarJornadaPorId(id);
    }
    
    private void buscarJornadasPorEstado() {
        System.out.println("Estados disponibles:");
        System.out.println("1. Planificada");
        System.out.println("2. En Curso");
        System.out.println("3. Finalizada");
        System.out.println("4. Cancelada");
        System.out.print("Seleccione el estado: ");
        int estadoOp = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String estado;
        switch (estadoOp) {
            case 1: estado = "Planificada"; break;
            case 2: estado = "En Curso"; break;
            case 3: estado = "Finalizada"; break;
            case 4: estado = "Cancelada"; break;
            default: 
                System.out.println("Opción de estado inválida.");
                return;
        }
        
        controller.buscarJornadasPorEstado(estado);
    }
    
    private void cambiarEstadoJornada() {
        System.out.print("Ingrese el ID de la jornada: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.println("Nuevos estados disponibles:");
        System.out.println("1. Planificada");
        System.out.println("2. En Curso");
        System.out.println("3. Finalizada");
        System.out.println("4. Cancelada");
        System.out.print("Seleccione el nuevo estado: ");
        int estadoOp = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String nuevoEstado;
        switch (estadoOp) {
            case 1: nuevoEstado = "Planificada"; break;
            case 2: nuevoEstado = "En Curso"; break;
            case 3: nuevoEstado = "Finalizada"; break;
            case 4: nuevoEstado = "Cancelada"; break;
            default: 
                System.out.println("Opción de estado inválida.");
                return;
        }
        
        boolean exito = controller.cambiarEstadoJornada(id, nuevoEstado);
        if (exito) {
            System.out.println("Estado actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el estado.");
        }
    }
    
    private void eliminarJornada() {
        System.out.print("Ingrese el ID de la jornada a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("¿Está seguro de que desea eliminar esta jornada? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            boolean exito = controller.eliminarJornada(id);
            if (exito) {
                System.out.println("Jornada eliminada exitosamente.");
            } else {
                System.out.println("Error al eliminar la jornada.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
}