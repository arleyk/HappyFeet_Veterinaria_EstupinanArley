package com.happyfeet.controller;

import com.happyfeet.model.Inventario;
import com.happyfeet.service.InventarioService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de inventario
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 */
public class InventarioController {
    private final InventarioService inventarioService;
    
    public InventarioController() throws VeterinariaException {
        this.inventarioService = new InventarioService();
    }
    
    /**
     * Registra un nuevo producto en el inventario
     */
    public boolean registrarProducto(String nombreProducto, Integer productoTipoId, String descripcion,
                                    String fabricante, Integer proveedorId, String lote, Integer cantidadStock,
                                    Integer stockMinimo, String unidadMedida, String fechaVencimiento,
                                    Double precioCompra, Double precioVenta, Boolean requiereReceta) {
        try {
            Inventario producto = inventarioService.registrarProducto(nombreProducto, productoTipoId, descripcion,
                                                                     fabricante, proveedorId, lote, cantidadStock,
                                                                     stockMinimo, unidadMedida, fechaVencimiento,
                                                                     precioCompra, precioVenta, requiereReceta);
            System.out.println("✅ Producto registrado exitosamente: " + producto);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los productos activos
     */
    public void listarProductos() {
        try {
            List<Inventario> productos = inventarioService.listarProductosActivos();
            if (productos.isEmpty()) {
                System.out.println("No hay productos registrados.");
            } else {
                System.out.println("\n=== LISTA DE PRODUCTOS ===");
                productos.forEach(System.out::println);
                System.out.println("Total: " + productos.size() + " productos registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
    }
    
    /**
     * Busca un producto por su ID
     */
    public void buscarProductoPorId(Integer id) {
        try {
            Optional<Inventario> producto = inventarioService.buscarProductoPorId(id);
            if (producto.isPresent()) {
                System.out.println("Producto encontrado: " + producto.get());
            } else {
                System.out.println("No se encontró ningún producto con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
    }
    
    /**
     * Busca productos por nombre
     */
    public void buscarProductosPorNombre(String nombre) {
        try {
            List<Inventario> productos = inventarioService.buscarProductosPorNombre(nombre);
            if (productos.isEmpty()) {
                System.out.println("No se encontraron productos con nombre que contenga: " + nombre);
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                productos.forEach(System.out::println);
                System.out.println("Se encontraron " + productos.size() + " productos");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
        }
    }
    
    /**
     * Muestra productos con stock bajo
     */
    public void mostrarProductosConStockBajo() {
        try {
            List<Inventario> productos = inventarioService.obtenerProductosConStockBajo();
            if (productos.isEmpty()) {
                System.out.println("No hay productos con stock bajo.");
            } else {
                System.out.println("\n=== PRODUCTOS CON STOCK BAJO ===");
                productos.forEach(System.out::println);
                System.out.println("Total: " + productos.size() + " productos con stock bajo");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener productos con stock bajo: " + e.getMessage());
        }
    }
    
    /**
     * Muestra productos próximos a vencer
     */
    public void mostrarProductosProximosAVencer() {
        try {
            List<Inventario> productos = inventarioService.obtenerProductosProximosAVencer();
            if (productos.isEmpty()) {
                System.out.println("No hay productos próximos a vencer.");
            } else {
                System.out.println("\n=== PRODUCTOS PRÓXIMOS A VENCER ===");
                productos.forEach(System.out::println);
                System.out.println("Total: " + productos.size() + " productos próximos a vencer");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener productos próximos a vencer: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un producto del sistema (soft delete)
     */
    public boolean eliminarProducto(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Inventario> producto = inventarioService.buscarProductoPorId(id);
            if (producto.isPresent()) {
                inventarioService.eliminarProducto(id);
                System.out.println("✅ Producto eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el producto con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de inventario
     */
    public void mostrarEstadisticas() {
        try {
            long totalProductos = inventarioService.contarProductosActivos();
            List<Inventario> productosStockBajo = inventarioService.obtenerProductosConStockBajo();
            List<Inventario> productosProximosAVencer = inventarioService.obtenerProductosProximosAVencer();
            
            System.out.println("\n=== ESTADÍSTICAS DE INVENTARIO ===");
            System.out.println("Total de productos registrados: " + totalProductos);
            System.out.println("Productos con stock bajo: " + productosStockBajo.size());
            System.out.println("Productos próximos a vencer: " + productosProximosAVencer.size());
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza el stock de un producto
     */
    public boolean actualizarStockProducto(Integer productoId, Integer nuevaCantidad) {
        try {
            inventarioService.actualizarStockProducto(productoId, nuevaCantidad);
            System.out.println("✅ Stock actualizado exitosamente para producto ID: " + productoId);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}