package com.happyfeet;

import com.happyfeet.view.MainMenu;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.LogConfig;
import com.happyfeet.util.DatabaseConnection;

import java.util.logging.Logger;

/**
 * Clase principal del Sistema de Gestión Veterinaria Happy Feet
 * Punto de entrada de la aplicación
 * 
 * Características implementadas:
 * ✅ Arquitectura MVC (Modelo-Vista-Controlador)
 * ✅ Principios SOLID
 * ✅ Patrones de diseño: Singleton, DAO, MVC, Template Method
 * ✅ Programación funcional con Stream API y lambdas
 * ✅ Manejo robusto de errores y logging
 * ✅ Conexión a base de datos MySQL con JDBC
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        try {
            // Configurar sistema de logging
            LogConfig.configure();
            logger.info("Iniciando Sistema de Gestión Veterinaria Happy Feet");
            
            // Verificar conexión a la base de datos
            if (!DatabaseConnection.getInstance().isConnectionValid()) {
                throw new VeterinariaException("No se pudo establecer conexión con la base de datos");
            }
            
            // Iniciar menú principal
            MainMenu mainMenu = new MainMenu();
            mainMenu.iniciar();
            
        } catch (VeterinariaException e) {
            logger.severe("Error crítico en el sistema: " + e.getMessage());
            System.err.println("❌ Error crítico: " + e.getMessage());
            System.err.println("Tipo de error: " + e.getErrorType());
            
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause().getMessage());
            }
            
        } catch (Exception e) {
            logger.severe("Error inesperado: " + e.getMessage());
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            // Asegurar que la conexión a BD se cierre
            try {
                DatabaseConnection.getInstance().closeConnection();
            } catch (Exception e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}