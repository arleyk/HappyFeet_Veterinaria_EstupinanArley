package com.happyfeet.service;

import com.happyfeet.model.Mascota;
import com.happyfeet.dao.MascotaDAO;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de mascotas
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 * Aplica el principio de responsabilidad única (SRP)
 */
public class MascotaService {
    private final MascotaDAO mascotaDAO;
    
    public MascotaService() throws VeterinariaException {
        this.mascotaDAO = new MascotaDAO();
    }
    
    /**
     * Registra una nueva mascota en el sistema
     * Aplica validaciones de negocio antes de guardar
     */
    public Mascota registrarMascota(Integer duenoId, String nombre, Integer razaId, String sexo) 
            throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosMascotaBasicos(duenoId, nombre, razaId, sexo);
        
        Mascota nuevaMascota = new Mascota(duenoId, nombre, razaId, sexo);
        return mascotaDAO.save(nuevaMascota);
    }
    
    /**
     * Registra una mascota completa con todos los datos
     */
    public Mascota registrarMascotaCompleta(Integer duenoId, String nombre, Integer razaId, 
                                           String fechaNacimiento, String sexo, Double pesoActual,
                                           String microchip, String alergias, String condicionesPreexistentes) 
            throws VeterinariaException {
        
        validarDatosMascotaBasicos(duenoId, nombre, razaId, sexo);
        
        Mascota mascota = new Mascota(duenoId, nombre, razaId, sexo);
        
        // Procesar fecha de nacimiento
        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            try {
                java.time.LocalDate fecha = java.time.LocalDate.parse(fechaNacimiento);
                mascota.setFechaNacimiento(fecha);
            } catch (java.time.format.DateTimeParseException e) {
                throw new VeterinariaException("Formato de fecha inválido. Use YYYY-MM-DD", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
        }
        
        // Validar y establecer peso
        if (pesoActual != null) {
            if (pesoActual <= 0) {
                throw new VeterinariaException("El peso debe ser un valor positivo", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
            if (pesoActual > 200) { // Peso máximo razonable para una mascota
                throw new VeterinariaException("El peso ingresado no es válido", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
            mascota.setPesoActual(pesoActual);
        }
        
        // Validar microchip único si se proporciona
        if (microchip != null && !microchip.trim().isEmpty()) {
            verificarMicrochipUnico(microchip);
            mascota.setMicrochip(microchip);
        }
        
        mascota.setAlergias(alergias);
        mascota.setCondicionesPreexistentes(condicionesPreexistentes);
        
        return mascotaDAO.save(mascota);
    }
    
    /**
     * Valida los datos básicos de una mascota
     */
    private void validarDatosMascotaBasicos(Integer duenoId, String nombre, Integer razaId, String sexo) 
            throws VeterinariaException {
        
        if (duenoId == null || duenoId <= 0) {
            throw new VeterinariaException("El ID del dueño es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de la mascota es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (razaId == null || razaId <= 0) {
            throw new VeterinariaException("El ID de la raza es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (sexo == null || (!sexo.equalsIgnoreCase("Macho") && !sexo.equalsIgnoreCase("Hembra"))) {
            throw new VeterinariaException("El sexo debe ser 'Macho' o 'Hembra'", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Verifica que no existan mascotas con el mismo microchip
     */
    private void verificarMicrochipUnico(String microchip) throws VeterinariaException {
        Optional<Mascota> mascotaPorMicrochip = mascotaDAO.findByMicrochip(microchip);
        if (mascotaPorMicrochip.isPresent()) {
            throw new VeterinariaException("Ya existe una mascota con el microchip: " + microchip, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
    }
    
    /**
     * Obtiene todas las mascotas activas
     */
    public List<Mascota> listarMascotasActivas() throws VeterinariaException {
        return mascotaDAO.findAll();
    }
    
    /**
     * Busca una mascota por su ID
     */
    public Optional<Mascota> buscarMascotaPorId(Integer id) throws VeterinariaException {
        return mascotaDAO.findById(id);
    }
    
    /**
     * Busca mascotas por nombre (búsqueda parcial)
     */
    public List<Mascota> buscarMascotasPorNombre(String nombre) throws VeterinariaException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de búsqueda no puede estar vacío", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return mascotaDAO.findByNombre(nombre);
    }
    
    /**
     * Busca mascotas por dueño
     */
    public List<Mascota> buscarMascotasPorDuenoId(Integer duenoId) throws VeterinariaException {
        if (duenoId == null || duenoId <= 0) {
            throw new VeterinariaException("El ID del dueño debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return mascotaDAO.findByDuenoId(duenoId);
    }
    
    /**
     * Actualiza los datos de una mascota existente
     */
    public Mascota actualizarMascota(Mascota mascota) throws VeterinariaException {
        if (mascota.getId() == null) {
            throw new VeterinariaException("No se puede actualizar una mascota sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que la mascota existe
        Optional<Mascota> mascotaExistente = mascotaDAO.findById(mascota.getId());
        if (mascotaExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró la mascota con ID: " + mascota.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return mascotaDAO.save(mascota);
    }
    
    /**
     * Elimina una mascota (soft delete)
     */
    public void eliminarMascota(Integer id) throws VeterinariaException {
        // Aquí se podrían agregar validaciones adicionales
        // como verificar si la mascota tiene citas activas o historial médico
        mascotaDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de mascotas activas
     */
    public long contarMascotasActivas() throws VeterinariaException {
        return mascotaDAO.count();
    }
    
    /**
     * Actualiza el peso de una mascota
     */
    public void actualizarPesoMascota(Integer mascotaId, Double nuevoPeso) throws VeterinariaException {
        if (mascotaId == null || mascotaId <= 0) {
            throw new VeterinariaException("El ID de la mascota debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (nuevoPeso != null) {
            if (nuevoPeso <= 0) {
                throw new VeterinariaException("El peso debe ser un valor positivo", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
            if (nuevoPeso > 200) {
                throw new VeterinariaException("El peso ingresado no es válido", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
        }
        
        // Verificar que la mascota existe
        Optional<Mascota> mascota = mascotaDAO.findById(mascotaId);
        if (mascota.isEmpty()) {
            throw new VeterinariaException("No se encontró la mascota con ID: " + mascotaId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        mascotaDAO.actualizarPeso(mascotaId, nuevoPeso);
    }
    /**
 * Cambia el dueño de una mascota
 * Aplica validaciones de negocio antes de realizar el cambio
 */
public Mascota cambiarDuenoMascota(Integer mascotaId, Integer nuevoDuenoId) throws VeterinariaException {
    // Validaciones básicas
    if (mascotaId == null || mascotaId <= 0) {
        throw new VeterinariaException("El ID de la mascota debe ser válido", 
                                     VeterinariaException.ErrorType.VALIDATION_ERROR);
    }
    
    if (nuevoDuenoId == null || nuevoDuenoId <= 0) {
        throw new VeterinariaException("El ID del nuevo dueño debe ser válido", 
                                     VeterinariaException.ErrorType.VALIDATION_ERROR);
    }
    
    // Verificar que la mascota existe
    Optional<Mascota> mascotaOptional = mascotaDAO.findById(mascotaId);
    if (mascotaOptional.isEmpty()) {
        throw new VeterinariaException("No se encontró la mascota con ID: " + mascotaId, 
                                     VeterinariaException.ErrorType.NOT_FOUND_ERROR);
    }
    
    Mascota mascota = mascotaOptional.get();
    
    // Verificar que no sea el mismo dueño
    if (mascota.getDuenoId().equals(nuevoDuenoId)) {
        throw new VeterinariaException("La mascota ya pertenece a este dueño", 
                                     VeterinariaException.ErrorType.VALIDATION_ERROR);
    }
    
    // Actualizar el dueño
    mascota.setDuenoId(nuevoDuenoId);
    
    return mascotaDAO.save(mascota);
}
}