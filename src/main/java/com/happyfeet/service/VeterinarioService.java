package com.happyfeet.service;

import com.happyfeet.model.Veterinario;
import com.happyfeet.dao.VeterinarioDAO;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de veterinarios
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 * Aplica el principio de responsabilidad única (SRP)
 */
public class VeterinarioService {
    private final VeterinarioDAO veterinarioDAO;
    
    public VeterinarioService() throws VeterinariaException {
        this.veterinarioDAO = new VeterinarioDAO();
    }
    
    /**
     * Registra un nuevo veterinario en el sistema
     * Aplica validaciones de negocio antes de guardar
     */
    public Veterinario registrarVeterinario(String nombreCompleto, String documentoIdentidad, String licenciaProfesional) 
            throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosVeterinarioBasicos(nombreCompleto, documentoIdentidad, licenciaProfesional);
        
        // Verificar duplicados
        verificarDuplicados(documentoIdentidad, licenciaProfesional, null);
        
        Veterinario nuevoVeterinario = new Veterinario(nombreCompleto, documentoIdentidad, licenciaProfesional);
        return veterinarioDAO.save(nuevoVeterinario);
    }
    
    /**
     * Registra un veterinario completo con todos los datos
     */
    public Veterinario registrarVeterinarioCompleto(String nombreCompleto, String documentoIdentidad, 
                                                   String licenciaProfesional, String especialidad, 
                                                   String telefono, String email, String fechaContratacion) 
            throws VeterinariaException {
        
        validarDatosVeterinarioBasicos(nombreCompleto, documentoIdentidad, licenciaProfesional);
        verificarDuplicados(documentoIdentidad, licenciaProfesional, email);
        
        Veterinario veterinario = new Veterinario(nombreCompleto, documentoIdentidad, licenciaProfesional);
        veterinario.setEspecialidad(especialidad);
        veterinario.setTelefono(telefono);
        veterinario.setEmail(email);
        
        // Procesar fecha de contratación
        if (fechaContratacion != null && !fechaContratacion.trim().isEmpty()) {
            try {
                java.time.LocalDate fecha = java.time.LocalDate.parse(fechaContratacion);
                veterinario.setFechaContratacion(fecha);
            } catch (java.time.format.DateTimeParseException e) {
                throw new VeterinariaException("Formato de fecha inválido. Use YYYY-MM-DD", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
        }
        
        return veterinarioDAO.save(veterinario);
    }
    
    /**
     * Valida los datos básicos de un veterinario
     */
    private void validarDatosVeterinarioBasicos(String nombreCompleto, String documentoIdentidad, String licenciaProfesional) 
            throws VeterinariaException {
        
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new VeterinariaException("El nombre completo es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (documentoIdentidad == null || documentoIdentidad.trim().isEmpty()) {
            throw new VeterinariaException("El documento de identidad es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (licenciaProfesional == null || licenciaProfesional.trim().isEmpty()) {
            throw new VeterinariaException("La licencia profesional es obligatoria", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Verifica que no existan veterinarios con el mismo documento, licencia o email
     */
    private void verificarDuplicados(String documentoIdentidad, String licenciaProfesional, String email) 
            throws VeterinariaException {
        
        Optional<Veterinario> veterinarioPorDocumento = veterinarioDAO.findByDocumento(documentoIdentidad);
        if (veterinarioPorDocumento.isPresent()) {
            throw new VeterinariaException("Ya existe un veterinario con el documento: " + documentoIdentidad, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        Optional<Veterinario> veterinarioPorLicencia = veterinarioDAO.findByLicencia(licenciaProfesional);
        if (veterinarioPorLicencia.isPresent()) {
            throw new VeterinariaException("Ya existe un veterinario con la licencia: " + licenciaProfesional, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        if (email != null && !email.trim().isEmpty()) {
            Optional<Veterinario> veterinarioPorEmail = veterinarioDAO.findByEmail(email);
            if (veterinarioPorEmail.isPresent()) {
                throw new VeterinariaException("Ya existe un veterinario con el email: " + email, 
                                             VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
            }
        }
    }
    
    /**
     * Obtiene todos los veterinarios activos ordenados por nombre
     */
    public List<Veterinario> listarVeterinariosActivos() throws VeterinariaException {
        return veterinarioDAO.findAllActiveOrdered();
    }
    
    /**
     * Busca un veterinario por su ID
     */
    public Optional<Veterinario> buscarVeterinarioPorId(Integer id) throws VeterinariaException {
        return veterinarioDAO.findById(id);
    }
    
    /**
     * Busca veterinarios por nombre (búsqueda parcial)
     */
    public List<Veterinario> buscarVeterinariosPorNombre(String nombre) throws VeterinariaException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de búsqueda no puede estar vacío", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return veterinarioDAO.findByNombre(nombre);
    }
    
    /**
     * Busca veterinarios por especialidad
     */
    public List<Veterinario> buscarVeterinariosPorEspecialidad(String especialidad) throws VeterinariaException {
        return veterinarioDAO.findByEspecialidad(especialidad);
    }
    
    /**
     * Actualiza los datos de un veterinario existente
     */
    public Veterinario actualizarVeterinario(Veterinario veterinario) throws VeterinariaException {
        if (veterinario.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un veterinario sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el veterinario existe
        Optional<Veterinario> veterinarioExistente = veterinarioDAO.findById(veterinario.getId());
        if (veterinarioExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el veterinario con ID: " + veterinario.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        // Verificar duplicados excluyendo el registro actual
        verificarDuplicadosParaActualizacion(veterinario);
        
        return veterinarioDAO.save(veterinario);
    }
    
    /**
     * Verifica duplicados para actualización (excluye el registro actual)
     */
    private void verificarDuplicadosParaActualizacion(Veterinario veterinario) throws VeterinariaException {
        Optional<Veterinario> veterinarioPorDocumento = veterinarioDAO.findByDocumento(veterinario.getDocumentoIdentidad());
        if (veterinarioPorDocumento.isPresent() && !veterinarioPorDocumento.get().getId().equals(veterinario.getId())) {
            throw new VeterinariaException("Ya existe otro veterinario con el documento: " + veterinario.getDocumentoIdentidad(), 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        Optional<Veterinario> veterinarioPorLicencia = veterinarioDAO.findByLicencia(veterinario.getLicenciaProfesional());
        if (veterinarioPorLicencia.isPresent() && !veterinarioPorLicencia.get().getId().equals(veterinario.getId())) {
            throw new VeterinariaException("Ya existe otro veterinario con la licencia: " + veterinario.getLicenciaProfesional(), 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        if (veterinario.getEmail() != null && !veterinario.getEmail().trim().isEmpty()) {
            Optional<Veterinario> veterinarioPorEmail = veterinarioDAO.findByEmail(veterinario.getEmail());
            if (veterinarioPorEmail.isPresent() && !veterinarioPorEmail.get().getId().equals(veterinario.getId())) {
                throw new VeterinariaException("Ya existe otro veterinario con el email: " + veterinario.getEmail(), 
                                             VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
            }
        }
    }
    
    /**
     * Elimina un veterinario (soft delete)
     */
    public void eliminarVeterinario(Integer id) throws VeterinariaException {
        // Aquí se podrían agregar validaciones adicionales
        // como verificar si el veterinario tiene citas activas
        veterinarioDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de veterinarios activos
     */
    public long contarVeterinariosActivos() throws VeterinariaException {
        return veterinarioDAO.count();
    }
    
    /**
     * Verifica si un veterinario está activo
     */
    public boolean estaActivo(Integer id) throws VeterinariaException {
        Optional<Veterinario> veterinario = veterinarioDAO.findById(id);
        return veterinario.isPresent() && veterinario.get().getActivo();
    }
}