package com.happyfeet.controller;

import com.happyfeet.model.Dueno;
import com.happyfeet.service.DuenoService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de dueños
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class DuenoController {
    private final DuenoService duenoService;
    
    public DuenoController() throws VeterinariaException {
        this.duenoService = new DuenoService();
    }
    
    /**
     * Registra un nuevo dueño en el sistema
     */
    public boolean registrarDueno(String nombre, String documento, String email) {
        try {
            Dueno dueno = duenoService.registrarDueno(nombre, documento, email);
            System.out.println("✅ Dueño registrado exitosamente: " + dueno);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar dueño: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra un dueño con todos sus datos
     */
    public boolean registrarDuenoCompleto(String nombre, String documento, String direccion,
                                         String telefono, String email, String contactoEmergencia) {
        try {
            Dueno dueno = duenoService.registrarDuenoCompleto(nombre, documento, direccion, 
                                                             telefono, email, contactoEmergencia);
            System.out.println("✅ Dueño registrado exitosamente: " + dueno);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar dueño: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los dueños activos
     */
    public void listarDuenos() {
        try {
            List<Dueno> duenos = duenoService.listarDuenosActivos();
            if (duenos.isEmpty()) {
                System.out.println("No hay dueños registrados.");
            } else {
                System.out.println("\n=== LISTA DE DUEÑOS ===");
                // Usando method reference para mayor legibilidad
                duenos.forEach(System.out::println);
                System.out.println("Total: " + duenos.size() + " dueños registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar dueños: " + e.getMessage());
        }
    }
    
    /**
     * Busca un dueño por su ID
     */
    public void buscarDuenoPorId(Integer id) {
        try {
            Optional<Dueno> dueno = duenoService.buscarDuenoPorId(id);
            if (dueno.isPresent()) {
                System.out.println("Dueño encontrado: " + dueno.get());
            } else {
                System.out.println("No se encontró ningún dueño con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar dueño: " + e.getMessage());
        }
    }
    
    /**
     * Busca dueños por nombre
     */
    public void buscarDuenosPorNombre(String nombre) {
        try {
            List<Dueno> duenos = duenoService.buscarDuenosPorNombre(nombre);
            if (duenos.isEmpty()) {
                System.out.println("No se encontraron dueños con nombre que contenga: " + nombre);
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                duenos.forEach(System.out::println);
                System.out.println("Se encontraron " + duenos.size() + " dueños");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar dueños: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un dueño del sistema (soft delete)
     */
    public boolean eliminarDueno(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Dueno> dueno = duenoService.buscarDuenoPorId(id);
            if (dueno.isPresent()) {
                duenoService.eliminarDueno(id);
                System.out.println("✅ Dueño eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el dueño con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar dueño: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de dueños
     */
    public void mostrarEstadisticas() {
        try {
            long totalDuenos = duenoService.contarDuenosActivos();
            System.out.println("\n=== ESTADÍSTICAS DE DUEÑOS ===");
            System.out.println("Total de dueños registrados: " + totalDuenos);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}
