package com.happyfeet.dao;

import com.happyfeet.model.Inventario;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InventarioDAO extends BaseDAO<Inventario> {
    
    public InventarioDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "inventario";
    }

    @Override
    protected Inventario mapResultSetToEntity(ResultSet rs) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(rs.getInt("id"));
        inventario.setNombreProducto(rs.getString("nombre_producto"));
        inventario.setProductoTipoId(rs.getInt("producto_tipo_id"));
        inventario.setDescripcion(rs.getString("descripcion"));
        inventario.setFabricante(rs.getString("fabricante"));
        inventario.setProveedorId(rs.getInt("proveedor_id"));
        inventario.setLote(rs.getString("lote"));
        inventario.setCantidadStock(rs.getInt("cantidad_stock"));
        inventario.setStockMinimo(rs.getInt("stock_minimo"));
        inventario.setUnidadMedida(rs.getString("unidad_medida"));
        
        Date fechaVencimiento = rs.getDate("fecha_vencimiento");
        if (fechaVencimiento != null && !rs.wasNull()) {
            inventario.setFechaVencimiento(fechaVencimiento.toLocalDate());
        }
        
        inventario.setPrecioCompra(rs.getDouble("precio_compra"));
        inventario.setPrecioVenta(rs.getDouble("precio_venta"));
        inventario.setRequiereReceta(rs.getBoolean("requiere_receta"));
        inventario.setActivo(rs.getBoolean("activo"));
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null && !rs.wasNull()) {
            inventario.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        return inventario;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Inventario entity) throws SQLException {
        ps.setString(1, entity.getNombreProducto());
        ps.setInt(2, entity.getProductoTipoId());
        setOptionalString(ps, 3, entity.getDescripcion());
        setOptionalString(ps, 4, entity.getFabricante());
        setOptionalInt(ps, 5, entity.getProveedorId());
        setOptionalString(ps, 6, entity.getLote());
        ps.setInt(7, entity.getCantidadStock());
        ps.setInt(8, entity.getStockMinimo());
        setOptionalString(ps, 9, entity.getUnidadMedida());
        setOptionalDate(ps, 10, entity.getFechaVencimiento());
        setOptionalDouble(ps, 11, entity.getPrecioCompra());
        ps.setDouble(12, entity.getPrecioVenta());
        ps.setBoolean(13, entity.getRequiereReceta());
        ps.setBoolean(14, entity.getActivo());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Inventario entity) throws SQLException {
        ps.setString(1, entity.getNombreProducto());
        ps.setInt(2, entity.getProductoTipoId());
        setOptionalString(ps, 3, entity.getDescripcion());
        setOptionalString(ps, 4, entity.getFabricante());
        setOptionalInt(ps, 5, entity.getProveedorId());
        setOptionalString(ps, 6, entity.getLote());
        ps.setInt(7, entity.getCantidadStock());
        ps.setInt(8, entity.getStockMinimo());
        setOptionalString(ps, 9, entity.getUnidadMedida());
        setOptionalDate(ps, 10, entity.getFechaVencimiento());
        setOptionalDouble(ps, 11, entity.getPrecioCompra());
        ps.setDouble(12, entity.getPrecioVenta());
        ps.setBoolean(13, entity.getRequiereReceta());
        ps.setBoolean(14, entity.getActivo());
        ps.setInt(15, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, " +
               "proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, " +
               "precio_compra, precio_venta, requiere_receta, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE inventario SET nombre_producto = ?, producto_tipo_id = ?, descripcion = ?, " +
               "fabricante = ?, proveedor_id = ?, lote = ?, cantidad_stock = ?, stock_minimo = ?, " +
               "unidad_medida = ?, fecha_vencimiento = ?, precio_compra = ?, precio_venta = ?, " +
               "requiere_receta = ?, activo = ? WHERE id = ?";
    }

    // Métodos específicos para Inventario
    
    public List<Inventario> findByTipo(Integer tipoId) throws VeterinariaException {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE producto_tipo_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, tipoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                productos.add(mapResultSetToEntity(rs));
            }
            return productos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar productos por tipo: " + e.getMessage());
            throw new VeterinariaException("Error al buscar productos por tipo", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    public List<Inventario> findByNombre(String nombre) throws VeterinariaException {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE nombre_producto LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                productos.add(mapResultSetToEntity(rs));
            }
            return productos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar productos por nombre: " + e.getMessage());
            throw new VeterinariaException("Error al buscar productos por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    public List<Inventario> findWithLowStock() throws VeterinariaException {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE cantidad_stock <= stock_minimo AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                productos.add(mapResultSetToEntity(rs));
            }
            return productos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar productos con stock bajo: " + e.getMessage());
            throw new VeterinariaException("Error al buscar productos con stock bajo", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    public List<Inventario> findProximosAVencer() throws VeterinariaException {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE fecha_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                productos.add(mapResultSetToEntity(rs));
            }
            return productos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar productos próximos a vencer: " + e.getMessage());
            throw new VeterinariaException("Error al buscar productos próximos a vencer", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    public void actualizarStock(Integer productoId, Integer nuevaCantidad) throws VeterinariaException {
        String sql = "UPDATE inventario SET cantidad_stock = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, productoId);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró el producto con ID: " + productoId, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Stock actualizado para producto ID: " + productoId + " - Nuevo stock: " + nuevaCantidad);
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar stock de producto: " + e.getMessage());
            throw new VeterinariaException("Error al actualizar stock de producto", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    // Métodos auxiliares para manejar valores opcionales
    private void setOptionalString(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
        if (value != null && !value.trim().isEmpty()) {
            ps.setString(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.VARCHAR);
        }
    }
    
    private void setOptionalInt(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.INTEGER);
        }
    }
    
    private void setOptionalDouble(PreparedStatement ps, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            ps.setDouble(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.DOUBLE);
        }
    }
    
    private void setOptionalDate(PreparedStatement ps, int parameterIndex, LocalDate value) throws SQLException {
        if (value != null) {
            ps.setDate(parameterIndex, Date.valueOf(value));
        } else {
            ps.setNull(parameterIndex, Types.DATE);
        }
    }
}