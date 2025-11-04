package com.happyfeet.service;

import com.happyfeet.model.Cita;
import com.happyfeet.dao.CitaDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de citas
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 * Aplica el principio de responsabilidad única (SRP)
 */
public class CitaService {
    private final CitaDAO citaDAO;
    
    public CitaService() throws VeterinariaException {
        this.citaDAO = new CitaDAO();
    }
    
    /**
     * Registra una nueva cita en el sistema
     * Aplica validaciones de negocio antes de guardar
     */
    public Cita registrarCita(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora, 
                             String motivo, Integer estadoId) throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosCita(mascotaId, fechaHora, motivo, estadoId);
        
        // Verificar disponibilidad del veterinario
        if (veterinarioId != null) {
            verificarDisponibilidadVeterinario(veterinarioId, fechaHora);
        }
        
        Cita nuevaCita = new Cita(mascotaId, veterinarioId, fechaHora, motivo, estadoId);
        return citaDAO.save(nuevaCita);
    }
    
    /**
     * Registra una cita completa con todos los datos
     */
    public Cita registrarCitaCompleta(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora,
                                     String motivo, Integer estadoId, String observaciones) throws VeterinariaException {
        
        validarDatosCita(mascotaId, fechaHora, motivo, estadoId);
        
        // Verificar disponibilidad del veterinario
        if (veterinarioId != null) {
            verificarDisponibilidadVeterinario(veterinarioId, fechaHora);
        }
        
        Cita cita = new Cita(mascotaId, veterinarioId, fechaHora, motivo, estadoId);
        cita.setObservaciones(observaciones);
        
        return citaDAO.save(cita);
    }
    
    /**
     * Valida los datos básicos de una cita
     */
    private void validarDatosCita(Integer mascotaId, LocalDateTime fechaHora, String motivo, Integer estadoId) 
            throws VeterinariaException {
        
        if (mascotaId == null || mascotaId <= 0) {
            throw new VeterinariaException("El ID de la mascota es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora == null) {
            throw new VeterinariaException("La fecha y hora de la cita son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Validar que la fecha no sea en el pasado
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new VeterinariaException("No se pueden programar citas en fechas pasadas", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new VeterinariaException("El motivo de la cita es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (estadoId == null || estadoId <= 0) {
            throw new VeterinariaException("El estado de la cita es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Verifica la disponibilidad del veterinario
     */
    private void verificarDisponibilidadVeterinario(Integer veterinarioId, LocalDateTime fechaHora) 
            throws VeterinariaException {
        
        boolean disponible = citaDAO.verificarDisponibilidadVeterinario(veterinarioId, fechaHora);
        if (!disponible) {
            throw new VeterinariaException("El veterinario no está disponible en la fecha y hora seleccionada", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todas las citas activas
     */
    public List<Cita> listarCitasActivas() throws VeterinariaException {
        return citaDAO.findAll();
    }
    
    /**
     * Busca una cita por su ID
     */
    public Optional<Cita> buscarCitaPorId(Integer id) throws VeterinariaException {
        return citaDAO.findById(id);
    }
    
    /**
     * Busca citas por mascota
     */
    public List<Cita> buscarCitasPorMascota(Integer mascotaId) throws VeterinariaException {
        if (mascotaId == null || mascotaId <= 0) {
            throw new VeterinariaException("El ID de la mascota debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return citaDAO.findByMascotaId(mascotaId);
    }
    
    /**
     * Busca citas por veterinario
     */
    public List<Cita> buscarCitasPorVeterinario(Integer veterinarioId) throws VeterinariaException {
        if (veterinarioId == null || veterinarioId <= 0) {
            throw new VeterinariaException("El ID del veterinario debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return citaDAO.findByVeterinarioId(veterinarioId);
    }
    
    /**
     * Busca citas por estado
     */
    public List<Cita> buscarCitasPorEstado(Integer estadoId) throws VeterinariaException {
        if (estadoId == null || estadoId <= 0) {
            throw new VeterinariaException("El ID del estado debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return citaDAO.findByEstadoId(estadoId);
    }
    
    /**
     * Busca citas por rango de fechas
     */
    public List<Cita> buscarCitasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) 
            throws VeterinariaException {
        
        if (fechaInicio == null || fechaFin == null) {
            throw new VeterinariaException("Ambas fechas (inicio y fin) son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaInicio.isAfter(fechaFin)) {
            throw new VeterinariaException("La fecha de inicio no puede ser posterior a la fecha fin", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        return citaDAO.findByFechaRange(fechaInicio, fechaFin);
    }
    
    /**
     * Obtiene las citas programadas para hoy
     */
    public List<Cita> obtenerCitasHoy() throws VeterinariaException {
        return citaDAO.findCitasHoy();
    }
    
    /**
     * Actualiza los datos de una cita existente
     */
    public Cita actualizarCita(Cita cita) throws VeterinariaException {
        if (cita.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una cita sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la cita existe
        Optional<Cita> citaExistente = citaDAO.findById(cita.getId());
        if (citaExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró la cita con ID: " + cita.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return citaDAO.save(cita);
    }
    
    /**
     * Actualiza el estado de una cita
     */
    public void actualizarEstadoCita(Integer citaId, Integer nuevoEstadoId) throws VeterinariaException {
        if (citaId == null || citaId <= 0) {
            throw new VeterinariaException("El ID de la cita debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (nuevoEstadoId == null || nuevoEstadoId <= 0) {
            throw new VeterinariaException("El nuevo estado debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la cita existe
        Optional<Cita> cita = citaDAO.findById(citaId);
        if (cita.isEmpty()) {
            throw new VeterinariaException("No se encontró la cita con ID: " + citaId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        citaDAO.actualizarEstado(citaId, nuevoEstadoId);
    }
    
    /**
     * Elimina una cita (soft delete)
     */
    public void eliminarCita(Integer id) throws VeterinariaException {
        citaDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de citas activas
     */
    public long contarCitasActivas() throws VeterinariaException {
        return citaDAO.count();
    }
    
    /**
     * Verifica la disponibilidad de un veterinario
     */
    public boolean verificarDisponibilidad(Integer veterinarioId, LocalDateTime fechaHora) throws VeterinariaException {
        if (veterinarioId == null || veterinarioId <= 0) {
            throw new VeterinariaException("El ID del veterinario debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora == null) {
            throw new VeterinariaException("La fecha y hora son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        return citaDAO.verificarDisponibilidadVeterinario(veterinarioId, fechaHora);
    }
}