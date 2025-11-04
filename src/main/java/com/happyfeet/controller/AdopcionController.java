package com.happyfeet.controller;

import com.happyfeet.model.Adopcion;
import com.happyfeet.service.AdopcionService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de adopciones
 */
public class AdopcionController {
    private final AdopcionService adopcionService;
    
    public AdopcionController() throws VeterinariaException {
        this.adopcionService = new AdopcionService();
    }
    
    /**
     * Registra una nueva adopción
     */
    public boolean registrarAdopcion(Integer mascotaAdopcionId, Integer duenoId, 
                                    String contratoTexto, String condicionesEspeciales) {
        try {
            Adopcion adopcion = adopcionService.registrarAdopcion(
                mascotaAdopcionId, duenoId, contratoTexto, condicionesEspeciales);
            System.out.println("✅ Adopción registrada exitosamente: " + adopcion);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar adopción: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las adopciones activas
     */
    public void listarAdopciones() {
        try {
            List<Adopcion> adopciones = adopcionService.listarAdopcionesActivas();
            if (adopciones.isEmpty()) {
                System.out.println("No hay adopciones registradas.");
            } else {
                System.out.println("\n=== LISTA DE ADOPCIONES ===");
                adopciones.forEach(System.out::println);
                System.out.println("Total: " + adopciones.size() + " adopciones");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar adopciones: " + e.getMessage());
        }
    }
    
    /**
     * Busca una adopción por su ID
     */
    public void buscarAdopcionPorId(Integer id) {
        try {
            Optional<Adopcion> adopcion = adopcionService.buscarAdopcionPorId(id);
            if (adopcion.isPresent()) {
                System.out.println("Adopción encontrada: " + adopcion.get());
            } else {
                System.out.println("No se encontró ninguna adopción con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar adopción: " + e.getMessage());
        }
    }
    
    /**
     * Busca adopciones por dueño
     */
    public void buscarAdopcionesPorDueno(Integer duenoId) {
        try {
            List<Adopcion> adopciones = adopcionService.buscarAdopcionesPorDueno(duenoId);
            if (adopciones.isEmpty()) {
                System.out.println("No se encontraron adopciones para el dueño con ID: " + duenoId);
            } else {
                System.out.println("\n=== ADOPCIONES DEL DUEÑO ===");
                adopciones.forEach(System.out::println);
                System.out.println("Se encontraron " + adopciones.size() + " adopciones");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar adopciones por dueño: " + e.getMessage());
        }
    }
    
    /**
     * Programa el seguimiento de una adopción
     */
    public boolean programarSeguimiento(Integer adopcionId, LocalDate fechaSeguimiento) {
        try {
            Adopcion adopcion = adopcionService.programarSeguimiento(adopcionId, fechaSeguimiento);
            System.out.println("✅ Seguimiento programado exitosamente: " + adopcion);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al programar seguimiento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una adopción del sistema (soft delete)
     */
    public boolean eliminarAdopcion(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Adopcion> adopcion = adopcionService.buscarAdopcionPorId(id);
            if (adopcion.isPresent()) {
                adopcionService.eliminarAdopcion(id);
                System.out.println("✅ Adopción eliminada exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró la adopción con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar adopción: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Muestra estadísticas de adopciones
     */
    public void mostrarEstadisticas() {
        try {
            long totalAdopciones = adopcionService.contarAdopcionesActivas();
            
            System.out.println("\n=== ESTADÍSTICAS DE ADOPCIONES ===");
            System.out.println("Total de adopciones registradas: " + totalAdopciones);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}