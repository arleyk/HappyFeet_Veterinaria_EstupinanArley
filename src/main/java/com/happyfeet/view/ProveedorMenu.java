package com.happyfeet.view;

import com.happyfeet.controller.ProveedorController;
import java.util.Scanner;

/**
 * Menú para la gestión de proveedores
 */
public class ProveedorMenu extends BaseMenu {
    private final ProveedorController proveedorController;
    
    public ProveedorMenu(Scanner scanner, ProveedorController controller) {
        super(scanner);
        this.proveedorController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE PROVEEDORES ===");
            System.out.println("1. Registrar Nuevo Proveedor");
            System.out.println("2. Listar Todos los Proveedores");
            System.out.println("3. Buscar Proveedor por ID");
            System.out.println("4. Buscar Proveedores por Nombre");
            System.out.println("5. Eliminar Proveedor");
            System.out.println("6. Estadísticas de Proveedores");
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
                registrarNuevoProveedor();
                break;
            case 2:
                proveedorController.listarProveedores();
                break;
            case 3:
                buscarProveedorPorId();
                break;
            case 4:
                buscarProveedoresPorNombre();
                break;
            case 5:
                eliminarProveedor();
                break;
            case 6:
                proveedorController.mostrarEstadisticas();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevoProveedor() {
        System.out.println("\n=== REGISTRAR NUEVO PROVEEDOR ===");

        try {
            System.out.print("Nombre de la empresa: ");
            String nombreEmpresa = scanner.nextLine().trim();

            System.out.print("Nombre del contacto: ");
            String contacto = scanner.nextLine().trim();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Dirección (opcional): ");
            String direccion = scanner.nextLine().trim();

            System.out.print("Sitio web (opcional): ");
            String sitioWeb = scanner.nextLine().trim();

            proveedorController.registrarProveedor(nombreEmpresa, contacto, telefono, email, direccion, sitioWeb);
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarProveedorPorId() {
        System.out.print("\nIngrese el ID del proveedor a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            proveedorController.buscarProveedorPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarProveedoresPorNombre() {
        System.out.print("\nIngrese el nombre o parte del nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        proveedorController.buscarProveedoresPorNombre(nombre);
    }
    
    private void eliminarProveedor() {
        System.out.print("\nIngrese el ID del proveedor a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar este proveedor? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                proveedorController.eliminarProveedor(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}