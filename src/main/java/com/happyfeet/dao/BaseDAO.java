package com.happyfeet.dao;

import com.happyfeet.model.BaseEntity;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * DAO base abstracta que implementa operaciones CRUD comunes
 * Aplica el principio DRY y Template Method para reutilizar código
 * Patrón DAO: separa la lógica de acceso a datos de la lógica de negocio
 */
public abstract class BaseDAO<T extends BaseEntity> {
    protected Connection connection;
    protected final Logger logger;
    
    public BaseDAO() throws VeterinariaException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.logger = Logger.getLogger(getClass().getName());
    }
    
    // Métodos abstractos que deben implementar las subclases
    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract void setPreparedStatementInsert(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void setPreparedStatementUpdate(PreparedStatement ps, T entity) throws SQLException;
    protected abstract String getInsertSQL();
    protected abstract String getUpdateSQL();
    
    /**
     * Busca una entidad por su ID
     */
    public Optional<T> findById(Integer id) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar por ID: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al buscar por ID", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene todas las entidades activas
     * Usa Stream API para procesamiento funcional de datos
     */
    public List<T> findAll() throws VeterinariaException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
            
        } catch (SQLException e) {
            logger.severe("Error al obtener todos los registros: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener registros", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Guarda una entidad (insert o update según si tiene ID)
     */
    public T save(T entity) throws VeterinariaException {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    
    /**
     * Inserta una nueva entidad en la base de datos
     */
    protected T insert(T entity) throws VeterinariaException {
        String sql = getInsertSQL();
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementInsert(ps, entity);
            ps.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            
            logger.info("Entidad creada: " + entity);
            return entity;
            
        } catch (SQLException e) {
            logger.severe("Error al insertar entidad: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al insertar", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Actualiza una entidad existente
     */
    protected T update(T entity) throws VeterinariaException {
        String sql = getUpdateSQL();
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setPreparedStatementUpdate(ps, entity);
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró la entidad con ID: " + entity.getId(), 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Entidad actualizada: " + entity);
            return entity;
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar entidad: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al actualizar", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Eliminación lógica (soft delete) de una entidad
     */
    public void delete(Integer id) throws VeterinariaException {
        String sql = "UPDATE " + getTableName() + " SET activo = FALSE WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró el registro con ID: " + id, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Registro eliminado (soft delete) - ID: " + id);
            
        } catch (SQLException e) {
            logger.severe("Error al eliminar registro: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al eliminar", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Cuenta el total de entidades activas
     */
    public long count() throws VeterinariaException {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE activo = TRUE";
        
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