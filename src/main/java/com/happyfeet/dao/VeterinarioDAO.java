package com.happyfeet.dao;

import com.happyfeet.model.Veterinario;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class VeterinarioDAO extends BaseDAO<Veterinario> {
    
    public VeterinarioDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "veterinarios";
    }

    @Override
    protected Veterinario mapResultSetToEntity(ResultSet rs) throws SQLException {
        Veterinario veterinario = new Veterinario();
        veterinario.setId(rs.getInt("id"));
        veterinario.setNombreCompleto(rs.getString("nombre_completo"));
        veterinario.setDocumentoIdentidad(rs.getString("documento_identidad"));
        veterinario.setLicenciaProfesional(rs.getString("licencia_profesional"));
        veterinario.setEspecialidad(rs.getString("especialidad"));
        veterinario.setTelefono(rs.getString("telefono"));
        veterinario.setEmail(rs.getString("email"));
        
        Date fechaContratacion = rs.getDate("fecha_contratacion");
        if (fechaContratacion != null && !rs.wasNull()) {
            veterinario.setFechaContratacion(fechaContratacion.toLocalDate());
        }
        
        veterinario.setActivo(rs.getBoolean("activo"));
        
        return veterinario;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Veterinario entity) throws SQLException {
        ps.setString(1, entity.getNombreCompleto());
        ps.setString(2, entity.getDocumentoIdentidad());
        ps.setString(3, entity.getLicenciaProfesional());
        setOptionalString(ps, 4, entity.getEspecialidad());
        setOptionalString(ps, 5, entity.getTelefono());
        setOptionalString(ps, 6, entity.getEmail());
        
        if (entity.getFechaContratacion() != null) {
            ps.setDate(7, Date.valueOf(entity.getFechaContratacion()));
        } else {
            ps.setNull(7, Types.DATE);
        }
        
        ps.setBoolean(8, entity.getActivo() != null ? entity.getActivo() : true);
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Veterinario entity) throws SQLException {
        ps.setString(1, entity.getNombreCompleto());
        ps.setString(2, entity.getDocumentoIdentidad());
        ps.setString(3, entity.getLicenciaProfesional());
        setOptionalString(ps, 4, entity.getEspecialidad());
        setOptionalString(ps, 5, entity.getTelefono());
        setOptionalString(ps, 6, entity.getEmail());
        
        if (entity.getFechaContratacion() != null) {
            ps.setDate(7, Date.valueOf(entity.getFechaContratacion()));
        } else {
            ps.setNull(7, Types.DATE);
        }
        
        ps.setBoolean(8, entity.getActivo() != null ? entity.getActivo() : true);
        ps.setInt(9, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO veterinarios (nombre_completo, documento_identidad, licencia_profesional, " +
               "especialidad, telefono, email, fecha_contratacion, activo) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE veterinarios SET nombre_completo = ?, documento_identidad = ?, licencia_profesional = ?, " +
               "especialidad = ?, telefono = ?, email = ?, fecha_contratacion = ?, activo = ? WHERE id = ?";
    }

    // Métodos específicos para Veterinario
    
    /**
     * Busca veterinario por documento de identidad
     */
    public Optional<Veterinario> findByDocumento(String documento) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE documento_identidad = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar veterinario por documento: " + e.getMessage());
            throw new VeterinariaException("Error al buscar veterinario por documento", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca veterinario por licencia profesional
     */
    public Optional<Veterinario> findByLicencia(String licencia) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE licencia_profesional = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, licencia);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar veterinario por licencia: " + e.getMessage());
            throw new VeterinariaException("Error al buscar veterinario por licencia", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca veterinario por email
     */
    public Optional<Veterinario> findByEmail(String email) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE email = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar veterinario por email: " + e.getMessage());
            throw new VeterinariaException("Error al buscar veterinario por email", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca veterinarios por especialidad
     */
    public List<Veterinario> findByEspecialidad(String especialidad) throws VeterinariaException {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE especialidad LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + especialidad + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                veterinarios.add(mapResultSetToEntity(rs));
            }
            return veterinarios;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar veterinarios por especialidad: " + e.getMessage());
            throw new VeterinariaException("Error al buscar veterinarios por especialidad", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca veterinarios por nombre (búsqueda parcial)
     */
    public List<Veterinario> findByNombre(String nombre) throws VeterinariaException {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE nombre_completo LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                veterinarios.add(mapResultSetToEntity(rs));
            }
            return veterinarios;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar veterinarios por nombre: " + e.getMessage());
            throw new VeterinariaException("Error al buscar veterinarios por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene todos los veterinarios activos ordenados por nombre
     */
    public List<Veterinario> findAllActiveOrdered() throws VeterinariaException {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE activo = TRUE ORDER BY nombre_completo";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                veterinarios.add(mapResultSetToEntity(rs));
            }
            return veterinarios;
            
        } catch (SQLException e) {
            logger.severe("Error al obtener veterinarios ordenados: " + e.getMessage());
            throw new VeterinariaException("Error al obtener veterinarios ordenados", 
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