package com.happyfeet.service;

import com.happyfeet.model.HistorialMedicoEspecial;
import com.happyfeet.dao.HistorialMedicoDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión del historial médico
 * Contiene la lógica de negocio para las operaciones del historial médico
 */
public class HistorialMedicoService {
    private final HistorialMedicoDAO historialMedicoDAO;
    
    public HistorialMedicoService() throws VeterinariaException {
        this.historialMedicoDAO = new HistorialMedicoDAO();
    }
    
    /**
     * Registra un nuevo evento en el historial médico
     */
    public HistorialMedicoEspecial registrarHistorial(Integer mascotaId, LocalDate fechaEvento, 
                                                     Integer eventoTipoId, String descripcion, 
                                                     String diagnostico, String tratamientoRecomendado,
                                                     Integer veterinarioId, Integer consultaId, 
                                                     Integer procedimientoId) throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosHistorial(mascotaId, fechaEvento, eventoTipoId, descripcion);
        
        HistorialMedicoEspecial historial = new HistorialMedicoEspecial();
        historial.setMascotaId(mascotaId);
        historial.setFechaEvento(fechaEvento);
        historial.setEventoTipoId(eventoTipoId);
        historial.setDescripcion(descripcion);
        historial.setDiagnostico(diagnostico);
        historial.setTratamientoRecomendado(tratamientoRecomendado);
        historial.setVeterinarioId(veterinarioId);
        historial.setConsultaId(consultaId);
        historial.setProcedimientoId(procedimientoId);
        
        return historialMedicoDAO.save(historial);
    }
    
    /**
     * Valida los datos básicos del historial médico
     */
    private void validarDatosHistorial(Integer mascotaId, LocalDate fechaEvento, 
                                      Integer eventoTipoId, String descripcion) throws VeterinariaException {
        
        if (mascotaId == null) {
            throw new VeterinariaException("El ID de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaEvento == null) {
            throw new VeterinariaException("La fecha del evento es obligatoria", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaEvento.isAfter(LocalDate.now())) {
            throw new VeterinariaException("La fecha del evento no puede ser futura", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (eventoTipoId == null) {
            throw new VeterinariaException("El tipo de evento es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new VeterinariaException("La descripción del evento es obligatoria", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todos los historiales médicos activos
     */
    public List<HistorialMedicoEspecial> listarHistorialesActivos() throws VeterinariaException {
        return historialMedicoDAO.findAll();
    }
    
    /**
     * Busca un historial médico por su ID
     */
    public Optional<HistorialMedicoEspecial> buscarHistorialPorId(Integer id) throws VeterinariaException {
        return historialMedicoDAO.findById(id);
    }
    
    /**
     * Busca historiales médicos por ID de mascota
     */
    public List<HistorialMedicoEspecial> buscarHistorialesPorMascota(Integer mascotaId) throws VeterinariaException {
        return historialMedicoDAO.findByMascotaId(mascotaId);
    }
    
    /**
     * Busca historiales médicos por tipo de evento
     */
    public List<HistorialMedicoEspecial> buscarHistorialesPorEventoTipo(Integer eventoTipoId) throws VeterinariaException {
        return historialMedicoDAO.findByEventoTipoId(eventoTipoId);
    }
    
    /**
     * Busca historiales médicos por rango de fechas
     */
    public List<HistorialMedicoEspecial> buscarHistorialesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        return historialMedicoDAO.findByFechaRange(Date.valueOf(fechaInicio), Date.valueOf(fechaFin));
    }
    
    /**
     * Actualiza un historial médico existente
     */
    public HistorialMedicoEspecial actualizarHistorial(HistorialMedicoEspecial historial) throws VeterinariaException {
        if (historial.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un historial sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el historial existe
        Optional<HistorialMedicoEspecial> historialExistente = historialMedicoDAO.findById(historial.getId());
        if (historialExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el historial con ID: " + historial.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return historialMedicoDAO.save(historial);
    }
    
    /**
     * Elimina un historial médico (soft delete)
     */
    public void eliminarHistorial(Integer id) throws VeterinariaException {
        historialMedicoDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de historiales activos
     */
    public long contarHistorialesActivos() throws VeterinariaException {
        return historialMedicoDAO.count();
    }
}