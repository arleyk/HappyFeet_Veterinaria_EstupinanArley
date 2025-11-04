package com.happyfeet.dao;

import com.happyfeet.model.Adopcion;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de adopciones
 */
public class AdopcionDAO extends BaseDAO<Adopcion> {
    
    public AdopcionDAO() throws VeterinariaException {
        super();
    }
    
    @Override
    protected String getTableName() {
        return "adopciones";
    }
    
    @Override
    protected Adopcion mapResultSetToEntity(ResultSet rs) throws SQLException {
        Adopcion adopcion = new Adopcion();
        adopcion.setId(rs.getInt("id"));
        adopcion.setMascotaAdopcionId(rs.getInt("mascota_adopcion_id"));
        adopcion.setDuenoId(rs.getInt("dueno_id"));
        
        Date fechaAdopcion = rs.getDate("fecha_adopcion");
        if (fechaAdopcion != null) {
            adopcion.setFechaAdopcion(fechaAdopcion.toLocalDate());
        }
        
        adopcion.setContratoTexto(rs.getString("contrato_texto"));
        adopcion.setCondicionesEspeciales(rs.getString("condiciones_especiales"));
        adopcion.setSeguimientoRequerido(rs.getBoolean("seguimiento_requerido"));
        
        Date fechaPrimerSeguimiento = rs.getDate("fecha_primer_seguimiento");
        if (fechaPrimerSeguimiento != null) {
            adopcion.setFechaPrimerSeguimiento(fechaPrimerSeguimiento.toLocalDate());
        }
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            adopcion.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        adopcion.setActivo(rs.getBoolean("activo"));
        return adopcion;
    }
    
    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Adopcion entity) throws SQLException {
        ps.setInt(1, entity.getMascotaAdopcionId());
        ps.setInt(2, entity.getDuenoId());
        ps.setDate(3, Date.valueOf(entity.getFechaAdopcion()));
        ps.setString(4, entity.getContratoTexto());
        ps.setString(5, entity.getCondicionesEspeciales());
        ps.setBoolean(6, entity.getSeguimientoRequerido());
        
        if (entity.getFechaPrimerSeguimiento() != null) {
            ps.setDate(7, Date.valueOf(entity.getFechaPrimerSeguimiento()));
        } else {
            ps.setNull(7, Types.DATE);
        }
    }
    
    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Adopcion entity) throws SQLException {
        ps.setInt(1, entity.getMascotaAdopcionId());
        ps.setInt(2, entity.getDuenoId());
        ps.setDate(3, Date.valueOf(entity.getFechaAdopcion()));
        ps.setString(4, entity.getContratoTexto());
        ps.setString(5, entity.getCondicionesEspeciales());
        ps.setBoolean(6, entity.getSeguimientoRequerido());
        
        if (entity.getFechaPrimerSeguimiento() != null) {
            ps.setDate(7, Date.valueOf(entity.getFechaPrimerSeguimiento()));
        } else {
            ps.setNull(7, Types.DATE);
        }
        
        ps.setInt(8, entity.getId());
    }
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO adopciones (mascota_adopcion_id, dueno_id, fecha_adopcion, contrato_texto, condiciones_especiales, seguimiento_requerido, fecha_primer_seguimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String getUpdateSQL() {
        return "UPDATE adopciones SET mascota_adopcion_id = ?, dueno_id = ?, fecha_adopcion = ?, contrato_texto = ?, condiciones_especiales = ?, seguimiento_requerido = ?, fecha_primer_seguimiento = ? WHERE id = ?";
    }
    
    /**
     * Busca adopciones por dueño
     */
    public List<Adopcion> findByDuenoId(Integer duenoId) throws VeterinariaException {
        List<Adopcion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE dueno_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por dueno_id: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por dueno_id", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca adopción por mascota en adopción
     */
    public Optional<Adopcion> findByMascotaAdopcionId(Integer mascotaAdopcionId) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_adopcion_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaAdopcionId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por mascota_adopcion_id: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por mascota_adopcion_id", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}