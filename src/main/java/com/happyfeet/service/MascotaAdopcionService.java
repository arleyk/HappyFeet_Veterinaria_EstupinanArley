package com.happyfeet.service;

import com.happyfeet.model.MascotaAdopcion;
import com.happyfeet.dao.MascotaAdopcionDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de mascotas en adopción
 */
public class MascotaAdopcionService {
    private final MascotaAdopcionDAO mascotaAdopcionDAO;
    
    public MascotaAdopcionService() throws VeterinariaException {
        this.mascotaAdopcionDAO = new MascotaAdopcionDAO();
    }
    
    /**
     * Registra una nueva mascota para adopción
     */
    public MascotaAdopcion registrarMascotaAdopcion(Integer mascotaId, String motivoIngreso, 
                                                   String historia, String temperamento, 
                                                   String necesidadesEspeciales) 
            throws VeterinariaException {
        
        validarDatosMascotaAdopcion(mascotaId, motivoIngreso);
        
        // Verificar que la mascota no esté ya en adopción
        Optional<MascotaAdopcion> existente = mascotaAdopcionDAO.findByMascotaId(mascotaId);
        if (existente.isPresent()) {
            throw new VeterinariaException("La mascota ya está registrada para adopción", 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        MascotaAdopcion mascotaAdopcion = new MascotaAdopcion();
        mascotaAdopcion.setMascotaId(mascotaId);
        mascotaAdopcion.setFechaIngreso(LocalDate.now());
        mascotaAdopcion.setMotivoIngreso(motivoIngreso);
        mascotaAdopcion.setEstado("Disponible");
        mascotaAdopcion.setHistoria(historia);
        mascotaAdopcion.setTemperamento(temperamento);
        mascotaAdopcion.setNecesidadesEspeciales(necesidadesEspeciales);
        
        return mascotaAdopcionDAO.save(mascotaAdopcion);
    }
    
    /**
     * Valida los datos de la mascota en adopción
     */
    private void validarDatosMascotaAdopcion(Integer mascotaId, String motivoIngreso) 
            throws VeterinariaException {
        
        if (mascotaId == null) {
            throw new VeterinariaException("El ID de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (motivoIngreso == null || motivoIngreso.trim().isEmpty()) {
            throw new VeterinariaException("El motivo de ingreso es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todas las mascotas en adopción activas
     */
    public List<MascotaAdopcion> listarMascotasAdopcionActivas() throws VeterinariaException {
        return mascotaAdopcionDAO.findAll();
    }
    
    /**
     * Busca mascotas disponibles para adopción
     */
    public List<MascotaAdopcion> buscarMascotasDisponibles() throws VeterinariaException {
        return mascotaAdopcionDAO.findMascotasDisponibles();
    }
    
    /**
     * Busca una mascota en adopción por su ID
     */
    public Optional<MascotaAdopcion> buscarMascotaAdopcionPorId(Integer id) throws VeterinariaException {
        return mascotaAdopcionDAO.findById(id);
    }
    
    /**
     * Busca mascotas en adopción por estado
     */
    public List<MascotaAdopcion> buscarMascotasAdopcionPorEstado(String estado) throws VeterinariaException {
        return mascotaAdopcionDAO.findByEstado(estado);
    }
    
    /**
     * Actualiza los datos de una mascota en adopción
     */
    public MascotaAdopcion actualizarMascotaAdopcion(MascotaAdopcion mascotaAdopcion) throws VeterinariaException {
        if (mascotaAdopcion.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una mascota en adopción sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la mascota en adopción existe
        Optional<MascotaAdopcion> existente = mascotaAdopcionDAO.findById(mascotaAdopcion.getId());
        if (existente.isEmpty()) {
            throw new VeterinariaException("No se encontró la mascota en adopción con ID: " + mascotaAdopcion.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return mascotaAdopcionDAO.save(mascotaAdopcion);
    }
    
    /**
     * Cambia el estado de una mascota en adopción
     */
    public MascotaAdopcion cambiarEstadoMascotaAdopcion(Integer id, String nuevoEstado) throws VeterinariaException {
        Optional<MascotaAdopcion> mascotaAdopcionOpt = mascotaAdopcionDAO.findById(id);
        if (mascotaAdopcionOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró la mascota en adopción con ID: " + id, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        MascotaAdopcion mascotaAdopcion = mascotaAdopcionOpt.get();
        mascotaAdopcion.setEstado(nuevoEstado);
        
        return mascotaAdopcionDAO.save(mascotaAdopcion);
    }
    
    /**
     * Elimina una mascota en adopción (soft delete)
     */
    public void eliminarMascotaAdopcion(Integer id) throws VeterinariaException {
        mascotaAdopcionDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de mascotas en adopción activas
     */
    public long contarMascotasAdopcionActivas() throws VeterinariaException {
        return mascotaAdopcionDAO.count();
    }
    
    /**
     * Obtiene estadísticas de mascotas en adopción por estado
     */
    public String obtenerEstadisticas() throws VeterinariaException {
        List<MascotaAdopcion> disponibles = buscarMascotasAdopcionPorEstado("Disponible");
        List<MascotaAdopcion> enProceso = buscarMascotasAdopcionPorEstado("En Proceso");
        List<MascotaAdopcion> adoptadas = buscarMascotasAdopcionPorEstado("Adoptada");
        List<MascotaAdopcion> retiradas = buscarMascotasAdopcionPorEstado("Retirada");
        
        return String.format("Disponibles: %d, En Proceso: %d, Adoptadas: %d, Retiradas: %d",
                           disponibles.size(), enProceso.size(), adoptadas.size(), retiradas.size());
    }
}