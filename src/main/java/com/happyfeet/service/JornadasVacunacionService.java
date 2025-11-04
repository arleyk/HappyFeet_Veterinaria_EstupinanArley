package com.happyfeet.service;

import com.happyfeet.model.JornadasVacunacion;
import com.happyfeet.dao.JornadasVacunacionDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de jornadas de vacunación
 */
public class JornadasVacunacionService {
    private final JornadasVacunacionDAO jornadasVacunacionDAO;
    
    public JornadasVacunacionService() throws VeterinariaException {
        this.jornadasVacunacionDAO = new JornadasVacunacionDAO();
    }
    
    /**
     * Registra una nueva jornada de vacunación
     */
    public JornadasVacunacion registrarJornada(String nombre, LocalDate fecha, String ubicacion, String estado, 
                                              Integer capacidadMaxima, String descripcion, 
                                              LocalTime horaInicio, LocalTime horaFin) 
            throws VeterinariaException {
        
        validarDatosJornada(nombre, fecha, ubicacion, estado);
        
        JornadasVacunacion jornada = new JornadasVacunacion();
        jornada.setNombre(nombre);
        jornada.setFecha(fecha);
        jornada.setUbicacion(ubicacion);
        jornada.setEstado(estado);
        jornada.setCapacidadMaxima(capacidadMaxima);
        jornada.setDescripcion(descripcion);
        jornada.setHoraInicio(horaInicio);
        jornada.setHoraFin(horaFin);
        
        return jornadasVacunacionDAO.save(jornada);
    }
    
    /**
     * Valida los datos de la jornada
     */
    private void validarDatosJornada(String nombre, LocalDate fecha, String ubicacion, String estado) 
            throws VeterinariaException {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de la jornada es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fecha == null) {
            throw new VeterinariaException("La fecha de la jornada es obligatoria", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new VeterinariaException("La ubicación de la jornada es obligatoria", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (estado == null || estado.trim().isEmpty()) {
            throw new VeterinariaException("El estado de la jornada es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todas las jornadas
     */
    public List<JornadasVacunacion> listarJornadas() throws VeterinariaException {
        return jornadasVacunacionDAO.findAll();
    }
    
    /**
     * Busca una jornada por su ID
     */
    public Optional<JornadasVacunacion> buscarJornadaPorId(Integer id) throws VeterinariaException {
        return jornadasVacunacionDAO.findById(id);
    }
    
    /**
     * Busca jornadas por estado
     */
    public List<JornadasVacunacion> buscarJornadasPorEstado(String estado) throws VeterinariaException {
        return jornadasVacunacionDAO.findByEstado(estado);
    }
    
    /**
     * Busca jornadas por fecha
     */
    public List<JornadasVacunacion> buscarJornadasPorFecha(LocalDate fecha) throws VeterinariaException {
        return jornadasVacunacionDAO.findByFecha(fecha);
    }
    
    /**
     * Busca jornadas planificadas (futuras)
     */
    public List<JornadasVacunacion> buscarJornadasPlanificadas() throws VeterinariaException {
        return jornadasVacunacionDAO.findJornadasPlanificadas();
    }
    
    /**
     * Actualiza los datos de una jornada
     */
    public JornadasVacunacion actualizarJornada(JornadasVacunacion jornada) throws VeterinariaException {
        if (jornada.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una jornada sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la jornada existe
        Optional<JornadasVacunacion> existente = jornadasVacunacionDAO.findById(jornada.getId());
        if (existente.isEmpty()) {
            throw new VeterinariaException("No se encontró la jornada con ID: " + jornada.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return jornadasVacunacionDAO.save(jornada);
    }
    
    /**
     * Cambia el estado de una jornada
     */
    public JornadasVacunacion cambiarEstadoJornada(Integer id, String nuevoEstado) throws VeterinariaException {
        Optional<JornadasVacunacion> jornadaOpt = jornadasVacunacionDAO.findById(id);
        if (jornadaOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró la jornada con ID: " + id, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        JornadasVacunacion jornada = jornadaOpt.get();
        jornada.setEstado(nuevoEstado);
        
        return jornadasVacunacionDAO.save(jornada);
    }
    
    /**
     * Elimina una jornada
     */
    public void eliminarJornada(Integer id) throws VeterinariaException {
        jornadasVacunacionDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de jornadas
     */
    public long contarJornadas() throws VeterinariaException {
        return jornadasVacunacionDAO.count();
    }
    
    /**
     * Obtiene estadísticas de jornadas por estado
     */
    public String obtenerEstadisticas() throws VeterinariaException {
        List<JornadasVacunacion> planificadas = buscarJornadasPorEstado("Planificada");
        List<JornadasVacunacion> enCurso = buscarJornadasPorEstado("En Curso");
        List<JornadasVacunacion> finalizadas = buscarJornadasPorEstado("Finalizada");
        List<JornadasVacunacion> canceladas = buscarJornadasPorEstado("Cancelada");
        
        return String.format("Planificadas: %d, En Curso: %d, Finalizadas: %d, Canceladas: %d",
                           planificadas.size(), enCurso.size(), finalizadas.size(), canceladas.size());
    }
}