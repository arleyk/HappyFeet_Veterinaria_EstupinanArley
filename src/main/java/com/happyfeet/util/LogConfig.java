package com.happyfeet.util;

import java.util.logging.*;

/**
 * Configurador del sistema de logging para la aplicación
 * Patrón Singleton para configuración centralizada
 */
public class LogConfig {
    private static boolean configured = false;
    private static final Logger logger = Logger.getLogger(LogConfig.class.getName());
    
    public static void configure() {
        if (!configured) {
            try {
                // Obtener el logger root
                Logger rootLogger = Logger.getLogger("");
                
                // Remover handlers por defecto (consola)
                Handler[] handlers = rootLogger.getHandlers();
                for (Handler handler : handlers) {
                    rootLogger.removeHandler(handler);
                }
                
                // Configurar FileHandler para escribir en archivo
                FileHandler fileHandler = new FileHandler("veterinaria_happy_feet.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.ALL);
                
                // Configurar ConsoleHandler para salida en consola
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setFormatter(new SimpleFormatter());
                consoleHandler.setLevel(Level.INFO);
                
                // Agregar handlers al root logger
                rootLogger.addHandler(fileHandler);
                rootLogger.addHandler(consoleHandler);
                rootLogger.setLevel(Level.ALL);
                
                configured = true;
                logger.info("Sistema de logging configurado correctamente");
                
            } catch (Exception e) {
                System.err.println("Error al configurar logging: " + e.getMessage());
                // Fallback: usar configuración básica
                Logger.getGlobal().setLevel(Level.INFO);
            }
        }
    }
    
    /**
     * Obtiene un logger para una clase específica
     */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}