package com.happyfeet.service;

import com.happyfeet.model.Inventario;
import com.happyfeet.dao.InventarioDAO;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de inventario
 * Contiene la lógica de negocio y coordina las operaciones con el DAO
 */
public class InventarioService {
    private final InventarioDAO inventarioDAO;
    
    public InventarioService() throws VeterinariaException {
        this.inventarioDAO = new InventarioDAO();
    }
    
    /**
     * Registra un nuevo producto en el inventario
     */
    public Inventario registrarProducto(String nombreProducto, Integer productoTipoId, String descripcion,
                                       String fabricante, Integer proveedorId, String lote, Integer cantidadStock,
                                       Integer stockMinimo, String unidadMedida, String fechaVencimiento,
                                       Double precioCompra, Double precioVenta, Boolean requiereReceta) 
            throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosProducto(nombreProducto, productoTipoId, cantidadStock, stockMinimo, precioVenta);
        
        Inventario nuevoProducto = new Inventario(nombreProducto, productoTipoId, cantidadStock, stockMinimo, precioVenta);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setFabricante(fabricante);
        nuevoProducto.setProveedorId(proveedorId);
        nuevoProducto.setLote(lote);
        nuevoProducto.setUnidadMedida(unidadMedida);
        
        // Procesar fecha de vencimiento
        if (fechaVencimiento != null && !fechaVencimiento.trim().isEmpty()) {
            try {
                java.time.LocalDate fecha = java.time.LocalDate.parse(fechaVencimiento);
                nuevoProducto.setFechaVencimiento(fecha);
            } catch (java.time.format.DateTimeParseException e) {
                throw new VeterinariaException("Formato de fecha inválido. Use YYYY-MM-DD", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
        }
        
        nuevoProducto.setPrecioCompra(precioCompra);
        nuevoProducto.setRequiereReceta(requiereReceta);
        nuevoProducto.setActivo(true);
        
        return inventarioDAO.save(nuevoProducto);
    }
    
    /**
     * Valida los datos básicos de un producto
     */
    private void validarDatosProducto(String nombreProducto, Integer productoTipoId, Integer cantidadStock,
                                     Integer stockMinimo, Double precioVenta) throws VeterinariaException {
        
        if (nombreProducto == null || nombreProducto.trim().isEmpty()) {
            throw new VeterinariaException("El nombre del producto es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (productoTipoId == null || productoTipoId <= 0) {
            throw new VeterinariaException("El ID del tipo de producto es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (cantidadStock == null || cantidadStock < 0) {
            throw new VeterinariaException("La cantidad en stock no puede ser negativa", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (stockMinimo == null || stockMinimo < 0) {
            throw new VeterinariaException("El stock mínimo no puede ser negativo", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (precioVenta == null || precioVenta < 0) {
            throw new VeterinariaException("El precio de venta no puede ser negativo", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }
    
    /**
     * Obtiene todos los productos activos
     */
    public List<Inventario> listarProductosActivos() throws VeterinariaException {
        return inventarioDAO.findAll();
    }
    
    /**
     * Busca un producto por su ID
     */
    public Optional<Inventario> buscarProductoPorId(Integer id) throws VeterinariaException {
        return inventarioDAO.findById(id);
    }
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    public List<Inventario> buscarProductosPorNombre(String nombre) throws VeterinariaException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre de búsqueda no puede estar vacío", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return inventarioDAO.findByNombre(nombre);
    }
    
    /**
     * Busca productos por tipo
     */
    public List<Inventario> buscarProductosPorTipo(Integer tipoId) throws VeterinariaException {
        if (tipoId == null || tipoId <= 0) {
            throw new VeterinariaException("El ID del tipo debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        return inventarioDAO.findByTipo(tipoId);
    }
    
    /**
     * Obtiene productos con stock bajo
     */
    public List<Inventario> obtenerProductosConStockBajo() throws VeterinariaException {
        return inventarioDAO.findWithLowStock();
    }
    
    /**
     * Obtiene productos próximos a vencer
     */
    public List<Inventario> obtenerProductosProximosAVencer() throws VeterinariaException {
        return inventarioDAO.findProximosAVencer();
    }
    
    /**
     * Actualiza los datos de un producto existente
     */
    public Inventario actualizarProducto(Inventario producto) throws VeterinariaException {
        if (producto.getId() == null) {
            throw new VeterinariaException("No se puede actualizar un producto sin ID", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el producto existe
        Optional<Inventario> productoExistente = inventarioDAO.findById(producto.getId());
        if (productoExistente.isEmpty()) {
            throw new VeterinariaException("No se encontró el producto con ID: " + producto.getId(), 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        return inventarioDAO.save(producto);
    }
    
    /**
     * Elimina un producto (soft delete)
     */
    public void eliminarProducto(Integer id) throws VeterinariaException {
        // Aquí se podrían agregar validaciones adicionales
        // como verificar si el producto tiene movimientos o prescripciones
        inventarioDAO.delete(id);
    }
    
    /**
     * Actualiza el stock de un producto
     */
    public void actualizarStockProducto(Integer productoId, Integer nuevaCantidad) throws VeterinariaException {
        if (productoId == null || productoId <= 0) {
            throw new VeterinariaException("El ID del producto debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (nuevaCantidad == null || nuevaCantidad < 0) {
            throw new VeterinariaException("La cantidad no puede ser negativa", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Verificar que el producto existe
        Optional<Inventario> producto = inventarioDAO.findById(productoId);
        if (producto.isEmpty()) {
            throw new VeterinariaException("No se encontró el producto con ID: " + productoId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        inventarioDAO.actualizarStock(productoId, nuevaCantidad);
    }
    
    /**
     * Obtiene el número total de productos activos
     */
    public long contarProductosActivos() throws VeterinariaException {
        return inventarioDAO.count();
    }
}