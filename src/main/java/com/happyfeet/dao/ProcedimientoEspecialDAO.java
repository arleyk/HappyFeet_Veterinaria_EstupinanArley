package com.happyfeet.dao;

import com.happyfeet.model.ProcedimientoEspecial;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de procedimientos especiales en la base de datos
 */
public class ProcedimientoEspecialDAO extends BaseDAO<ProcedimientoEspecial> {
    
    public ProcedimientoEspecialDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "procedimientos_especiales";
    }

    @Override
    protected ProcedimientoEspecial mapResultSetToEntity(ResultSet rs) throws SQLException {
        ProcedimientoEspecial procedimiento = new ProcedimientoEspecial();
        procedimiento.setId(rs.getInt("id"));
        procedimiento.setMascotaId(rs.getInt("mascota_id"));
        procedimiento.setVeterinarioId(rs.getInt("veterinario_id"));
        procedimiento.setTipoProcedimiento(rs.getString("tipo_procedimiento"));
        procedimiento.setNombreProcedimiento(rs.getString("nombre_procedimiento"));
        
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");
        if (fechaHora != null) {
            procedimiento.setFechaHora(fechaHora.toLocalDateTime());
        }
        
        procedimiento.setDuracionEstimadaMinutos(rs.getInt("duracion_estimada_minutos"));
        if (rs.wasNull()) procedimiento.setDuracionEstimadaMinutos(null);
        
        procedimiento.setInformacionPreoperatoria(rs.getString("informacion_preoperatoria"));
        procedimiento.setDetalleProcedimiento(rs.getString("detalle_procedimiento"));
        procedimiento.setComplicaciones(rs.getString("complicaciones"));
        procedimiento.setSeguimientoPostoperatorio(rs.getString("seguimiento_postoperatorio"));
        
        Date proximoControl = rs.getDate("proximo_control");
        if (proximoControl != null) {
            procedimiento.setProximoControl(proximoControl.toLocalDate().atStartOfDay());
        }
        
        procedimiento.setEstado(rs.getString("estado"));
        procedimiento.setCostoProcedimiento(rs.getDouble("costo_procedimiento"));
        if (rs.wasNull()) procedimiento.setCostoProcedimiento(null);
        
        // Campos de BaseEntity
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            procedimiento.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        procedimiento.setActivo(rs.getBoolean("activo"));
        if (rs.wasNull()) procedimiento.setActivo(true);
        
        return procedimiento;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, ProcedimientoEspecial entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setInt(2, entity.getVeterinarioId());
        ps.setString(3, entity.getTipoProcedimiento());
        ps.setString(4, entity.getNombreProcedimiento());
        ps.setTimestamp(5, Timestamp.valueOf(entity.getFechaHora()));
        
        if (entity.getDuracionEstimadaMinutos() != null) {
            ps.setInt(6, entity.getDuracionEstimadaMinutos());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
        
        ps.setString(7, entity.getInformacionPreoperatoria());
        ps.setString(8, entity.getDetalleProcedimiento());
        ps.setString(9, entity.getComplicaciones());
        ps.setString(10, entity.getSeguimientoPostoperatorio());
        
        if (entity.getProximoControl() != null) {
            ps.setDate(11, Date.valueOf(entity.getProximoControl().toLocalDate()));
        } else {
            ps.setNull(11, Types.DATE);
        }
        
        ps.setString(12, entity.getEstado());
        
        if (entity.getCostoProcedimiento() != null) {
            ps.setDouble(13, entity.getCostoProcedimiento());
        } else {
            ps.setNull(13, Types.DECIMAL);
        }
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, ProcedimientoEspecial entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setInt(2, entity.getVeterinarioId());
        ps.setString(3, entity.getTipoProcedimiento());
        ps.setString(4, entity.getNombreProcedimiento());
        ps.setTimestamp(5, Timestamp.valueOf(entity.getFechaHora()));
        
        if (entity.getDuracionEstimadaMinutos() != null) {
            ps.setInt(6, entity.getDuracionEstimadaMinutos());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
        
        ps.setString(7, entity.getInformacionPreoperatoria());
        ps.setString(8, entity.getDetalleProcedimiento());
        ps.setString(9, entity.getComplicaciones());
        ps.setString(10, entity.getSeguimientoPostoperatorio());
        
        if (entity.getProximoControl() != null) {
            ps.setDate(11, Date.valueOf(entity.getProximoControl().toLocalDate()));
        } else {
            ps.setNull(11, Types.DATE);
        }
        
        ps.setString(12, entity.getEstado());
        
        if (entity.getCostoProcedimiento() != null) {
            ps.setDouble(13, entity.getCostoProcedimiento());
        } else {
            ps.setNull(13, Types.DECIMAL);
        }
        
        ps.setInt(14, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO procedimientos_especiales (mascota_id, veterinario_id, tipo_procedimiento, " +
               "nombre_procedimiento, fecha_hora, duracion_estimada_minutos, informacion_preoperatoria, " +
               "detalle_procedimiento, complicaciones, seguimiento_postoperatorio, proximo_control, estado, " +
               "costo_procedimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE procedimientos_especiales SET mascota_id = ?, veterinario_id = ?, tipo_procedimiento = ?, " +
               "nombre_procedimiento = ?, fecha_hora = ?, duracion_estimada_minutos = ?, " +
               "informacion_preoperatoria = ?, detalle_procedimiento = ?, complicaciones = ?, " +
               "seguimiento_postoperatorio = ?, proximo_control = ?, estado = ?, costo_procedimiento = ? " +
               "WHERE id = ?";
    }

    // Métodos específicos para ProcedimientoEspecial
    
    /**
     * Busca procedimientos por ID de mascota
     */
    public List<ProcedimientoEspecial> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ? AND activo = TRUE ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                procedimientos.add(mapResultSetToEntity(rs));
            }
            return procedimientos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar procedimientos por mascota: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar procedimientos por mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca procedimientos por ID de veterinario
     */
    public List<ProcedimientoEspecial> findByVeterinarioId(Integer veterinarioId) throws VeterinariaException {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE veterinario_id = ? AND activo = TRUE ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, veterinarioId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                procedimientos.add(mapResultSetToEntity(rs));
            }
            return procedimientos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar procedimientos por veterinario: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar procedimientos por veterinario", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca procedimientos por estado
     */
    public List<ProcedimientoEspecial> findByEstado(String estado) throws VeterinariaException {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE estado = ? AND activo = TRUE ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                procedimientos.add(mapResultSetToEntity(rs));
            }
            return procedimientos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar procedimientos por estado: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar procedimientos por estado", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca procedimientos por tipo
     */
    public List<ProcedimientoEspecial> findByTipo(String tipoProcedimiento) throws VeterinariaException {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE tipo_procedimiento = ? AND activo = TRUE ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipoProcedimiento);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                procedimientos.add(mapResultSetToEntity(rs));
            }
            return procedimientos;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar procedimientos por tipo: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar procedimientos por tipo", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Actualiza el estado de un procedimiento
     */
    public boolean updateEstado(Integer id, String nuevoEstado) throws VeterinariaException {
        String sql = "UPDATE " + getTableName() + " SET estado = ? WHERE id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar estado del procedimiento: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al actualizar estado", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Cuenta procedimientos por estado
     */
    public long countByEstado(String estado) throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE estado = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
            
        } catch (SQLException e) {
            logger.severe("Error al contar procedimientos por estado: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al contar procedimientos por estado", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}