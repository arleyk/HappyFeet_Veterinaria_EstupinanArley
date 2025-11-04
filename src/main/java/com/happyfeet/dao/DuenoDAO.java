package com.happyfeet.dao;

import com.happyfeet.model.Dueno;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DAO específico para la entidad Dueño
 * Implementa operaciones de acceso a datos para la tabla 'duenos'
 */
public class DuenoDAO extends BaseDAO<Dueno> {
    public DuenoDAO() throws VeterinariaException {
        super(); // Llama al constructor de BaseDAO
    }
    @Override
    protected String getTableName() {
        return "duenos";
    }
    
    @Override
    protected Dueno mapResultSetToEntity(ResultSet rs) throws SQLException {
        Dueno dueno = new Dueno(
            rs.getInt("id"),
            rs.getString("nombre_completo"),
            rs.getString("documento_identidad"),
            rs.getString("direccion"),
            rs.getString("telefono"),
            rs.getString("email"),
            rs.getString("contacto_emergencia")
        );
        dueno.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        dueno.setActivo(rs.getBoolean("activo"));
        return dueno;
    }
    
    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Dueno dueno) throws SQLException {
        ps.setString(1, dueno.getNombreCompleto());
        ps.setString(2, dueno.getDocumentoIdentidad());
        ps.setString(3, dueno.getDireccion());
        ps.setString(4, dueno.getTelefono());
        ps.setString(5, dueno.getEmail());
        ps.setString(6, dueno.getContactoEmergencia());
        ps.setTimestamp(7, Timestamp.valueOf(dueno.getFechaRegistro()));
        ps.setBoolean(8, dueno.getActivo());
    }
    
    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Dueno dueno) throws SQLException {
        ps.setString(1, dueno.getNombreCompleto());
        ps.setString(2, dueno.getDocumentoIdentidad());
        ps.setString(3, dueno.getDireccion());
        ps.setString(4, dueno.getTelefono());
        ps.setString(5, dueno.getEmail());
        ps.setString(6, dueno.getContactoEmergencia());
        ps.setBoolean(7, dueno.getActivo());
        ps.setInt(8, dueno.getId());
    }
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO duenos (nombre_completo, documento_identidad, direccion, " +
               "telefono, email, contacto_emergencia, fecha_registro, activo) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String getUpdateSQL() {
        return "UPDATE duenos SET nombre_completo = ?, documento_identidad = ?, " +
               "direccion = ?, telefono = ?, email = ?, contacto_emergencia = ?, " +
               "activo = ? WHERE id = ?";
    }
    
    /**
     * Busca dueños por email usando Stream API - Programación funcional
     */
    public Optional<Dueno> findByEmail(String email) throws VeterinariaException {
        return findAll().stream()
                       .filter(dueno -> email.equalsIgnoreCase(dueno.getEmail()))
                       .findFirst();
    }
    
    /**
     * Busca dueños por documento de identidad
     */
    public Optional<Dueno> findByDocumento(String documento) throws VeterinariaException {
        String sql = "SELECT * FROM duenos WHERE documento_identidad = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por documento: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por documento", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene dueños activos ordenados por nombre usando Stream API
     */
    public List<Dueno> findAllActiveOrdered() throws VeterinariaException {
        return findAll().stream()
                       .sorted((d1, d2) -> d1.getNombreCompleto().compareToIgnoreCase(d2.getNombreCompleto()))
                       .collect(Collectors.toList());
    }
    
    /**
     * Busca dueños por nombre (búsqueda parcial)
     */
    public List<Dueno> findByNombreContaining(String nombre) throws VeterinariaException {
        String sql = "SELECT * FROM duenos WHERE nombre_completo LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            List<Dueno> resultados = new ArrayList<>();
            while (rs.next()) {
                resultados.add(mapResultSetToEntity(rs));
            }
            return resultados;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por nombre: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
}