package com.happyfeet.dao;

import com.happyfeet.model.MascotaAdopcion;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de mascotas en adopción
 */
public class MascotaAdopcionDAO extends BaseDAO<MascotaAdopcion> {
    
    public MascotaAdopcionDAO() throws VeterinariaException {
        super();
    }
    
    @Override
    protected String getTableName() {
        return "mascotas_adopcion";
    }
    
    @Override
    protected MascotaAdopcion mapResultSetToEntity(ResultSet rs) throws SQLException {
        MascotaAdopcion mascotaAdopcion = new MascotaAdopcion();
        mascotaAdopcion.setId(rs.getInt("id"));
        mascotaAdopcion.setMascotaId(rs.getInt("mascota_id"));
        
        Date fechaIngreso = rs.getDate("fecha_ingreso");
        if (fechaIngreso != null) {
            mascotaAdopcion.setFechaIngreso(fechaIngreso.toLocalDate());
        }
        
        mascotaAdopcion.setMotivoIngreso(rs.getString("motivo_ingreso"));
        mascotaAdopcion.setEstado(rs.getString("estado"));
        mascotaAdopcion.setHistoria(rs.getString("historia"));
        mascotaAdopcion.setTemperamento(rs.getString("temperamento"));
        mascotaAdopcion.setNecesidadesEspeciales(rs.getString("necesidades_especiales"));
        mascotaAdopcion.setFotoAdicionalUrl(rs.getString("foto_adicional_url"));
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            mascotaAdopcion.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        mascotaAdopcion.setActivo(rs.getBoolean("activo"));
        return mascotaAdopcion;
    }
    
    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, MascotaAdopcion entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setDate(2, Date.valueOf(entity.getFechaIngreso()));
        ps.setString(3, entity.getMotivoIngreso());
        ps.setString(4, entity.getEstado());
        ps.setString(5, entity.getHistoria());
        ps.setString(6, entity.getTemperamento());
        ps.setString(7, entity.getNecesidadesEspeciales());
        ps.setString(8, entity.getFotoAdicionalUrl());
    }
    
    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, MascotaAdopcion entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setDate(2, Date.valueOf(entity.getFechaIngreso()));
        ps.setString(3, entity.getMotivoIngreso());
        ps.setString(4, entity.getEstado());
        ps.setString(5, entity.getHistoria());
        ps.setString(6, entity.getTemperamento());
        ps.setString(7, entity.getNecesidadesEspeciales());
        ps.setString(8, entity.getFotoAdicionalUrl());
        ps.setInt(9, entity.getId());
    }
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO mascotas_adopcion (mascota_id, fecha_ingreso, motivo_ingreso, estado, historia, temperamento, necesidades_especiales, foto_adicional_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String getUpdateSQL() {
        return "UPDATE mascotas_adopcion SET mascota_id = ?, fecha_ingreso = ?, motivo_ingreso = ?, estado = ?, historia = ?, temperamento = ?, necesidades_especiales = ?, foto_adicional_url = ? WHERE id = ?";
    }
    
    /**
     * Busca mascotas en adopción por estado
     */
    public List<MascotaAdopcion> findByEstado(String estado) throws VeterinariaException {
        List<MascotaAdopcion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE estado = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por estado: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por estado", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca mascotas disponibles para adopción
     */
    public List<MascotaAdopcion> findMascotasDisponibles() throws VeterinariaException {
        return findByEstado("Disponible");
    }
    
    /**
     * Busca mascotas en adopción por ID de mascota
     */
    public Optional<MascotaAdopcion> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por mascota_id: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por mascota_id", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}