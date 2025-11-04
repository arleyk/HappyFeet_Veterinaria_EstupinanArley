package com.happyfeet.view;

import com.happyfeet.controller.MascotaAdopcionController;

import java.util.Scanner;

/**
 * Menú para la gestión de mascotas en adopción
 */
public class MascotaAdopcionMenu extends BaseMenu {
    private final MascotaAdopcionController controller;
    
    public MascotaAdopcionMenu(Scanner scanner) {
        super(scanner);
        try {
            this.controller = new MascotaAdopcionController();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el controlador de mascotas en adopción", e);
        }
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE MASCOTAS EN ADOPCIÓN ===");
            System.out.println("1. Registrar mascota para adopción");
            System.out.println("2. Listar todas las mascotas en adopción");
            System.out.println("3. Listar mascotas disponibles");
            System.out.println("4. Buscar mascota en adopción por ID");
            System.out.println("5. Cambiar estado de mascota");
            System.out.println("6. Eliminar mascota en adopción");
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
                registrarMascotaAdopcion();
                break;
            case 2:
                controller.listarMascotasAdopcion();
                break;
            case 3:
                controller.listarMascotasDisponibles();
                break;
            case 4:
                buscarMascotaAdopcionPorId();
                break;
            case 5:
                cambiarEstadoMascota();
                break;
            case 6:
                eliminarMascotaAdopcion();
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
    
    private void registrarMascotaAdopcion() {
        System.out.println("\n--- Registrar Mascota para Adopción ---");
        
        System.out.print("ID de la mascota: ");
        Integer mascotaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("Motivo de ingreso: ");
        String motivoIngreso = scanner.nextLine();
        
        System.out.print("Historia de la mascota: ");
        String historia = scanner.nextLine();
        
        System.out.print("Temperamento: ");
        String temperamento = scanner.nextLine();
        
        System.out.print("Necesidades especiales: ");
        String necesidadesEspeciales = scanner.nextLine();
        
        boolean exito = controller.registrarMascotaAdopcion(mascotaId, motivoIngreso, historia, 
                                                           temperamento, necesidadesEspeciales);
        if (exito) {
            System.out.println("Mascota registrada para adopción exitosamente.");
        } else {
            System.out.println("Error al registrar la mascota para adopción.");
        }
    }
    
    private void buscarMascotaAdopcionPorId() {
        System.out.print("Ingrese el ID de la mascota en adopción: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        controller.buscarMascotaAdopcionPorId(id);
    }
    
    private void cambiarEstadoMascota() {
        System.out.print("Ingrese el ID de la mascota: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.println("Estados disponibles:");
        System.out.println("1. Disponible");
        System.out.println("2. En Proceso");
        System.out.println("3. Adoptada");
        System.out.println("4. Retirada");
        System.out.print("Seleccione el nuevo estado: ");
        
        int estadoOp = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String nuevoEstado;
        switch (estadoOp) {
            case 1: nuevoEstado = "Disponible"; break;
            case 2: nuevoEstado = "En Proceso"; break;
            case 3: nuevoEstado = "Adoptada"; break;
            case 4: nuevoEstado = "Retirada"; break;
            default: 
                System.out.println("Opción de estado inválida.");
                return;
        }
        
        boolean exito = controller.cambiarEstadoMascotaAdopcion(id, nuevoEstado);
        if (exito) {
            System.out.println("Estado actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el estado.");
        }
    }
    
    private void eliminarMascotaAdopcion() {
        System.out.print("Ingrese el ID de la mascota en adopción a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        System.out.print("¿Está seguro de que desea eliminar esta mascota de adopción? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            boolean exito = controller.eliminarMascotaAdopcion(id);
            if (exito) {
                System.out.println("Mascota en adopción eliminada exitosamente.");
            } else {
                System.out.println("Error al eliminar la mascota en adopción.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
}