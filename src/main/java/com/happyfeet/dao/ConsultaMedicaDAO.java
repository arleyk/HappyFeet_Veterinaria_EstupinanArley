package com.happyfeet.dao;

import com.happyfeet.model.ConsultaMedica;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de consultas médicas en la base de datos
 */
public class ConsultaMedicaDAO extends BaseDAO<ConsultaMedica> {
    
    public ConsultaMedicaDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "consultas_medicas";
    }

    @Override
    protected ConsultaMedica mapResultSetToEntity(ResultSet rs) throws SQLException {
        ConsultaMedica consulta = new ConsultaMedica();
        consulta.setId(rs.getInt("id"));
        consulta.setMascotaId(rs.getInt("mascota_id"));
        consulta.setVeterinarioId(rs.getInt("veterinario_id"));
        consulta.setCitaId(rs.getInt("cita_id"));
        
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");
        if (fechaHora != null) {
            consulta.setFechaHora(fechaHora.toLocalDateTime());
        }
        
        consulta.setMotivo(rs.getString("motivo"));
        consulta.setSintomas(rs.getString("sintomas"));
        consulta.setDiagnostico(rs.getString("diagnostico"));
        consulta.setRecomendaciones(rs.getString("recomendaciones"));
        consulta.setObservaciones(rs.getString("observaciones"));
        consulta.setPesoRegistrado(rs.getDouble("peso_registrado"));
        if (rs.wasNull()) consulta.setPesoRegistrado(null);
        consulta.setTemperatura(rs.getDouble("temperatura"));
        if (rs.wasNull()) consulta.setTemperatura(null);
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_creacion");
        if (fechaRegistro != null) {
            consulta.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        return consulta;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, ConsultaMedica entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setInt(2, entity.getVeterinarioId());
        
        if (entity.getCitaId() != null) {
            ps.setInt(3, entity.getCitaId());
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        
        ps.setTimestamp(4, Timestamp.valueOf(entity.getFechaHora()));
        ps.setString(5, entity.getMotivo());
        ps.setString(6, entity.getSintomas());
        ps.setString(7, entity.getDiagnostico());
        ps.setString(8, entity.getRecomendaciones());
        ps.setString(9, entity.getObservaciones());
        
        if (entity.getPesoRegistrado() != null) {
            ps.setDouble(10, entity.getPesoRegistrado());
        } else {
            ps.setNull(10, Types.DECIMAL);
        }
        
        if (entity.getTemperatura() != null) {
            ps.setDouble(11, entity.getTemperatura());
        } else {
            ps.setNull(11, Types.DECIMAL);
        }
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, ConsultaMedica entity) throws SQLException {
        ps.setInt(1, entity.getMascotaId());
        ps.setInt(2, entity.getVeterinarioId());
        
        if (entity.getCitaId() != null) {
            ps.setInt(3, entity.getCitaId());
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        
        ps.setTimestamp(4, Timestamp.valueOf(entity.getFechaHora()));
        ps.setString(5, entity.getMotivo());
        ps.setString(6, entity.getSintomas());
        ps.setString(7, entity.getDiagnostico());
        ps.setString(8, entity.getRecomendaciones());
        ps.setString(9, entity.getObservaciones());
        
        if (entity.getPesoRegistrado() != null) {
            ps.setDouble(10, entity.getPesoRegistrado());
        } else {
            ps.setNull(10, Types.DECIMAL);
        }
        
        if (entity.getTemperatura() != null) {
            ps.setDouble(11, entity.getTemperatura());
        } else {
            ps.setNull(11, Types.DECIMAL);
        }
        
        ps.setInt(12, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO consultas_medicas (mascota_id, veterinario_id, cita_id, fecha_hora, " +
               "motivo, sintomas, diagnostico, recomendaciones, observaciones, peso_registrado, temperatura) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE consultas_medicas SET mascota_id = ?, veterinario_id = ?, cita_id = ?, " +
               "fecha_hora = ?, motivo = ?, sintomas = ?, diagnostico = ?, recomendaciones = ?, " +
               "observaciones = ?, peso_registrado = ?, temperatura = ? WHERE id = ?";
    }

    // Métodos específicos para ConsultaMedica
    
    /**
     * Busca consultas médicas por ID de mascota
     */
    public List<ConsultaMedica> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ? ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                consultas.add(mapResultSetToEntity(rs));
            }
            return consultas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar consultas por mascota: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar consultas por mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca consultas médicas por ID de veterinario
     */
    public List<ConsultaMedica> findByVeterinarioId(Integer veterinarioId) throws VeterinariaException {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE veterinario_id = ? ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, veterinarioId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                consultas.add(mapResultSetToEntity(rs));
            }
            return consultas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar consultas por veterinario: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar consultas por veterinario", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca consultas médicas por rango de fechas
     */
    public List<ConsultaMedica> findByFechaBetween(java.sql.Date startDate, java.sql.Date endDate) throws VeterinariaException {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE DATE(fecha_hora) BETWEEN ? AND ? ORDER BY fecha_hora DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                consultas.add(mapResultSetToEntity(rs));
            }
            return consultas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar consultas por rango de fechas: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar consultas por rango de fechas", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Cuenta el número de consultas por mascota
     */
    public long countByMascotaId(Integer mascotaId) throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE mascota_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
            
        } catch (SQLException e) {
            logger.severe("Error al contar consultas por mascota: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al contar consultas por mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}