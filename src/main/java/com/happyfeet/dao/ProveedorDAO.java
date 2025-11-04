package com.happyfeet.dao;

import com.happyfeet.model.Proveedor;
import com.happyfeet.exception.VeterinariaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProveedorDAO extends BaseDAO<Proveedor> {
    
    public ProveedorDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "proveedores";
    }

    @Override
    protected Proveedor mapResultSetToEntity(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(rs.getInt("id"));
        proveedor.setNombreEmpresa(rs.getString("nombre_empresa"));
        proveedor.setContacto(rs.getString("contacto"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setEmail(rs.getString("email"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setSitioWeb(rs.getString("sitio_web"));
        proveedor.setActivo(rs.getBoolean("activo"));
        
        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
        if (fechaRegistro != null && !rs.wasNull()) {
            proveedor.setFechaRegistro(fechaRegistro.toLocalDateTime());
        }
        
        return proveedor;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Proveedor entity) throws SQLException {
        ps.setString(1, entity.getNombreEmpresa());
        setOptionalString(ps, 2, entity.getContacto());
        setOptionalString(ps, 3, entity.getTelefono());
        setOptionalString(ps, 4, entity.getEmail());
        setOptionalString(ps, 5, entity.getDireccion());
        setOptionalString(ps, 6, entity.getSitioWeb());
        ps.setBoolean(7, entity.getActivo());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Proveedor entity) throws SQLException {
        ps.setString(1, entity.getNombreEmpresa());
        setOptionalString(ps, 2, entity.getContacto());
        setOptionalString(ps, 3, entity.getTelefono());
        setOptionalString(ps, 4, entity.getEmail());
        setOptionalString(ps, 5, entity.getDireccion());
        setOptionalString(ps, 6, entity.getSitioWeb());
        ps.setBoolean(7, entity.getActivo());
        ps.setInt(8, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO proveedores (nombre_empresa, contacto, telefono, email, direccion, sitio_web, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE proveedores SET nombre_empresa = ?, contacto = ?, telefono = ?, email = ?, direccion = ?, sitio_web = ?, activo = ? WHERE id = ?";
    }

    // Métodos específicos para Proveedor
    
    public List<Proveedor> findByNombre(String nombre) throws VeterinariaException {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE nombre_empresa LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                proveedores.add(mapResultSetToEntity(rs));
            }
            return proveedores;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar proveedores por nombre: " + e.getMessage());
            throw new VeterinariaException("Error al buscar proveedores por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    public Optional<Proveedor> findByEmail(String email) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE email = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar proveedor por email: " + e.getMessage());
            throw new VeterinariaException("Error al buscar proveedor por email", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    // Método auxiliar para manejar valores String opcionales
    private void setOptionalString(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
        if (value != null && !value.trim().isEmpty()) {
            ps.setString(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.VARCHAR);
        }
    }
}