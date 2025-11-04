package com.happyfeet.dao;

import com.happyfeet.model.Mascota;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MascotaDAO extends BaseDAO<Mascota> {
    
    public MascotaDAO() throws VeterinariaException {
        super();
    }

    @Override
    protected String getTableName() {
        return "mascotas";
    }

    @Override
    protected Mascota mapResultSetToEntity(ResultSet rs) throws SQLException {
        Mascota mascota = new Mascota(
            rs.getInt("dueno_id"),
            rs.getString("nombre"),
            rs.getInt("raza_id"),
            rs.getString("sexo")
        );
        
        // Establecer ID
        mascota.setId(rs.getInt("id"));
        
        // Campos opcionales
        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null && !rs.wasNull()) {
            mascota.setFechaNacimiento(fechaNacimiento.toLocalDate());
        }
        
        Double pesoActual = rs.getDouble("peso_actual");
        if (!rs.wasNull()) {
            mascota.setPesoActual(pesoActual);
        }
        
        mascota.setMicrochip(rs.getString("microchip"));
        mascota.setTatuaje(rs.getString("tatuaje"));
        mascota.setUrlFoto(rs.getString("url_foto"));
        mascota.setAlergias(rs.getString("alergias"));
        mascota.setCondicionesPreexistentes(rs.getString("condiciones_preexistentes"));
        
        return mascota;
    }

    @Override
    protected void setPreparedStatementInsert(PreparedStatement ps, Mascota entity) throws SQLException {
        ps.setInt(1, entity.getDuenoId());
        ps.setString(2, entity.getNombre());
        ps.setInt(3, entity.getRazaId());
        
        if (entity.getFechaNacimiento() != null) {
            ps.setDate(4, Date.valueOf(entity.getFechaNacimiento()));
        } else {
            ps.setNull(4, Types.DATE);
        }
        
        ps.setString(5, entity.getSexo());
        
        if (entity.getPesoActual() != null) {
            ps.setDouble(6, entity.getPesoActual());
        } else {
            ps.setNull(6, Types.DOUBLE);
        }
        
        setOptionalString(ps, 7, entity.getMicrochip());
        setOptionalString(ps, 8, entity.getTatuaje());
        setOptionalString(ps, 9, entity.getUrlFoto());
        setOptionalString(ps, 10, entity.getAlergias());
        setOptionalString(ps, 11, entity.getCondicionesPreexistentes());
    }

    @Override
    protected void setPreparedStatementUpdate(PreparedStatement ps, Mascota entity) throws SQLException {
        ps.setInt(1, entity.getDuenoId());
        ps.setString(2, entity.getNombre());
        ps.setInt(3, entity.getRazaId());
        
        if (entity.getFechaNacimiento() != null) {
            ps.setDate(4, Date.valueOf(entity.getFechaNacimiento()));
        } else {
            ps.setNull(4, Types.DATE);
        }
        
        ps.setString(5, entity.getSexo());
        
        if (entity.getPesoActual() != null) {
            ps.setDouble(6, entity.getPesoActual());
        } else {
            ps.setNull(6, Types.DOUBLE);
        }
        
        setOptionalString(ps, 7, entity.getMicrochip());
        setOptionalString(ps, 8, entity.getTatuaje());
        setOptionalString(ps, 9, entity.getUrlFoto());
        setOptionalString(ps, 10, entity.getAlergias());
        setOptionalString(ps, 11, entity.getCondicionesPreexistentes());
        ps.setInt(12, entity.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO mascotas (dueno_id, nombre, raza_id, fecha_nacimiento, sexo, " +
               "peso_actual, microchip, tatuaje, url_foto, alergias, condiciones_preexistentes) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE mascotas SET dueno_id = ?, nombre = ?, raza_id = ?, fecha_nacimiento = ?, " +
               "sexo = ?, peso_actual = ?, microchip = ?, tatuaje = ?, url_foto = ?, " +
               "alergias = ?, condiciones_preexistentes = ? WHERE id = ?";
    }

    // Métodos específicos para Mascota
    
    /**
     * Busca mascotas por dueño
     */
    public List<Mascota> findByDuenoId(Integer duenoId) throws VeterinariaException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE dueno_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToEntity(rs));
            }
            return mascotas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar mascotas por dueño: " + e.getMessage());
            throw new VeterinariaException("Error al buscar mascotas por dueño", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca mascota por número de microchip
     */
    public Optional<Mascota> findByMicrochip(String microchip) throws VeterinariaException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE microchip = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, microchip);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.severe("Error al buscar mascota por microchip: " + e.getMessage());
            throw new VeterinariaException("Error al buscar mascota por microchip", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca mascotas por nombre (búsqueda parcial)
     */
    public List<Mascota> findByNombre(String nombre) throws VeterinariaException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE nombre LIKE ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToEntity(rs));
            }
            return mascotas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar mascotas por nombre: " + e.getMessage());
            throw new VeterinariaException("Error al buscar mascotas por nombre", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Busca mascotas por raza
     */
    public List<Mascota> findByRazaId(Integer razaId) throws VeterinariaException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE raza_id = ? AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, razaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToEntity(rs));
            }
            return mascotas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar mascotas por raza: " + e.getMessage());
            throw new VeterinariaException("Error al buscar mascotas por raza", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Actualiza el peso de una mascota
     */
    public void actualizarPeso(Integer mascotaId, Double nuevoPeso) throws VeterinariaException {
        String sql = "UPDATE mascotas SET peso_actual = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (nuevoPeso != null) {
                ps.setDouble(1, nuevoPeso);
            } else {
                ps.setNull(1, Types.DOUBLE);
            }
            ps.setInt(2, mascotaId);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                throw new VeterinariaException("No se encontró la mascota con ID: " + mascotaId, 
                                             VeterinariaException.ErrorType.NOT_FOUND_ERROR);
            }
            
            logger.info("Peso actualizado para mascota ID: " + mascotaId + " - Nuevo peso: " + nuevoPeso);
            
        } catch (SQLException e) {
            logger.severe("Error al actualizar peso de mascota: " + e.getMessage());
            throw new VeterinariaException("Error al actualizar peso de mascota", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene mascotas con condiciones preexistentes
     */
    public List<Mascota> findWithCondicionesPreexistentes() throws VeterinariaException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE condiciones_preexistentes IS NOT NULL AND condiciones_preexistentes != '' AND activo = TRUE";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                mascotas.add(mapResultSetToEntity(rs));
            }
            return mascotas;
            
        } catch (SQLException e) {
            logger.severe("Error al buscar mascotas con condiciones preexistentes: " + e.getMessage());
            throw new VeterinariaException("Error al buscar mascotas con condiciones preexistentes", 
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