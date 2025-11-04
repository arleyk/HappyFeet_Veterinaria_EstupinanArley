package com.happyfeet.service;

import com.happyfeet.model.ProcedimientoEspecial;
import com.happyfeet.dao.ProcedimientoEspecialDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de procedimientos especiales
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 */
public class ProcedimientoEspecialService {
    private final ProcedimientoEspecialDAO procedimientoEspecialDAO;
    
    public ProcedimientoEspecialService() throws VeterinariaException {
        this.procedimientoEspecialDAO = new ProcedimientoEspecialDAO();
    }
    
    /**
     * Registra un nuevo procedimiento especial en el sistema
     */
    public ProcedimientoEspecial registrarProcedimientoEspecial(Integer mascotaId, Integer veterinarioId,
                                                               String tipoProcedimiento, String nombreProcedimiento,
                                                               LocalDateTime fechaHora, String detalleProcedimiento) 
            throws VeterinariaException {
        
        validarDatosProcedimiento(mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, detalleProcedimiento);
        
        ProcedimientoEspecial procedimiento = new ProcedimientoEspecial(mascotaId, veterinarioId, tipoProcedimiento,
                                                                       nombreProcedimiento, fechaHora, detalleProcedimiento);
        return procedimientoEspecialDAO.save(procedimiento);
    }
    
    /**
     * Registra un procedimiento especial completo con todos los datos
     */
    public ProcedimientoEspecial registrarProcedimientoEspecialCompleto(Integer mascotaId, Integer veterinarioId,
                                                                       String tipoProcedimiento, String nombreProcedimiento,
                                                                       LocalDateTime fechaHora, Integer duracionEstimadaMinutos,
                                                                       String informacionPreoperatoria, String detalleProcedimiento,
                                                                       String complicaciones, String seguimientoPostoperatorio,
                                                                       LocalDateTime proximoControl, String estado, 
                                                                       Double costoProcedimiento) throws VeterinariaException {
        
        validarDatosProcedimiento(mascotaId, veterinarioId, tipoProcedimiento, nombreProcedimiento, fechaHora, detalleProcedimiento);
        
        ProcedimientoEspecial procedimiento = new ProcedimientoEspecial(mascotaId, veterinarioId, tipoProcedimiento,
                                                                       nombreProcedimiento, fechaHora, detalleProcedimiento);
        procedimiento.setDuracionEstimadaMinutos(duracionEstimadaMinutos);
        procedimiento.setInformacionPreoperatoria(informacionPreoperatoria);
        procedimiento.setComplicaciones(complicaciones);
        procedimiento.setSeguimientoPostoperatorio(seguimientoPostoperatorio);
        procedimiento.setProximoControl(proximoControl);
        procedimiento.setEstado(estado);
        procedimiento.setCostoProcedimiento(costoProcedimiento);
        
        return procedimientoEspecialDAO.save(procedimiento);
    }
    
    /**
     * Valida los datos básicos de un procedimiento especial
     */
    private void validarDatosProcedimiento(Integer mascotaId, Integer veterinarioId, String tipoProcedimiento,
                                          String nombreProcedimiento, LocalDateTime fechaHora, String detalleProcedimiento) 
            throws VeterinariaException {
        
        if (mascotaId == null) {
            throw new VeterinariaException("El ID de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (veterinarioId == null) {
            throw new VeterinariaException("El ID del veterinario es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (tipoProcedimiento == null || tipoProcedimiento.trim().isEmpty()) {
            throw new VeterinariaException("El tipo de procedimiento es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (nombreProcedimiento == null || nombreProcedimiento.trim().isEmpty()) {
            throw new VeterinariaException("El nombre del procedimiento es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora == null) {
            throw new VeterinariaException("La fecha y hora del procedimiento son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (detalleProcedimiento == null || detalleProcedimiento.trim().isEmpty()) {
            throw new VeterinariaException("El detalle del procedimiento es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todos los procedimientos especiales activos
     */
    public List<ProcedimientoEspecial> listarProcedimientosEspeciales() throws VeterinariaException {
        return procedimientoEspecialDAO.findAll();
    }
    
    /**
     * Busca un procedimiento especial por su ID
     */
    public Optional<ProcedimientoEspecial> buscarProcedimientoEspecialPorId(Integer id) throws VeterinariaException {
        return procedimientoEspecialDAO.findById(id);
    }
    
    /**
     * Busca procedimientos por ID de mascota
     */
    public List<ProcedimientoEspecial> buscarProcedimientosPorMascota(Integer mascotaId) throws VeterinariaException {
        return procedimientoEspecialDAO.findByMascotaId(mascotaId);
    }
    
    /**
     * Busca procedimientos por ID de veterinario
     */
    public List<ProcedimientoEspecial> buscarProcedimientosPorVeterinario(Integer veterinarioId) throws VeterinariaException {
        return procedimientoEspecialDAO.findByVeterinarioId(veterinarioId);
    }
    
    /**
     * Busca procedimientos por estado
     */
    public List<ProcedimientoEspecial> buscarProcedimientosPorEstado(String estado) throws VeterinariaException {
        return procedimientoEspecialDAO.findByEstado(estado);
    }
    
    /**
     * Busca procedimientos por tipo
     */
    public List<ProcedimientoEspecial> buscarProcedimientosPorTipo(String tipoProcedimiento) throws VeterinariaException {
        return procedimientoEspecialDAO.findByTipo(tipoProcedimiento);
    }
    
    /**
     * Actualiza el estado de un procedimiento
     */
    public boolean actualizarEstadoProcedimiento(Integer id, String nuevoEstado) throws VeterinariaException {
        return procedimientoEspecialDAO.updateEstado(id, nuevoEstado);
    }
    
    /**
     * Actualiza los datos de un procedimiento existente
     */
    public ProcedimientoEspecial actualizarProcedimientoEspecial(ProcedimientoEspecial procedimiento) throws VeterinariaException {
        if (procedimiento.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un procedimiento sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el procedimiento existe
        Optional<ProcedimientoEspecial> procedimientoExistente = procedimientoEspecialDAO.findById(procedimiento.getId());
        if (procedimientoExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el procedimiento con ID: " + procedimiento.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return procedimientoEspecialDAO.save(procedimiento);
    }
    
    /**
     * Elimina un procedimiento (soft delete)
     */
    public void eliminarProcedimiento(Integer id) throws VeterinariaException {
        procedimientoEspecialDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de procedimientos activos
     */
    public long contarProcedimientosEspeciales() throws VeterinariaException {
        return procedimientoEspecialDAO.count();
    }
    
    /**
     * Obtiene estadísticas por estado
     */
    public long contarProcedimientosPorEstado(String estado) throws VeterinariaException {
        return procedimientoEspecialDAO.countByEstado(estado);
    }
}