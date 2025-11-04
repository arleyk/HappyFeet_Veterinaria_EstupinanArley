package com.happyfeet.dao;

import com.happyfeet.model.Cita;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CitaDAO extends BaseDAO<Cita> {
    
    public CitaDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "citas";
    }

    @Override
    protected Cita mapResultSetToEntity(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setId(rs.getInt("id"));
        cita.setMascotaId(rs.getInt("mascota_id"));
        
        Integer veterinarioId = rs.getInt("veterinario_id");
        if (!rs.wasNull()) {
            cita.setVeterinarioId(veterinarioId);
        }
        
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");
        if (fechaHora != null) {
            cita.setFechaHora(fechaHora.toLocalDateTime());
        }
        
        cita.setMotivo(rs.getString("motivo"));
        cita.setEstadoId(rs.getInt("estado_id"));
        cita.setObservaciones(rs.getString("observaciones"));
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        if (fechaCreacion != null) {
            cita.setFechaCreacion(fechaCreacion.toLocalDateTime());
        }
        
        return cita;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Cita entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        
        if (entity.getVeterinarioId() != null) {
            ps.setInt(2, entity.getVeterinarioId());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        
        if (entity.getFechaHora() != null) {
            ps.setTimestamp(3, Timestamp.valueOf(entity.getFechaHora()));
        } else {
            ps.setNull(3, Types.TIMESTAMP);
        }
        
        setOptionalString(ps, 4, entity.getMotivo());
        ps.setInt(5, entity.getEstadoId());
        setOptionalString(ps, 6, entity.getObservaciones());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Cita entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        
        if (entity.getVeterinarioId() != null) {
            ps.setInt(2, entity.getVeterinarioId());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        
        if (entity.getFechaHora() != null) {
            ps.setTimestamp(3, Timestamp.valueOf(entity.getFechaHora()));
        } else {
            ps.setNull(3, Types.TIMESTAMP);
        }
        
        setOptionalString(ps, 4, entity.getMotivo());
        ps.setInt(5, entity.getEstadoId());
        setOptionalString(ps, 6, entity.getObservaciones());
        ps.setInt(7, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO citas (mascota_id, veterinario_id, fecha_hora, motivo, estado_id, observaciones) " +
               "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE citas SET mascota_id = ?, veterinario_id = ?, fecha_hora = ?, " +
               "motivo = ?, estado_id = ?, observaciones = ? WHERE id = ?";
    }

    // Métodos específicos para Cita
    
    /**
     * Busca citas por mascota
     */
    public List<Cita> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                citas.add(mapResultSetToEntity(rs));
            }
            return citas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar citas por mascota: " + e.getMessage());
            throw new VeterinariaException("Error al buscar citas por mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca citas por veterinario
     */
    public List<Cita> findByVeterinarioId(Integer veterinarioId) throws VeterinariaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE veterinario_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, veterinarioId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                citas.add(mapResultSetToEntity(rs));
            }
            return citas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar citas por veterinario: " + e.getMessage());
            throw new VeterinariaException("Error al buscar citas por veterinario", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca citas por estado
     */
    public List<Cita> findByEstadoId(Integer estadoId) throws VeterinariaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE estado_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, estadoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                citas.add(mapResultSetToEntity(rs));
            }
            return citas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar citas por estado: " + e.getMessage());
            throw new VeterinariaException("Error al buscar citas por estado", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca citas por rango de fechas
     */
    public List<Cita> findByFechaRange(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws VeterinariaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE fecha_hora BETWEEN ? AND ? ORDER BY fecha_hora";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            ps.setTimestamp(2, Timestamp.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                citas.add(mapResultSetToEntity(rs));
            }
            return citas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar citas por rango de fechas: " + e.getMessage());
            throw new VeterinariaException("Error al buscar citas por rango de fechas", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene citas programadas para hoy
     */
    public List<Cita> findCitasHoy() throws VeterinariaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE DATE(fecha_hora) = CURDATE() ORDER BY fecha_hora";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                citas.add(mapResultSetToEntity(rs));
            }
            return citas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar citas de hoy: " + e.getMessage());
            throw new VeterinariaException("Error al buscar citas de hoy", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Actualiza el estado de una cita
     */
    public void actualizarEstado(Integer citaId, Integer nuevoEstadoId) throws VeterinariaException {
        String sql = "UPDATE citas SET estado_id = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstadoId);
            ps.setInt(2, citaId);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró la cita con ID: " + citaId, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Estado actualizado para cita ID: " + citaId + " - Nuevo estado: " + nuevoEstadoId);
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar estado de cita: " + e.getMessage());
            throw new VeterinariaException("Error al actualizar estado de cita", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Verifica disponibilidad de veterinario en una fecha/hora
     */
    public boolean verificarDisponibilidadVeterinario(Integer veterinarioId, LocalDateTime fechaHora) throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + 
                    " WHERE veterinario_id = ? AND fecha_hora = ? AND estado_id IN (1, 2, 3)"; // Programada, Confirmada, En Proceso
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, veterinarioId);
            ps.setTimestamp(2, Timestamp.valueOf(fechaHora));
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0; // Disponible si no hay citas en ese horario
            }
            return true;
            
        } catch (SQLException e) {
            logger.severe("Error al verificar disponibilidad: " + e.getMessage());
            throw new VeterinariaException("Error al verificar disponibilidad del veterinario", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Método auxiliar para manejar valores String opcionales
     */
    private void setOptionalString(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
        if (value != null && !value.trim().isEmpty()) {
            ps.setString(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.VARCHAR);
        }
    }
}