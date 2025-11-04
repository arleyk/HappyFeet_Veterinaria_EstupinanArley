package com.happyfeet.view;

import com.happyfeet.controller.InventarioController;
import java.util.Scanner;

/**
 * Menú para la gestión de inventario
 */
public class InventarioMenu extends BaseMenu {
    private final InventarioController inventarioController;
    
    public InventarioMenu(Scanner scanner, InventarioController controller) {
        super(scanner);
        this.inventarioController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE INVENTARIO ===");
            System.out.println("1. Registrar Nuevo Producto");
            System.out.println("2. Listar Todos los Productos");
            System.out.println("3. Buscar Producto por ID");
            System.out.println("4. Buscar Productos por Nombre");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Estadísticas de Inventario");
            System.out.println("7. Productos con Stock Bajo");
            System.out.println("8. Productos Próximos a Vencer");
            System.out.println("9. Actualizar Stock de Producto");
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
                registrarNuevoProducto();
                break;
            case 2:
                inventarioController.listarProductos();
                break;
            case 3:
                buscarProductoPorId();
                break;
            case 4:
                buscarProductosPorNombre();
                break;
            case 5:
                eliminarProducto();
                break;
            case 6:
                inventarioController.mostrarEstadisticas();
                break;
            case 7:
                inventarioController.mostrarProductosConStockBajo();
                break;
            case 8:
                inventarioController.mostrarProductosProximosAVencer();
                break;
            case 9:
                actualizarStockProducto();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevoProducto() {
        System.out.println("\n=== REGISTRAR NUEVO PRODUCTO ===");

        try {
            System.out.print("Nombre del producto: ");
            String nombreProducto = scanner.nextLine().trim();

            System.out.print("ID del tipo de producto: ");
            Integer productoTipoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine().trim();

            System.out.print("Fabricante: ");
            String fabricante = scanner.nextLine().trim();

            System.out.print("ID del proveedor (opcional, presione Enter para omitir): ");
            String proveedorIdStr = scanner.nextLine().trim();
            Integer proveedorId = proveedorIdStr.isEmpty() ? null : Integer.parseInt(proveedorIdStr);

            System.out.print("Lote: ");
            String lote = scanner.nextLine().trim();

            System.out.print("Cantidad en stock: ");
            Integer cantidadStock = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Stock mínimo: ");
            Integer stockMinimo = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Unidad de medida: ");
            String unidadMedida = scanner.nextLine().trim();

            System.out.print("Fecha de vencimiento (YYYY-MM-DD, opcional): ");
            String fechaVencimiento = scanner.nextLine().trim();

            System.out.print("Precio de compra (opcional): ");
            String precioCompraStr = scanner.nextLine().trim();
            Double precioCompra = precioCompraStr.isEmpty() ? null : Double.parseDouble(precioCompraStr);

            System.out.print("Precio de venta: ");
            Double precioVenta = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("¿Requiere receta? (s/n): ");
            String requiereRecetaStr = scanner.nextLine().trim();
            Boolean requiereReceta = requiereRecetaStr.equalsIgnoreCase("s");

            inventarioController.registrarProducto(nombreProducto, productoTipoId, descripcion, fabricante,
                                                  proveedorId, lote, cantidadStock, stockMinimo, unidadMedida,
                                                  fechaVencimiento, precioCompra, precioVenta, requiereReceta);
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarProductoPorId() {
        System.out.print("\nIngrese el ID del producto a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            inventarioController.buscarProductoPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarProductosPorNombre() {
        System.out.print("\nIngrese el nombre o parte del nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        inventarioController.buscarProductosPorNombre(nombre);
    }
    
    private void eliminarProducto() {
        System.out.print("\nIngrese el ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("¿Está seguro de que desea eliminar este producto? (s/n): ");
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                inventarioController.eliminarProducto(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void actualizarStockProducto() {
        System.out.println("\n=== ACTUALIZAR STOCK DE PRODUCTO ===");
        try {
            System.out.print("ID del producto: ");
            Integer productoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nueva cantidad en stock: ");
            Integer nuevaCantidad = Integer.parseInt(scanner.nextLine().trim());

            inventarioController.actualizarStockProducto(productoId, nuevaCantidad);
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los IDs y cantidades deben ser números válidos.");
        }
    }
}