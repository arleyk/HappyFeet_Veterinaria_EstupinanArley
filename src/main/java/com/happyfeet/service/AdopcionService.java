package com.happyfeet.service;

import com.happyfeet.model.Adopcion;
import com.happyfeet.model.MascotaAdopcion;
import com.happyfeet.dao.AdopcionDAO;
import com.happyfeet.dao.MascotaAdopcionDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de adopciones
 */
public class AdopcionService {
    private final AdopcionDAO adopcionDAO;
    private final MascotaAdopcionDAO mascotaAdopcionDAO;
    
    public AdopcionService() throws VeterinariaException {
        this.adopcionDAO = new AdopcionDAO();
        this.mascotaAdopcionDAO = new MascotaAdopcionDAO();
    }
    
    /**
     * Registra una nueva adopción
     */
    public Adopcion registrarAdopcion(Integer mascotaAdopcionId, Integer duenoId, 
                                     String contratoTexto, String condicionesEspeciales) 
            throws VeterinariaException {
        
        validarDatosAdopcion(mascotaAdopcionId, duenoId);
        
        // Verificar que la mascota esté disponible para adopción
        Optional<MascotaAdopcion> mascotaAdopcionOpt = mascotaAdopcionDAO.findById(mascotaAdopcionId);
        if (mascotaAdopcionOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró la mascota en adopción con ID: " + mascotaAdopcionId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        MascotaAdopcion mascotaAdopcion = mascotaAdopcionOpt.get();
        if (!"Disponible".equals(mascotaAdopcion.getEstado())) {
            throw new VeterinariaException("La mascota no está disponible para adopción. Estado actual: " + mascotaAdopcion.getEstado(), 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que no exista una adopción previa para esta mascota
        Optional<Adopcion> adopcionExistente = adopcionDAO.findByMascotaAdopcionId(mascotaAdopcionId);
        if (adopcionExistente.isPresent()) {
            throw new VeterinariaException("Ya existe una adopción registrada para esta mascota", 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        Adopcion adopcion = new Adopcion();
        adopcion.setMascotaAdopcionId(mascotaAdopcionId);
        adopcion.setDuenoId(duenoId);
        adopcion.setFechaAdopcion(LocalDate.now());
        adopcion.setContratoTexto(contratoTexto);
        adopcion.setCondicionesEspeciales(condicionesEspeciales);
        adopcion.setSeguimientoRequerido(true);
        
        // Actualizar el estado de la mascota a "Adoptada"
        mascotaAdopcion.setEstado("Adoptada");
        mascotaAdopcionDAO.save(mascotaAdopcion);
        
        return adopcionDAO.save(adopcion);
    }
    
    /**
     * Valida los datos de la adopción
     */
    private void validarDatosAdopcion(Integer mascotaAdopcionId, Integer duenoId) 
            throws VeterinariaException {
        
        if (mascotaAdopcionId == null) {
            throw new VeterinariaException("El ID de la mascota en adopción es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (duenoId == null) {
            throw new VeterinariaException("El ID del dueño es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todas las adopciones activas
     */
    public List<Adopcion> listarAdopcionesActivas() throws VeterinariaException {
        return adopcionDAO.findAll();
    }
    
    /**
     * Busca una adopción por su ID
     */
    public Optional<Adopcion> buscarAdopcionPorId(Integer id) throws VeterinariaException {
        return adopcionDAO.findById(id);
    }
    
    /**
     * Busca adopciones por dueño
     */
    public List<Adopcion> buscarAdopcionesPorDueno(Integer duenoId) throws VeterinariaException {
        return adopcionDAO.findByDuenoId(duenoId);
    }
    
    /**
     * Actualiza los datos de una adopción
     */
    public Adopcion actualizarAdopcion(Adopcion adopcion) throws VeterinariaException {
        if (adopcion.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una adopción sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la adopción existe
        Optional<Adopcion> existente = adopcionDAO.findById(adopcion.getId());
        if (existente.isEmpty()) {
            throw new VeterinariaException("No se encontró la adopción con ID: " + adopcion.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return adopcionDAO.save(adopcion);
    }
    
    /**
     * Programa el seguimiento de una adopción
     */
    public Adopcion programarSeguimiento(Integer adopcionId, LocalDate fechaSeguimiento) throws VeterinariaException {
        Optional<Adopcion> adopcionOpt = adopcionDAO.findById(adopcionId);
        if (adopcionOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró la adopción con ID: " + adopcionId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        Adopcion adopcion = adopcionOpt.get();
        adopcion.setFechaPrimerSeguimiento(fechaSeguimiento);
        
        return adopcionDAO.save(adopcion);
    }
    
    /**
     * Elimina una adopción (soft delete)
     */
    public void eliminarAdopcion(Integer id) throws VeterinariaException {
        adopcionDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de adopciones activas
     */
    public long contarAdopcionesActivas() throws VeterinariaException {
        return adopcionDAO.count();
    }
}