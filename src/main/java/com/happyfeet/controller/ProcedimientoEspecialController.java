package com.happyfeet.controller;

import com.happyfeet.model.ProcedimientoEspecial;
import com.happyfeet.service.ProcedimientoEspecialService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de procedimientos especiales
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class ProcedimientoEspecialController {
    private final ProcedimientoEspecialService procedimientoEspecialService;
    
    public ProcedimientoEspecialController() throws VeterinariaException {
        this.procedimientoEspecialService = new ProcedimientoEspecialService();
    }
    
    /**
     * Registra un nuevo procedimiento especial en el sistema
     */
    public boolean registrarProcedimientoEspecial(Integer mascotaId, Integer veterinarioId,
                                                 String tipoProcedimiento, String nombreProcedimiento,
                                                 LocalDateTime fechaHora, String detalleProcedimiento) {
        try {
            ProcedimientoEspecial procedimiento = procedimientoEspecialService.registrarProcedimientoEspecial(
                mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, detalleProcedimiento);
            System.out.println("✅ Procedimiento especial registrado exitosamente: " + procedimiento);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar procedimiento especial: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra un procedimiento especial completo con todos los datos
     */
    public boolean registrarProcedimientoEspecialCompleto(Integer mascotaId, Integer veterinarioId,
                                                         String tipoProcedimiento, String nombreProcedimiento,
                                                         LocalDateTime fechaHora, Integer duracionEstimadaMinutos,
                                                         String informacionPreoperatoria, String detalleProcedimiento,
                                                         String complicaciones, String seguimientoPostoperatorio,
                                                         LocalDateTime proximoControl, String estado, 
                                                         Double costoProcedimiento) {
        try {
            ProcedimientoEspecial procedimiento = procedimientoEspecialService.registrarProcedimientoEspecialCompleto(
                mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, duracionEstimadaMinutos,
                informacionPreoperatoria, detalleProcedimiento, complicaciones, seguimientoPostoperatorio,
                proximoControl, estado, costoProcedimiento);
            System.out.println("✅ Procedimiento especial registrado exitosamente: " + procedimiento);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar procedimiento especial: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los procedimientos especiales activos
     */
    public void listarProcedimientosEspeciales() {
        try {
            List<ProcedimientoEspecial> procedimientos = procedimientoEspecialService.listarProcedimientosEspeciales();
            if (procedimientos.isEmpty()) {
                System.out.println("No hay procedimientos especiales registrados.");
            } else {
                System.out.println("\n=== LISTA DE PROCEDIMIENTOS ESPECIALES ===");
                procedimientos.forEach(System.out::println);
                System.out.println("Total: " + procedimientos.size() + " procedimientos registrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar procedimientos especiales: " + e.getMessage());
        }
    }
    
    /**
     * Busca un procedimiento especial por su ID
     */
    public void buscarProcedimientoEspecialPorId(Integer id) {
        try {
            Optional<ProcedimientoEspecial> procedimiento = procedimientoEspecialService.buscarProcedimientoEspecialPorId(id);
            if (procedimiento.isPresent()) {
                System.out.println("Procedimiento especial encontrado: " + procedimiento.get());
            } else {
                System.out.println("No se encontró ningún procedimiento especial con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar procedimiento especial: " + e.getMessage());
        }
    }
    
    /**
     * Busca procedimientos por mascota
     */
    public void buscarProcedimientosPorMascota(Integer mascotaId) {
        try {
            List<ProcedimientoEspecial> procedimientos = procedimientoEspecialService.buscarProcedimientosPorMascota(mascotaId);
            if (procedimientos.isEmpty()) {
                System.out.println("No se encontraron procedimientos para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n=== PROCEDIMIENTOS DE LA MASCOTA ===");
                procedimientos.forEach(System.out::println);
                System.out.println("Total: " + procedimientos.size() + " procedimientos encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar procedimientos por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Busca procedimientos por veterinario
     */
    public void buscarProcedimientosPorVeterinario(Integer veterinarioId) {
        try {
            List<ProcedimientoEspecial> procedimientos = procedimientoEspecialService.buscarProcedimientosPorVeterinario(veterinarioId);
            if (procedimientos.isEmpty()) {
                System.out.println("No se encontraron procedimientos para el veterinario con ID: " + veterinarioId);
            } else {
                System.out.println("\n=== PROCEDIMIENTOS DEL VETERINARIO ===");
                procedimientos.forEach(System.out::println);
                System.out.println("Total: " + procedimientos.size() + " procedimientos encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar procedimientos por veterinario: " + e.getMessage());
        }
    }
    
    /**
     * Busca procedimientos por estado
     */
    public void buscarProcedimientosPorEstado(String estado) {
        try {
            List<ProcedimientoEspecial> procedimientos = procedimientoEspecialService.buscarProcedimientosPorEstado(estado);
            if (procedimientos.isEmpty()) {
                System.out.println("No se encontraron procedimientos con estado: " + estado);
            } else {
                System.out.println("\n=== PROCEDIMIENTOS POR ESTADO ===");
                procedimientos.forEach(System.out::println);
                System.out.println("Total: " + procedimientos.size() + " procedimientos encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar procedimientos por estado: " + e.getMessage());
        }
    }
    
    /**
     * Busca procedimientos por tipo
     */
    public void buscarProcedimientosPorTipo(String tipoProcedimiento) {
        try {
            List<ProcedimientoEspecial> procedimientos = procedimientoEspecialService.buscarProcedimientosPorTipo(tipoProcedimiento);
            if (procedimientos.isEmpty()) {
                System.out.println("No se encontraron procedimientos de tipo: " + tipoProcedimiento);
            } else {
                System.out.println("\n=== PROCEDIMIENTOS POR TIPO ===");
                procedimientos.forEach(System.out::println);
                System.out.println("Total: " + procedimientos.size() + " procedimientos encontrados");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar procedimientos por tipo: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza el estado de un procedimiento
     */
    public boolean actualizarEstadoProcedimiento(Integer id, String nuevoEstado) {
        try {
            boolean exito = procedimientoEspecialService.actualizarEstadoProcedimiento(id, nuevoEstado);
            if (exito) {
                System.out.println("✅ Estado del procedimiento actualizado exitosamente");
            } else {
                System.out.println("❌ No se pudo actualizar el estado del procedimiento");
            }
            return exito;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar estado del procedimiento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de procedimientos especiales
     */
    public void mostrarEstadisticas() {
        try {
            long totalProcedimientos = procedimientoEspecialService.contarProcedimientosEspeciales();
            long programados = procedimientoEspecialService.contarProcedimientosPorEstado("Programado");
            long enProceso = procedimientoEspecialService.contarProcedimientosPorEstado("En Proceso");
            long finalizados = procedimientoEspecialService.contarProcedimientosPorEstado("Finalizado");
            long cancelados = procedimientoEspecialService.contarProcedimientosPorEstado("Cancelado");
            
            System.out.println("\n=== ESTADÍSTICAS DE PROCEDIMIENTOS ESPECIALES ===");
            System.out.println("Total de procedimientos registrados: " + totalProcedimientos);
            System.out.println("Procedimientos programados: " + programados);
            System.out.println("Procedimientos en proceso: " + enProceso);
            System.out.println("Procedimientos finalizados: " + finalizados);
            System.out.println("Procedimientos cancelados: " + cancelados);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un procedimiento del sistema (soft delete)
     */
    public boolean eliminarProcedimiento(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<ProcedimientoEspecial> procedimiento = procedimientoEspecialService.buscarProcedimientoEspecialPorId(id);
            if (procedimiento.isPresent()) {
                procedimientoEspecialService.eliminarProcedimiento(id);
                System.out.println("✅ Procedimiento eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el procedimiento con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar procedimiento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza un procedimiento existente
     */
    public boolean actualizarProcedimientoEspecial(ProcedimientoEspecial procedimiento) {
        try {
            ProcedimientoEspecial procedimientoActualizado = procedimientoEspecialService.actualizarProcedimientoEspecial(procedimiento);
            System.out.println("✅ Procedimiento actualizado exitosamente: " + procedimientoActualizado);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar procedimiento: " + e.getMessage());
            return false;
        }
    }
}