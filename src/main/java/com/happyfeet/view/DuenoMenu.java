package com.happyfeet.view;

import com.happyfeet.controller.DuenoController;
import java.util.Scanner;

/**
 * Menú para la gestión de dueños de mascotas
 */
public class DuenoMenu extends BaseMenu {
    private final DuenoController duenoController;
    
    public DuenoMenu(Scanner scanner, DuenoController controller) {
        super(scanner);
        this.duenoController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE DUEÑOS ===");
            System.out.println("1. Registrar Nuevo Dueño");
            System.out.println("2. Listar Todos los Dueños");
            System.out.println("3. Buscar Dueño por ID");
            System.out.println("4. Buscar Dueños por Nombre");
            System.out.println("5. Eliminar Dueño");
            System.out.println("6. Estadísticas de Dueños");
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
                registrarNuevoDueño();
                break;
            case 2:
                duenoController.listarDuenos();
                break;
            case 3:
                buscarDueñoPorId();
                break;
            case 4:
                buscarDueñosPorNombre();
                break;
            case 5:
                eliminarDueño();
                break;
            case 6:
                duenoController.mostrarEstadisticas();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevoDueño() {
        System.out.println("\n=== REGISTRAR NUEVO DUEÑO ===");

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Documento de identidad: ");
        String documento = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("¿Desea ingresar datos adicionales? (s/n): ");
        String respuesta = scanner.nextLine().trim();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine().trim();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();

            System.out.print("Contacto de emergencia: ");
            String contactoEmergencia = scanner.nextLine().trim();

            duenoController.registrarDuenoCompleto(nombre, documento, direccion, 
                                                  telefono, email, contactoEmergencia);
        } else {
            duenoController.registrarDueno(nombre, documento, email);
        }
    }
    
    private void buscarDueñoPorId() {
        System.out.print("\nIngrese el ID del dueño a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            duenoController.buscarDuenoPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarDueñosPorNombre() {
        System.out.print("\nIngrese el nombre o parte del nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        duenoController.buscarDuenosPorNombre(nombre);
    }
    
    private void eliminarDueño() {
        System.out.print("\nIngrese el ID del dueño a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar este dueño? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                duenoController.eliminarDueno(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}