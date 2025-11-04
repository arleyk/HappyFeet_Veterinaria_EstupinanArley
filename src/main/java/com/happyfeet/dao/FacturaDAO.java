package com.happyfeet.dao;

import com.happyfeet.model.Factura;
import com.happyfeet.model.ItemFactura;
import com.happyfeet.exception.VeterinariaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones de base de datos relacionadas con facturas
 */
public class FacturaDAO extends BaseDAO<Factura> {
    
    public FacturaDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "facturas";
    }

    @Override
    protected Factura mapResultSetToEntity(ResultSet rs) throws SQLException {
        Factura factura = new Factura();
        factura.setId(rs.getInt("id"));
        factura.setDuenoId(rs.getInt("dueno_id"));
        factura.setNumeroFactura(rs.getString("numero_factura"));
        factura.setFechaEmision(rs.getTimestamp("fecha_emision").toLocalDateTime());
        factura.setSubtotal(rs.getBigDecimal("subtotal"));
        factura.setImpuesto(rs.getBigDecimal("impuesto"));
        factura.setDescuento(rs.getBigDecimal("descuento"));
        factura.setTotal(rs.getBigDecimal("total"));
        factura.setMetodoPago(Factura.MetodoPago.valueOf(rs.getString("metodo_pago")));
        factura.setEstado(Factura.EstadoFactura.valueOf(rs.getString("estado")));
        factura.setObservaciones(rs.getString("observaciones"));
        factura.setFechaRegistro(rs.getTimestamp("fecha_emision").toLocalDateTime());
        factura.setActivo(true); // Las facturas no tienen soft delete en el schema
        
        return factura;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Factura entity) throws SQLException {
        ps.setInt(1, entity.getDuenoId());
        ps.setString(2, entity.getNumeroFactura());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getFechaEmision()));
        ps.setBigDecimal(4, entity.getSubtotal());
        ps.setBigDecimal(5, entity.getImpuesto());
        ps.setBigDecimal(6, entity.getDescuento());
        ps.setBigDecimal(7, entity.getTotal());
        ps.setString(8, entity.getMetodoPago().name());
        ps.setString(9, entity.getEstado().name());
        ps.setString(10, entity.getObservaciones());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Factura entity) throws SQLException {
        ps.setString(1, entity.getNumeroFactura());
        ps.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getFechaEmision()));
        ps.setBigDecimal(3, entity.getSubtotal());
        ps.setBigDecimal(4, entity.getImpuesto());
        ps.setBigDecimal(5, entity.getDescuento());
        ps.setBigDecimal(6, entity.getTotal());
        ps.setString(7, entity.getMetodoPago().name());
        ps.setString(8, entity.getEstado().name());
        ps.setString(9, entity.getObservaciones());
        ps.setInt(10, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO facturas (dueno_id, numero_factura, fecha_emision, subtotal, impuesto, descuento, total, metodo_pago, estado, observaciones) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE facturas SET numero_factura = ?, fecha_emision = ?, subtotal = ?, impuesto = ?, descuento = ?, total = ?, metodo_pago = ?, estado = ?, observaciones = ? WHERE id = ?";
    }

    /**
     * Inserta un item de factura en la base de datos
     */
    public void insertarItemFactura(ItemFactura item) throws VeterinariaException {
        String sql = "INSERT INTO items_factura (factura_id, tipo_item, producto_id, servicio_id, servicio_descripcion, cantidad, precio_unitario, subtotal) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, item.getFacturaId());
            ps.setString(2, item.getTipoItem().name());
            
            if (item.getProductoId() != null) {
                ps.setInt(3, item.getProductoId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            
            if (item.getServicioId() != null) {
                ps.setInt(4, item.getServicioId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, item.getServicioDescripcion());
            ps.setInt(6, item.getCantidad());
            ps.setBigDecimal(7, item.getPrecioUnitario());
            ps.setBigDecimal(8, item.getSubtotal());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            logger.severe("Error al insertar item de factura: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al insertar item de factura", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Obtiene los items de una factura específica
     */
    public List<ItemFactura> obtenerItemsPorFacturaId(Integer facturaId) throws VeterinariaException {
        List<ItemFactura> items = new ArrayList<>();
        String sql = "SELECT * FROM items_factura WHERE factura_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, facturaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ItemFactura item = new ItemFactura();
                item.setId(rs.getInt("id"));
                item.setFacturaId(rs.getInt("factura_id"));
                item.setTipoItem(ItemFactura.TipoItem.valueOf(rs.getString("tipo_item")));
                
                Integer productoId = rs.getInt("producto_id");
                if (!rs.wasNull()) {
                    item.setProductoId(productoId);
                }
                
                Integer servicioId = rs.getInt("servicio_id");
                if (!rs.wasNull()) {
                    item.setServicioId(servicioId);
                }
                
                item.setServicioDescripcion(rs.getString("servicio_descripcion"));
                item.setCantidad(rs.getInt("cantidad"));
                item.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                
                items.add(item);
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener items de factura: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener items de factura", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return items;
    }

    /**
     * Obtiene facturas por dueño
     */
    public List<Factura> obtenerFacturasPorDueno(Integer duenoId) throws VeterinariaException {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE dueno_id = ? ORDER BY fecha_emision DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                facturas.add(mapResultSetToEntity(rs));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener facturas por dueño: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener facturas por dueño", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return facturas;
    }

    /**
     * Actualiza el estado de una factura
     */
    public void actualizarEstadoFactura(Integer facturaId, Factura.EstadoFactura estado) throws VeterinariaException {
        String sql = "UPDATE facturas SET estado = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setInt(2, facturaId);
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró la factura con ID: " + facturaId, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar estado de factura: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al actualizar estado de factura", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Obtiene el siguiente número de factura disponible
     */
    public String obtenerSiguienteNumeroFactura() throws VeterinariaException {
        String sql = "SELECT CONCAT('FACT-', LPAD(COALESCE(MAX(CAST(SUBSTRING(numero_factura, 6) AS UNSIGNED)), 0) + 1, 6, '0')) " +
                    "FROM facturas WHERE numero_factura LIKE 'FACT-%'";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString(1);
            }
            return "FACT-000001";
            
        } catch (SQLException e) {
            logger.severe("Error al obtener siguiente número de factura: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener número de factura", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}