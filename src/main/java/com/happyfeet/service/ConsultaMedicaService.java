package com.happyfeet.service;

import com.happyfeet.model.ConsultaMedica;
import com.happyfeet.dao.ConsultaMedicaDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de consultas médicas
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 */
public class ConsultaMedicaService {
    private final ConsultaMedicaDAO consultaMedicaDAO;
    
    public ConsultaMedicaService() throws VeterinariaException {
        this.consultaMedicaDAO = new ConsultaMedicaDAO();
    }
    
    /**
     * Registra una nueva consulta médica en el sistema
     */
    public ConsultaMedica registrarConsultaMedica(Integer mascotaId, Integer veterinarioId, 
                                                 LocalDateTime fechaHora, String motivo, 
                                                 String sintomas, String diagnostico) 
            throws VeterinariaException {
        
        validarDatosConsulta(mascotaId, veterinarioId, fechaHora, motivo);
        
        ConsultaMedica consulta = new ConsultaMedica(mascotaId, veterinarioId, fechaHora, 
                                                   motivo, sintomas, diagnostico);
        return consultaMedicaDAO.save(consulta);
    }
    
    /**
     * Registra una consulta médica completa con todos los datos
     */
    public ConsultaMedica registrarConsultaMedicaCompleta(Integer mascotaId, Integer veterinarioId, 
                                                         Integer citaId, LocalDateTime fechaHora, 
                                                         String motivo, String sintomas, 
                                                         String diagnostico, String recomendaciones, 
                                                         String observaciones, Double pesoRegistrado, 
                                                         Double temperatura) throws VeterinariaException {
        
        validarDatosConsulta(mascotaId, veterinarioId, fechaHora, motivo);
        
        ConsultaMedica consulta = new ConsultaMedica(mascotaId, veterinarioId, fechaHora, 
                                                   motivo, sintomas, diagnostico);
        consulta.setCitaId(citaId);
        consulta.setRecomendaciones(recomendaciones);
        consulta.setObservaciones(observaciones);
        consulta.setPesoRegistrado(pesoRegistrado);
        consulta.setTemperatura(temperatura);
        
        return consultaMedicaDAO.save(consulta);
    }
    
    /**
     * Valida los datos básicos de una consulta médica
     */
    private void validarDatosConsulta(Integer mascotaId, Integer veterinarioId, 
                                     LocalDateTime fechaHora, String motivo) 
            throws VeterinariaException {
        
        if (mascotaId == null) {
            throw new VeterinariaException("El ID de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (veterinarioId == null) {
            throw new VeterinariaException("El ID del veterinario es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora == null) {
            throw new VeterinariaException("La fecha y hora de la consulta son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora.isAfter(LocalDateTime.now())) {
            throw new VeterinariaException("La fecha de la consulta no puede ser futura", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new VeterinariaException("El motivo de la consulta es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todas las consultas médicas
     */
    public List<ConsultaMedica> listarConsultasMedicas() throws VeterinariaException {
        return consultaMedicaDAO.findAll();
    }
    
    /**
     * Busca una consulta médica por su ID
     */
    public Optional<ConsultaMedica> buscarConsultaMedicaPorId(Integer id) throws VeterinariaException {
        return consultaMedicaDAO.findById(id);
    }
    
    /**
     * Busca consultas médicas por ID de mascota
     */
    public List<ConsultaMedica> buscarConsultasPorMascota(Integer mascotaId) throws VeterinariaException {
        return consultaMedicaDAO.findByMascotaId(mascotaId);
    }
    
    /**
     * Busca consultas médicas por ID de veterinario
     */
    public List<ConsultaMedica> buscarConsultasPorVeterinario(Integer veterinarioId) throws VeterinariaException {
        return consultaMedicaDAO.findByVeterinarioId(veterinarioId);
    }
    
    /**
     * Actualiza los datos de una consulta médica existente
     */
    public ConsultaMedica actualizarConsultaMedica(ConsultaMedica consulta) throws VeterinariaException {
        if (consulta.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una consulta sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la consulta existe
        Optional<ConsultaMedica> consultaExistente = consultaMedicaDAO.findById(consulta.getId());
        if (consultaExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró la consulta con ID: " + consulta.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return consultaMedicaDAO.save(consulta);
    }
    
    /**
     * Obtiene el número total de consultas médicas
     */
    public long contarConsultasMedicas() throws VeterinariaException {
        return consultaMedicaDAO.count();
    }
    
    /**
     * Obtiene el número de consultas por mascota
     */
    public long contarConsultasPorMascota(Integer mascotaId) throws VeterinariaException {
        return consultaMedicaDAO.countByMascotaId(mascotaId);
    }
}