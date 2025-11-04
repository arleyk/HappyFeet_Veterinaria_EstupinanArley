package com.happyfeet.view;

import com.happyfeet.controller.VeterinarioController;
import java.util.Scanner;

/**
 * Menú para la gestión de veterinarios
 */
public class VeterinarioMenu extends BaseMenu {
    private final VeterinarioController veterinarioController;
    
    public VeterinarioMenu(Scanner scanner, VeterinarioController controller) {
        super(scanner);
        this.veterinarioController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE VETERINARIOS ===");
            System.out.println("1. Registrar Nuevo Veterinario");
            System.out.println("2. Registrar Veterinario Completo");
            System.out.println("3. Listar Veterinarios Activos");
            System.out.println("4. Buscar Veterinario por ID");
            System.out.println("5. Buscar Veterinarios por Nombre");
            System.out.println("6. Buscar Veterinarios por Especialidad");
            System.out.println("7. Eliminar Veterinario");
            System.out.println("8. Estadísticas de Veterinarios");
            System.out.println("9. Verificar Veterinario Activo");
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
                registrarNuevoVeterinario();
                break;
            case 2:
                registrarVeterinarioCompleto();
                break;
            case 3:
                veterinarioController.listarVeterinarios();
                break;
            case 4:
                buscarVeterinarioPorId();
                break;
            case 5:
                buscarVeterinariosPorNombre();
                break;
            case 6:
                buscarVeterinariosPorEspecialidad();
                break;
            case 7:
                eliminarVeterinario();
                break;
            case 8:
                veterinarioController.mostrarEstadisticas();
                break;
            case 9:
                verificarVeterinarioActivo();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevoVeterinario() {
        System.out.println("\n=== REGISTRAR NUEVO VETERINARIO ===");

        try {
            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine().trim();

            System.out.print("Documento de identidad: ");
            String documentoIdentidad = scanner.nextLine().trim();

            System.out.print("Licencia profesional: ");
            String licenciaProfesional = scanner.nextLine().trim();

            boolean exito = veterinarioController.registrarVeterinario(nombreCompleto, documentoIdentidad, licenciaProfesional);
            if (exito) {
                System.out.println("✅ Veterinario registrado exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar el veterinario");
            }
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void registrarVeterinarioCompleto() {
        System.out.println("\n=== REGISTRAR VETERINARIO COMPLETO ===");

        try {
            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine().trim();

            System.out.print("Documento de identidad: ");
            String documentoIdentidad = scanner.nextLine().trim();

            System.out.print("Licencia profesional: ");
            String licenciaProfesional = scanner.nextLine().trim();

            System.out.print("Especialidad: ");
            String especialidad = scanner.nextLine().trim();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Fecha de contratación (YYYY-MM-DD): ");
            String fechaContratacion = scanner.nextLine().trim();

            boolean exito = veterinarioController.registrarVeterinarioCompleto(nombreCompleto, documentoIdentidad, 
                                                                              licenciaProfesional, especialidad, 
                                                                              telefono, email, fechaContratacion);
            if (exito) {
                System.out.println("✅ Veterinario registrado exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar el veterinario");
            }
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarVeterinarioPorId() {
        System.out.print("\nIngrese el ID del veterinario a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            veterinarioController.buscarVeterinarioPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarVeterinariosPorNombre() {
        System.out.print("\nIngrese el nombre o parte del nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        veterinarioController.buscarVeterinariosPorNombre(nombre);
    }
    
    private void buscarVeterinariosPorEspecialidad() {
        System.out.print("\nIngrese la especialidad a buscar: ");
        String especialidad = scanner.nextLine().trim();
        veterinarioController.buscarVeterinariosPorEspecialidad(especialidad);
    }
    
    private void eliminarVeterinario() {
        System.out.print("\nIngrese el ID del veterinario a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar este veterinario? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                boolean exito = veterinarioController.eliminarVeterinario(id);
                if (!exito) {
                    System.out.println("❌ No se pudo eliminar el veterinario");
                }
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void verificarVeterinarioActivo() {
        System.out.print("\nIngrese el ID del veterinario a verificar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            veterinarioController.verificarVeterinarioActivo(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}