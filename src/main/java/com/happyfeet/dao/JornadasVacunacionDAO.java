package com.happyfeet.dao;

import com.happyfeet.model.JornadasVacunacion;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de jornadas de vacunación
 */
public class JornadasVacunacionDAO extends BaseDAO<JornadasVacunacion> {
    
    public JornadasVacunacionDAO() throws VeterinariaException {
        super();
    }
    
    @Override
    protected String getTableName() {
        return "jornadas_vacunacion";
    }
    
    @Override
    protected JornadasVacunacion mapResultSetToEntity(ResultSet rs) throws SQLException {
        JornadasVacunacion jornada = new JornadasVacunacion();
        jornada.setId(rs.getInt("id"));
        jornada.setNombre(rs.getString("nombre"));
        
        Date fecha = rs.getDate("fecha");
        if (fecha != null) {
            jornada.setFecha(fecha.toLocalDate());
        }
        
        Time horaInicio = rs.getTime("hora_inicio");
        if (horaInicio != null) {
            jornada.setHoraInicio(horaInicio.toLocalTime());
        }
        
        Time horaFin = rs.getTime("hora_fin");
        if (horaFin != null) {
            jornada.setHoraFin(horaFin.toLocalTime());
        }
        
        jornada.setUbicacion(rs.getString("ubicacion"));
        jornada.setDescripcion(rs.getString("descripcion"));
        jornada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
        jornada.setEstado(rs.getString("estado"));
        
        // Nota: La tabla no tiene fecha_registro ni activo en el SQL proporcionado
        // Si se añaden estos campos, descomentar las siguientes líneas:
        /*
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            jornada.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        jornada.setActivo(rs.getBoolean("activo"));
        */
        
        return jornada;
    }
    
    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, JornadasVacunacion entity) throws SQLException {
        ps.setString(1, entity.getNombre());
        ps.setDate(2, Date.valueOf(entity.getFecha()));
        ps.setTime(3, entity.getHoraInicio() != null ? Time.valueOf(entity.getHoraInicio()) : null);
        ps.setTime(4, entity.getHoraFin() != null ? Time.valueOf(entity.getHoraFin()) : null);
        ps.setString(5, entity.getUbicacion());
        ps.setString(6, entity.getDescripcion());
        ps.setInt(7, entity.getCapacidadMaxima());
        ps.setString(8, entity.getEstado());
    }
    
    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, JornadasVacunacion entity) throws SQLException {
        ps.setString(1, entity.getNombre());
        ps.setDate(2, Date.valueOf(entity.getFecha()));
        ps.setTime(3, entity.getHoraInicio() != null ? Time.valueOf(entity.getHoraInicio()) : null);
        ps.setTime(4, entity.getHoraFin() != null ? Time.valueOf(entity.getHoraFin()) : null);
        ps.setString(5, entity.getUbicacion());
        ps.setString(6, entity.getDescripcion());
        ps.setInt(7, entity.getCapacidadMaxima());
        ps.setString(8, entity.getEstado());
        ps.setInt(9, entity.getId());
    }
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO jornadas_vacunacion (nombre, fecha, hora_inicio, hora_fin, ubicacion, descripcion, capacidad_maxima, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String getUpdateSQL() {
        return "UPDATE jornadas_vacunacion SET nombre = ?, fecha = ?, hora_inicio = ?, hora_fin = ?, ubicacion = ?, descripcion = ?, capacidad_maxima = ?, estado = ? WHERE id = ?";
    }
    
    /**
     * Busca jornadas por estado
     */
    public List<JornadasVacunacion> findByEstado(String estado) throws VeterinariaException {
        List<JornadasVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE estado = ?";
        
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
     * Busca jornadas por fecha
     */
    public List<JornadasVacunacion> findByFecha(LocalDate fecha) throws VeterinariaException {
        List<JornadasVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE fecha = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por fecha: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por fecha", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca jornadas planificadas (futuras)
     */
    public List<JornadasVacunacion> findJornadasPlanificadas() throws VeterinariaException {
        List<JornadasVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE fecha >= CURDATE() AND estado IN ('Planificada', 'En Curso')";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar jornadas planificadas: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar jornadas planificadas", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Override del método delete para adaptarse a la estructura de la tabla
     */
    @Override
    public void delete(Integer id) throws VeterinariaException {
        // Como la tabla no tiene campo activo, hacemos delete físico
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró el registro con ID: " + id, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Registro eliminado - ID: " + id);
            
        } catch (SQLException e) {
            logger.severe("Error al eliminar registro: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al eliminar", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Override del método count para adaptarse a la estructura de la tabla
     */
    @Override
    public long count() throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
            
        } catch (SQLException e) {
            logger.severe("Error al contar registros: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al contar registros", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}