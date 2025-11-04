package com.happyfeet.dao;

import com.happyfeet.model.RegistroJornadaVacunacion;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de registros de jornadas de vacunación
 */
public class RegistroJornadaVacunacionDAO extends BaseDAO<RegistroJornadaVacunacion> {
    
    public RegistroJornadaVacunacionDAO() throws VeterinariaException {
        super();
    }
    
    @Override
    protected String getTableName() {
        return "registro_jornada_vacunacion";
    }
    
    @Override
    protected RegistroJornadaVacunacion mapResultSetToEntity(ResultSet rs) throws SQLException {
        RegistroJornadaVacunacion registro = new RegistroJornadaVacunacion();
        registro.setId(rs.getInt("id"));
        registro.setJornadaId(rs.getInt("jornada_id"));
        registro.setMascotaId(rs.getInt("mascota_id"));
        registro.setDuenoId(rs.getInt("dueno_id"));
        registro.setVacunaId(rs.getInt("vacuna_id"));
        registro.setVeterinarioId(rs.getInt("veterinario_id"));
        
        Timestamp fechaHora = rs.getTimestamp("fecha_hora");
        if (fechaHora != null) {
            registro.setFechaHora(fechaHora.toLocalDateTime());
        }
        
        registro.setLoteVacuna(rs.getString("lote_vacuna"));
        
        Date proximaDosis = rs.getDate("proxima_dosis");
        if (proximaDosis != null) {
            registro.setProximaDosis(proximaDosis.toLocalDate());
        }
        
        registro.setObservaciones(rs.getString("observaciones"));
        
        // Nota: La tabla no tiene fecha_registro ni activo en el SQL proporcionado
        // Si se añaden estos campos, descomentar las siguientes líneas:
        /*
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null) {
            registro.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        registro.setActivo(rs.getBoolean("activo"));
        */
        
        return registro;
    }
    
    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, RegistroJornadaVacunacion entity) throws SQLException {
        ps.setInt(1, entity.getJornadaId());
        ps.setInt(2, entity.getMascotaId());
        ps.setInt(3, entity.getDuenoId());
        ps.setInt(4, entity.getVacunaId());
        ps.setObject(5, entity.getVeterinarioId(), Types.INTEGER);
        ps.setTimestamp(6, Timestamp.valueOf(entity.getFechaHora()));
        ps.setString(7, entity.getLoteVacuna());
        ps.setDate(8, entity.getProximaDosis() != null ? Date.valueOf(entity.getProximaDosis()) : null);
        ps.setString(9, entity.getObservaciones());
    }
    
    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, RegistroJornadaVacunacion entity) throws SQLException {
        ps.setInt(1, entity.getJornadaId());
        ps.setInt(2, entity.getMascotaId());
        ps.setInt(3, entity.getDuenoId());
        ps.setInt(4, entity.getVacunaId());
        ps.setObject(5, entity.getVeterinarioId(), Types.INTEGER);
        ps.setTimestamp(6, Timestamp.valueOf(entity.getFechaHora()));
        ps.setString(7, entity.getLoteVacuna());
        ps.setDate(8, entity.getProximaDosis() != null ? Date.valueOf(entity.getProximaDosis()) : null);
        ps.setString(9, entity.getObservaciones());
        ps.setInt(10, entity.getId());
    }
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO registro_jornada_vacunacion (jornada_id, mascota_id, dueno_id, vacuna_id, veterinario_id, fecha_hora, lote_vacuna, proxima_dosis, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String getUpdateSQL() {
        return "UPDATE registro_jornada_vacunacion SET jornada_id = ?, mascota_id = ?, dueno_id = ?, vacuna_id = ?, veterinario_id = ?, fecha_hora = ?, lote_vacuna = ?, proxima_dosis = ?, observaciones = ? WHERE id = ?";
    }
    
    /**
     * Busca registros por jornada
     */
    public List<RegistroJornadaVacunacion> findByJornadaId(Integer jornadaId) throws VeterinariaException {
        List<RegistroJornadaVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE jornada_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, jornadaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por jornada_id: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por jornada_id", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca registros por mascota
     */
    public List<RegistroJornadaVacunacion> findByMascotaId(Integer mascotaId) throws VeterinariaException {
        List<RegistroJornadaVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE mascota_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por mascota_id: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por mascota_id", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca registros por dueño
     */
    public List<RegistroJornadaVacunacion> findByDuenoId(Integer duenoId) throws VeterinariaException {
        List<RegistroJornadaVacunacion> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE dueno_id = ?";
        
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
     * Cuenta registros por jornada
     */
    public long countByJornadaId(Integer jornadaId) throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE jornada_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, jornadaId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
            
        } catch (SQLException e) {
            logger.severe("Error al contar registros por jornada: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al contar registros por jornada", 
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