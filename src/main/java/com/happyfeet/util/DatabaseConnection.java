package com.happyfeet.util;

import com.happyfeet.exception.VeterinariaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Clase Singleton para gestionar la conexión a la base de datos
 * Patrón Singleton: asegura una única instancia de conexión en la aplicación
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/happy_feet_veterinaria";
    private static final String USER = "root";
    private static final String PASSWORD = "arley123";
    
    private DatabaseConnection() throws VeterinariaException {
        try {
            // Cargar driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión a BD establecida exitosamente");
        } catch (ClassNotFoundException e) {
            throw new VeterinariaException("Driver de MySQL no encontrado", 
                                         VeterinariaException.ErrorType.DATABASE_ERROR);
        } catch (SQLException e) {
            throw new VeterinariaException("No se pudo establecer conexión con la BD: " + e.getMessage(), 
                                         VeterinariaException.ErrorType.DATABASE_ERROR);
        }
    }
    
    /**
     * Obtiene la instancia única de DatabaseConnection (Singleton)
     */
    public static DatabaseConnection getInstance() throws VeterinariaException {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Conexión a BD cerrada");
                instance = null; // Permitir nueva instancia si se necesita reconectar
            }
        } catch (SQLException e) {
            logger.warning("Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si la conexión está activa
     */
    public boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}