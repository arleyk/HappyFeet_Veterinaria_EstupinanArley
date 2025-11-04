package com.happyfeet.controller;

import com.happyfeet.model.HistorialMedicoEspecial;
import com.happyfeet.service.HistorialMedicoService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión del historial médico
 * Coordina entre la vista y el servicio para el historial médico
 */
public class HistorialMedicoController {
    private final HistorialMedicoService historialMedicoService;
    
    public HistorialMedicoController() throws VeterinariaException {
        this.historialMedicoService = new HistorialMedicoService();
    }
    
    /**
     * Registra un nuevo evento en el historial médico
     */
    public boolean registrarHistorial(Integer mascotaId, LocalDate fechaEvento, Integer eventoTipoId,
                                     String descripcion, String diagnostico, String tratamientoRecomendado,
                                     Integer veterinarioId, Integer consultaId, Integer procedimientoId) {
        try {
            HistorialMedicoEspecial historial = historialMedicoService.registrarHistorial(
                mascotaId, fechaEvento, eventoTipoId, descripcion, diagnostico, 
                tratamientoRecomendado, veterinarioId, consultaId, procedimientoId
            );
            System.out.println("✅ Historial médico registrado exitosamente: " + historial);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar historial médico: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los historiales médicos activos
     */
    public void listarHistoriales() {
        try {
            List<HistorialMedicoEspecial> historiales = historialMedicoService.listarHistorialesActivos();
            if (historiales.isEmpty()) {
                System.out.println("No hay historiales médicos registrados.");
            } else {
                System.out.println("\n=== LISTA DE HISTORIALES MÉDICOS ===");
                historiales.forEach(System.out::println);
                System.out.println("Total: " + historiales.size() + " historiales registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar historiales médicos: " + e.getMessage());
        }
    }
    
    /**
     * Busca un historial médico por su ID
     */
    public void buscarHistorialPorId(Integer id) {
        try {
            Optional<HistorialMedicoEspecial> historial = historialMedicoService.buscarHistorialPorId(id);
            if (historial.isPresent()) {
                System.out.println("Historial médico encontrado: " + historial.get());
            } else {
                System.out.println("No se encontró ningún historial médico con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar historial médico: " + e.getMessage());
        }
    }
    
    /**
     * Busca historiales médicos por ID de mascota
     */
    public void buscarHistorialesPorMascota(Integer mascotaId) {
        try {
            List<HistorialMedicoEspecial> historiales = historialMedicoService.buscarHistorialesPorMascota(mascotaId);
            if (historiales.isEmpty()) {
                System.out.println("No se encontraron historiales médicos para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n=== HISTORIALES MÉDICOS DE LA MASCOTA ID: " + mascotaId + " ===");
                historiales.forEach(System.out::println);
                System.out.println("Total: " + historiales.size() + " historiales encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar historiales por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Busca historiales médicos por tipo de evento
     */
    public void buscarHistorialesPorEventoTipo(Integer eventoTipoId) {
        try {
            List<HistorialMedicoEspecial> historiales = historialMedicoService.buscarHistorialesPorEventoTipo(eventoTipoId);
            if (historiales.isEmpty()) {
                System.out.println("No se encontraron historiales médicos para el tipo de evento con ID: " + eventoTipoId);
            } else {
                System.out.println("\n=== HISTORIALES MÉDICOS DEL TIPO DE EVENTO ID: " + eventoTipoId + " ===");
                historiales.forEach(System.out::println);
                System.out.println("Total: " + historiales.size() + " historiales encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar historiales por tipo de evento: " + e.getMessage());
        }
    }
    
    /**
     * Busca historiales médicos por rango de fechas
     */
    public void buscarHistorialesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            List<HistorialMedicoEspecial> historiales = historialMedicoService.buscarHistorialesPorRangoFechas(fechaInicio, fechaFin);
            if (historiales.isEmpty()) {
                System.out.println("No se encontraron historiales médicos en el rango de fechas: " + 
                                 fechaInicio + " a " + fechaFin);
            } else {
                System.out.println("\n=== HISTORIALES MÉDICOS DEL " + fechaInicio + " AL " + fechaFin + " ===");
                historiales.forEach(System.out::println);
                System.out.println("Total: " + historiales.size() + " historiales encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar historiales por rango de fechas: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un historial médico del sistema (soft delete)
     */
    public boolean eliminarHistorial(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<HistorialMedicoEspecial> historial = historialMedicoService.buscarHistorialPorId(id);
            if (historial.isPresent()) {
                historialMedicoService.eliminarHistorial(id);
                System.out.println("✅ Historial médico eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el historial médico con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar historial médico: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de historiales médicos
     */
    public void mostrarEstadisticas() {
        try {
            long totalHistoriales = historialMedicoService.contarHistorialesActivos();
            System.out.println("\n=== ESTADÍSTICAS DE HISTORIALES MÉDICOS ===");
            System.out.println("Total de historiales médicos registrados: " + totalHistoriales);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}