package com.happyfeet.dao;

import com.happyfeet.model.Servicio;
import com.happyfeet.exception.VeterinariaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

/**
 * DAO para operaciones de base de datos relacionadas con servicios
 */
public class ServicioDAO extends BaseDAO<Servicio> {
    
    public ServicioDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "servicios";
    }

    @Override
    protected Servicio mapResultSetToEntity(ResultSet rs) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setId(rs.getInt("id"));
        servicio.setNombre(rs.getString("nombre"));
        servicio.setDescripcion(rs.getString("descripcion"));
        servicio.setCategoria(rs.getString("categoria"));
        servicio.setPrecioBase(rs.getBigDecimal("precio_base"));
        servicio.setDuracionEstimadaMinutos(rs.getInt("duracion_estimada_minutos"));
        servicio.setActivo(rs.getBoolean("activo"));
        servicio.setFechaRegistro(rs.getTimestamp("fecha_registro") != null ? 
                                rs.getTimestamp("fecha_registro").toLocalDateTime() : null);
        
        return servicio;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Servicio entity) throws SQLException {
        ps.setString(1, entity.getNombre());
        ps.setString(2, entity.getDescripcion());
        ps.setString(3, entity.getCategoria());
        ps.setBigDecimal(4, entity.getPrecioBase());
        ps.setInt(5, entity.getDuracionEstimadaMinutos());
        ps.setBoolean(6, entity.getActivo());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Servicio entity) throws SQLException {
        ps.setString(1, entity.getNombre());
        ps.setString(2, entity.getDescripcion());
        ps.setString(3, entity.getCategoria());
        ps.setBigDecimal(4, entity.getPrecioBase());
        ps.setInt(5, entity.getDuracionEstimadaMinutos());
        ps.setBoolean(6, entity.getActivo());
        ps.setInt(7, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO servicios (nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo) " +
               "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE servicios SET nombre = ?, descripcion = ?, categoria = ?, precio_base = ?, duracion_estimada_minutos = ?, activo = ? WHERE id = ?";
    }

    /**
     * Busca servicios por categoría
     */
    public List<Servicio> findByCategoria(String categoria) throws VeterinariaException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios WHERE categoria = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                servicios.add(mapResultSetToEntity(rs));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al buscar servicios por categoría: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar servicios por categoría", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return servicios;
    }

    /**
     * Busca servicios por nombre (búsqueda parcial)
     */
    public List<Servicio> findByNombreContaining(String nombre) throws VeterinariaException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios WHERE nombre LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                servicios.add(mapResultSetToEntity(rs));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al buscar servicios por nombre: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar servicios por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return servicios;
    }
}