package com.happyfeet.controller;

import com.happyfeet.model.Proveedor;
import com.happyfeet.service.ProveedorService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de proveedores
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 */
public class ProveedorController {
    private final ProveedorService proveedorService;
    
    public ProveedorController() throws VeterinariaException {
        this.proveedorService = new ProveedorService();
    }
    
    /**
     * Registra un nuevo proveedor en el sistema
     */
    public boolean registrarProveedor(String nombreEmpresa, String contacto, String telefono, 
                                     String email, String direccion, String sitioWeb) {
        try {
            Proveedor proveedor = proveedorService.registrarProveedor(nombreEmpresa, contacto, telefono, 
                                                                     email, direccion, sitioWeb);
            System.out.println("✅ Proveedor registrado exitosamente: " + proveedor);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los proveedores activos
     */
    public void listarProveedores() {
        try {
            List<Proveedor> proveedores = proveedorService.listarProveedoresActivos();
            if (proveedores.isEmpty()) {
                System.out.println("No hay proveedores registrados.");
            } else {
                System.out.println("\n=== LISTA DE PROVEEDORES ===");
                proveedores.forEach(System.out::println);
                System.out.println("Total: " + proveedores.size() + " proveedores registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
    }
    
    /**
     * Busca un proveedor por su ID
     */
    public void buscarProveedorPorId(Integer id) {
        try {
            Optional<Proveedor> proveedor = proveedorService.buscarProveedorPorId(id);
            if (proveedor.isPresent()) {
                System.out.println("Proveedor encontrado: " + proveedor.get());
            } else {
                System.out.println("No se encontró ningún proveedor con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
        }
    }
    
    /**
     * Busca proveedores por nombre
     */
    public void buscarProveedoresPorNombre(String nombre) {
        try {
            List<Proveedor> proveedores = proveedorService.buscarProveedoresPorNombre(nombre);
            if (proveedores.isEmpty()) {
                System.out.println("No se encontraron proveedores con nombre que contenga: " + nombre);
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                proveedores.forEach(System.out::println);
                System.out.println("Se encontraron " + proveedores.size() + " proveedores");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar proveedores: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un proveedor del sistema (soft delete)
     */
    public boolean eliminarProveedor(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Proveedor> proveedor = proveedorService.buscarProveedorPorId(id);
            if (proveedor.isPresent()) {
                proveedorService.eliminarProveedor(id);
                System.out.println("✅ Proveedor eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el proveedor con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de proveedores
     */
    public void mostrarEstadisticas() {
        try {
            long totalProveedores = proveedorService.contarProveedoresActivos();
            System.out.println("\n=== ESTADÍSTICAS DE PROVEEDORES ===");
            System.out.println("Total de proveedores registrados: " + totalProveedores);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}