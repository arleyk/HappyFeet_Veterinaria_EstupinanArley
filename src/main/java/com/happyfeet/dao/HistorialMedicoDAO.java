package com.happyfeet.dao;

import com.happyfeet.model.HistorialMedicoEspecial;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión del historial médico en la base de datos
 */
public class HistorialMedicoDAO extends BaseDAO<HistorialMedicoEspecial> {

    public HistorialMedicoDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "historial_medico";
    }

    @Override
    protected HistorialMedicoEspecial mapResultSetToEntity(ResultSet rs) throws SQLException {
        HistorialMedicoEspecial historial = new HistorialMedicoEspecial();
        historial.setId(rs.getInt("id"));
        historial.setMascotaId(rs.getInt("mascota_id"));
        
        Date fechaEvento = rs.getDate("fecha_evento");
        if (fechaEvento != null) {
            historial.setFechaEvento(fechaEvento.toLocalDate());
        }
        
        historial.setEventoTipoId(rs.getInt("evento_tipo_id"));
        historial.setDescripcion(rs.getString("descripcion"));
        historial.setDiagnostico(rs.getString("diagnostico"));
        historial.setTratamientoRecomendado(rs.getString("tratamiento_recomendado"));
        
        int veterinarioId = rs.getInt("veterinario_id");
        historial.setVeterinarioId(rs.wasNull() ? null : veterinarioId);
        
        int consultaId = rs.getInt("consulta_id");
        historial.setConsultaId(rs.wasNull() ? null : consultaId);
        
        int procedimientoId = rs.getInt("procedimiento_id");
        historial.setProcedimientoId(rs.wasNull() ? null : procedimientoId);
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            historial.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        historial.setActivo(rs.getBoolean("activo"));
        return historial;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, HistorialMedicoEspecial entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setDate(2, Date.valueOf(entity.getFechaEvento()));
        ps.setInt(3, entity.getEventoTipoId());
        ps.setString(4, entity.getDescripcion());
        ps.setString(5, entity.getDiagnostico());
        ps.setString(6, entity.getTratamientoRecomendado());
        
        if (entity.getVeterinarioId() != null) {
            ps.setInt(7, entity.getVeterinarioId());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        
        if (entity.getConsultaId() != null) {
            ps.setInt(8, entity.getConsultaId());
        } else {
            ps.setNull(8, Types.INTEGER);
        }
        
        if (entity.getProcedimientoId() != null) {
            ps.setInt(9, entity.getProcedimientoId());
        } else {
            ps.setNull(9, Types.INTEGER);
        }
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, HistorialMedicoEspecial entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setDate(2, Date.valueOf(entity.getFechaEvento()));
        ps.setInt(3, entity.getEventoTipoId());
        ps.setString(4, entity.getDescripcion());
        ps.setString(5, entity.getDiagnostico());
        ps.setString(6, entity.getTratamientoRecomendado());
        
        if (entity.getVeterinarioId() != null) {
            ps.setInt(7, entity.getVeterinarioId());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        
        if (entity.getConsultaId() != null) {
            ps.setInt(8, entity.getConsultaId());
        } else {
            ps.setNull(8, Types.INTEGER);
        }
        
        if (entity.getProcedimientoId() != null) {
            ps.setInt(9, entity.getProcedimientoId());
        } else {
            ps.setNull(9, Types.INTEGER);
        }
        
        ps.setInt(10, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO historial_medico (mascota_id, fecha_evento, evento_tipo_id, descripcion, " +
               "diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE historial_medico SET mascota_id=?, fecha_evento=?, evento_tipo_id=?, descripcion=?, " +
               "diagnostico=?, tratamiento_recomendado=?, veterinario_id=?, consulta_id=?, procedimiento_id=? " +
               "WHERE id=?";
    }

    /**
     * Busca historiales médicos por ID de mascota
     */
    public List<HistorialMedicoEspecial> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        List<HistorialMedicoEspecial> historiales = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ? AND activo = TRUE ORDER BY fecha_evento DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                historiales.add(mapResultSetToEntity(rs));
            }
            return historiales;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar historiales por mascota ID: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar historiales por mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Busca historiales médicos por tipo de evento
     */
    public List<HistorialMedicoEspecial> findByEventoTipoId(Integer eventoTipoId) throws VeterinariaException {
        List<HistorialMedicoEspecial> historiales = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE evento_tipo_id = ? AND activo = TRUE ORDER BY fecha_evento DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventoTipoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                historiales.add(mapResultSetToEntity(rs));
            }
            return historiales;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar historiales por tipo de evento: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar historiales por tipo de evento", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Busca historiales médicos por rango de fechas
     */
    public List<HistorialMedicoEspecial> findByFechaRange(Date fechaInicio, Date fechaFin) throws VeterinariaException {
        List<HistorialMedicoEspecial> historiales = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE fecha_evento BETWEEN ? AND ? AND activo = TRUE ORDER BY fecha_evento DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                historiales.add(mapResultSetToEntity(rs));
            }
            return historiales;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar historiales por rango de fechas: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar historiales por rango de fechas", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}