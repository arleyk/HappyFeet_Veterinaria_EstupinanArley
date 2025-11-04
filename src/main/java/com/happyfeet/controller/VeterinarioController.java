package com.happyfeet.controller;

import com.happyfeet.model.Veterinario;
import com.happyfeet.service.VeterinarioService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de veterinarios
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class VeterinarioController {
    private final VeterinarioService veterinarioService;
    
    public VeterinarioController() throws VeterinariaException {
        this.veterinarioService = new VeterinarioService();
    }
    
    /**
     * Registra un nuevo veterinario en el sistema
     */
    public boolean registrarVeterinario(String nombreCompleto, String documentoIdentidad, String licenciaProfesional) {
        try {
            Veterinario veterinario = veterinarioService.registrarVeterinario(nombreCompleto, documentoIdentidad, licenciaProfesional);
            System.out.println("✅ Veterinario registrado exitosamente: " + veterinario);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar veterinario: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra un veterinario con todos sus datos
     */
    public boolean registrarVeterinarioCompleto(String nombreCompleto, String documentoIdentidad, 
                                               String licenciaProfesional, String especialidad, 
                                               String telefono, String email, String fechaContratacion) {
        try {
            Veterinario veterinario = veterinarioService.registrarVeterinarioCompleto(nombreCompleto, documentoIdentidad, 
                                                                                     licenciaProfesional, especialidad, 
                                                                                     telefono, email, fechaContratacion);
            System.out.println("✅ Veterinario registrado exitosamente: " + veterinario);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar veterinario: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los veterinarios activos
     */
    public void listarVeterinarios() {
        try {
            List<Veterinario> veterinarios = veterinarioService.listarVeterinariosActivos();
            if (veterinarios.isEmpty()) {
                System.out.println("No hay veterinarios registrados.");
            } else {
                System.out.println("\n=== LISTA DE VETERINARIOS ===");
                veterinarios.forEach(System.out::println);
                System.out.println("Total: " + veterinarios.size() + " veterinarios registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar veterinarios: " + e.getMessage());
        }
    }
    
    /**
     * Busca un veterinario por su ID
     */
    public void buscarVeterinarioPorId(Integer id) {
        try {
            Optional<Veterinario> veterinario = veterinarioService.buscarVeterinarioPorId(id);
            if (veterinario.isPresent()) {
                System.out.println("Veterinario encontrado: " + veterinario.get());
            } else {
                System.out.println("No se encontró ningún veterinario con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar veterinario: " + e.getMessage());
        }
    }
    
    /**
     * Busca veterinarios por nombre
     */
    public void buscarVeterinariosPorNombre(String nombre) {
        try {
            List<Veterinario> veterinarios = veterinarioService.buscarVeterinariosPorNombre(nombre);
            if (veterinarios.isEmpty()) {
                System.out.println("No se encontraron veterinarios con nombre que contenga: " + nombre);
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                veterinarios.forEach(System.out::println);
                System.out.println("Se encontraron " + veterinarios.size() + " veterinarios");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar veterinarios: " + e.getMessage());
        }
    }
    
    /**
     * Busca veterinarios por especialidad
     */
    public void buscarVeterinariosPorEspecialidad(String especialidad) {
        try {
            List<Veterinario> veterinarios = veterinarioService.buscarVeterinariosPorEspecialidad(especialidad);
            if (veterinarios.isEmpty()) {
                System.out.println("No se encontraron veterinarios con especialidad: " + especialidad);
            } else {
                System.out.println("\n=== VETERINARIOS POR ESPECIALIDAD ===");
                veterinarios.forEach(System.out::println);
                System.out.println("Total: " + veterinarios.size() + " veterinarios encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar veterinarios por especialidad: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un veterinario del sistema (soft delete)
     */
    public boolean eliminarVeterinario(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Veterinario> veterinario = veterinarioService.buscarVeterinarioPorId(id);
            if (veterinario.isPresent()) {
                veterinarioService.eliminarVeterinario(id);
                System.out.println("✅ Veterinario eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el veterinario con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar veterinario: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de veterinarios
     */
    public void mostrarEstadisticas() {
        try {
            long totalVeterinarios = veterinarioService.contarVeterinariosActivos();
            System.out.println("\n=== ESTADÍSTICAS DE VETERINARIOS ===");
            System.out.println("Total de veterinarios registrados: " + totalVeterinarios);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si un veterinario está activo
     */
    public void verificarVeterinarioActivo(Integer id) {
        try {
            boolean activo = veterinarioService.estaActivo(id);
            if (activo) {
                System.out.println("✅ El veterinario con ID " + id + " está activo");
            } else {
                System.out.println("❌ El veterinario con ID " + id + " no está activo");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al verificar estado del veterinario: " + e.getMessage());
        }
    }
}   