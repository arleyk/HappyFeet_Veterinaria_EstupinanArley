package com.happyfeet.service;

import com.happyfeet.model.Dueno;
import com.happyfeet.dao.DuenoDAO;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de dueños
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 * Aplica el principio de responsabilidad única (SRP)
 */
public class DuenoService {
    private final DuenoDAO duenoDAO;
    
    public DuenoService() throws VeterinariaException {
        this.duenoDAO = new DuenoDAO();
    }
    
    /**
     * Registra un nuevo dueño en el sistema
     * Aplica validaciones de negocio antes de guardar
     */
    public Dueno registrarDueno(String nombre, String documento, String email) 
            throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosDueno(nombre, documento, email);
        
        // Verificar duplicados
        verificarDuplicados(documento, email);
        
        Dueno nuevoDueno = new Dueno(nombre, documento, email);
        return duenoDAO.save(nuevoDueno);
    }
    
    /**
     * Registra un dueño completo con todos los datos
     */
    public Dueno registrarDuenoCompleto(String nombre, String documento, String direccion,
                                       String telefono, String email, String contactoEmergencia) 
            throws VeterinariaException {
        
        validarDatosDueno(nombre, documento, email);
        verificarDuplicados(documento, email);
        
        Dueno dueno = new Dueno(nombre, documento, email);
        dueno.setDireccion(direccion);
        dueno.setTelefono(telefono);
        dueno.setContactoEmergencia(contactoEmergencia);
        
        return duenoDAO.save(dueno);
    }
    
    /**
     * Valida los datos básicos de un dueño
     */
    private void validarDatosDueno(String nombre, String documento, String email) 
            throws VeterinariaException {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre completo es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (documento == null || documento.trim().isEmpty()) {
            throw new VeterinariaException("El documento de identidad es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (email == null || !email.contains("@")) {
            throw new VeterinariaException("El email debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Verifica que no existan dueños con el mismo documento o email
     */
    private void verificarDuplicados(String documento, String email) 
            throws VeterinariaException {
        
        Optional<Dueno> duenoPorDocumento = duenoDAO.findByDocumento(documento);
        if (duenoPorDocumento.isPresent()) {
            throw new VeterinariaException("Ya existe un dueño con el documento: " + documento, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
        
        Optional<Dueno> duenoPorEmail = duenoDAO.findByEmail(email);
        if (duenoPorEmail.isPresent()) {
            throw new VeterinariaException("Ya existe un dueño con el email: " + email, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
    }
    
    /**
     * Obtiene todos los dueños activos ordenados por nombre
     */
    public List<Dueno> listarDuenosActivos() throws VeterinariaException {
        return duenoDAO.findAllActiveOrdered();
    }
    
    /**
     * Busca un dueño por su ID
     */
    public Optional<Dueno> buscarDuenoPorId(Integer id) throws VeterinariaException {
        return duenoDAO.findById(id);
    }
    
    /**
     * Busca dueños por nombre (búsqueda parcial)
     */
    public List<Dueno> buscarDuenosPorNombre(String nombre) throws VeterinariaException {
        return duenoDAO.findByNombreContaining(nombre);
    }
    
    /**
     * Actualiza los datos de un dueño existente
     */
    public Dueno actualizarDueno(Dueno dueno) throws VeterinariaException {
        if (dueno.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un dueño sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el dueño existe
        Optional<Dueno> duenoExistente = duenoDAO.findById(dueno.getId());
        if (duenoExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el dueño con ID: " + dueno.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return duenoDAO.save(dueno);
    }
    
    /**
     * Elimina un dueño (soft delete)
     */
    public void eliminarDueno(Integer id) throws VeterinariaException {
        // Aquí se podrían agregar validaciones adicionales
        // como verificar si el dueño tiene mascotas activas
        duenoDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de dueños activos
     */
    public long contarDuenosActivos() throws VeterinariaException {
        return duenoDAO.count();
    }
}