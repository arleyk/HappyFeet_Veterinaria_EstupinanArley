package com.happyfeet.service;

import com.happyfeet.model.RegistroJornadaVacunacion;
import com.happyfeet.dao.RegistroJornadaVacunacionDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de registros de jornadas de vacunación
 */
public class RegistroJornadaVacunacionService {
    private final RegistroJornadaVacunacionDAO registroJornadaVacunacionDAO;
    
    public RegistroJornadaVacunacionService() throws VeterinariaException {
        this.registroJornadaVacunacionDAO = new RegistroJornadaVacunacionDAO();
    }
    
    /**
     * Registra un nuevo registro de vacunación en jornada
     */
    public RegistroJornadaVacunacion registrarVacunacion(Integer jornadaId, Integer mascotaId, Integer duenoId, 
                                                        Integer vacunaId, LocalDateTime fechaHora, String loteVacuna, 
                                                        Integer veterinarioId, String observaciones) 
            throws VeterinariaException {
        
        validarDatosRegistro(jornadaId, mascotaId, duenoId, vacunaId, fechaHora);
        
        RegistroJornadaVacunacion registro = new RegistroJornadaVacunacion();
        registro.setJornadaId(jornadaId);
        registro.setMascotaId(mascotaId);
        registro.setDuenoId(duenoId);
        registro.setVacunaId(vacunaId);
        registro.setFechaHora(fechaHora);
        registro.setLoteVacuna(loteVacuna);
        registro.setVeterinarioId(veterinarioId);
        registro.setObservaciones(observaciones);
        
        return registroJornadaVacunacionDAO.save(registro);
    }
    
    /**
     * Valida los datos del registro
     */
    private void validarDatosRegistro(Integer jornadaId, Integer mascotaId, Integer duenoId, Integer vacunaId, LocalDateTime fechaHora) 
            throws VeterinariaException {
        
        if (jornadaId == null) {
            throw new VeterinariaException("El ID de la jornada es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (mascotaId == null) {
            throw new VeterinariaException("El ID de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (duenoId == null) {
            throw new VeterinariaException("El ID del dueño es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (vacunaId == null) {
            throw new VeterinariaException("El ID de la vacuna es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaHora == null) {
            throw new VeterinariaException("La fecha y hora son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todos los registros
     */
    public List<RegistroJornadaVacunacion> listarRegistros() throws VeterinariaException {
        return registroJornadaVacunacionDAO.findAll();
    }
    
    /**
     * Busca un registro por su ID
     */
    public Optional<RegistroJornadaVacunacion> buscarRegistroPorId(Integer id) throws VeterinariaException {
        return registroJornadaVacunacionDAO.findById(id);
    }
    
    /**
     * Busca registros por jornada
     */
    public List<RegistroJornadaVacunacion> buscarRegistrosPorJornada(Integer jornadaId) throws VeterinariaException {
        return registroJornadaVacunacionDAO.findByJornadaId(jornadaId);
    }
    
    /**
     * Busca registros por mascota
     */
    public List<RegistroJornadaVacunacion> buscarRegistrosPorMascota(Integer mascotaId) throws VeterinariaException {
        return registroJornadaVacunacionDAO.findByMascotaId(mascotaId);
    }
    
    /**
     * Busca registros por dueño
     */
    public List<RegistroJornadaVacunacion> buscarRegistrosPorDueno(Integer duenoId) throws VeterinariaException {
        return registroJornadaVacunacionDAO.findByDuenoId(duenoId);
    }
    
    /**
     * Actualiza los datos de un registro
     */
    public RegistroJornadaVacunacion actualizarRegistro(RegistroJornadaVacunacion registro) throws VeterinariaException {
        if (registro.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un registro sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el registro existe
        Optional<RegistroJornadaVacunacion> existente = registroJornadaVacunacionDAO.findById(registro.getId());
        if (existente.isEmpty()) {
            throw new VeterinariaException("No se encontró el registro con ID: " + registro.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return registroJornadaVacunacionDAO.save(registro);
    }
    
    /**
     * Programa la próxima dosis de vacunación
     */
    public RegistroJornadaVacunacion programarProximaDosis(Integer registroId, java.time.LocalDate proximaDosis) throws VeterinariaException {
        Optional<RegistroJornadaVacunacion> registroOpt = registroJornadaVacunacionDAO.findById(registroId);
        if (registroOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró el registro con ID: " + registroId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        RegistroJornadaVacunacion registro = registroOpt.get();
        registro.setProximaDosis(proximaDosis);
        
        return registroJornadaVacunacionDAO.save(registro);
    }
    
    /**
     * Elimina un registro
     */
    public void eliminarRegistro(Integer id) throws VeterinariaException {
        registroJornadaVacunacionDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de registros
     */
    public long contarRegistros() throws VeterinariaException {
        return registroJornadaVacunacionDAO.count();
    }
    
    /**
     * Cuenta registros por jornada
     */
    public long contarRegistrosPorJornada(Integer jornadaId) throws VeterinariaException {
        return registroJornadaVacunacionDAO.countByJornadaId(jornadaId);
    }
}