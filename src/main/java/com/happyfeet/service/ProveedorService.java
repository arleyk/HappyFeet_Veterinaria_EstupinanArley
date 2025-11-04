package com.happyfeet.service;

import com.happyfeet.model.Proveedor;
import com.happyfeet.dao.ProveedorDAO;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de proveedores
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 */
public class ProveedorService {
    private final ProveedorDAO proveedorDAO;
    
    public ProveedorService() throws VeterinariaException {
        this.proveedorDAO = new ProveedorDAO();
    }
    
    /**
     * Registra un nuevo proveedor en el sistema
     */
    public Proveedor registrarProveedor(String nombreEmpresa, String contacto, String telefono, 
                                       String email, String direccion, String sitioWeb) 
            throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosProveedor(nombreEmpresa, contacto, telefono, email);
        
        // Verificar que el email sea único
        verificarEmailUnico(email);
        
        Proveedor nuevoProveedor = new Proveedor(nombreEmpresa, contacto, telefono, email);
        nuevoProveedor.setDireccion(direccion);
        nuevoProveedor.setSitioWeb(sitioWeb);
        nuevoProveedor.setActivo(true);
        
        return proveedorDAO.save(nuevoProveedor);
    }
    
    /**
     * Valida los datos básicos de un proveedor
     */
    private void validarDatosProveedor(String nombreEmpresa, String contacto, String telefono, String email) 
            throws VeterinariaException {
        
        if (nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de la empresa es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (contacto == null || contacto.trim().isEmpty()) {
            throw new VeterinariaException("El nombre del contacto es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new VeterinariaException("El teléfono es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new VeterinariaException("El email es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Validación básica de formato de email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new VeterinariaException("El formato del email no es válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Verifica que no existan proveedores con el mismo email
     */
    private void verificarEmailUnico(String email) throws VeterinariaException {
        Optional<Proveedor> proveedorPorEmail = proveedorDAO.findByEmail(email);
        if (proveedorPorEmail.isPresent()) {
            throw new VeterinariaException("Ya existe un proveedor con el email: " + email, 
                                         VeterinariaException.ErrorType.DUPLICATE_ENTRY_ERROR);
        }
    }
    
    /**
     * Obtiene todos los proveedores activos
     */
    public List<Proveedor> listarProveedoresActivos() throws VeterinariaException {
        return proveedorDAO.findAll();
    }
    
    /**
     * Busca un proveedor por su ID
     */
    public Optional<Proveedor> buscarProveedorPorId(Integer id) throws VeterinariaException {
        return proveedorDAO.findById(id);
    }
    
    /**
     * Busca proveedores por nombre (búsqueda parcial)
     */
    public List<Proveedor> buscarProveedoresPorNombre(String nombre) throws VeterinariaException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de búsqueda no puede estar vacío", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return proveedorDAO.findByNombre(nombre);
    }
    
    /**
     * Actualiza los datos de un proveedor existente
     */
    public Proveedor actualizarProveedor(Proveedor proveedor) throws VeterinariaException {
        if (proveedor.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un proveedor sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el proveedor existe
        Optional<Proveedor> proveedorExistente = proveedorDAO.findById(proveedor.getId());
        if (proveedorExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el proveedor con ID: " + proveedor.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return proveedorDAO.save(proveedor);
    }
    
    /**
     * Elimina un proveedor (soft delete)
     */
    public void eliminarProveedor(Integer id) throws VeterinariaException {
        // Aquí se podrían agregar validaciones adicionales
        // como verificar si el proveedor tiene productos asociados
        proveedorDAO.delete(id);
    }
    
    /**
     * Obtiene el número total de proveedores activos
     */
    public long contarProveedoresActivos() throws VeterinariaException {
        return proveedorDAO.count();
    }
}