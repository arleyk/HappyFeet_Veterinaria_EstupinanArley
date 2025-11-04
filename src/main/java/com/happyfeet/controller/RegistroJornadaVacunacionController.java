package com.happyfeet.controller;

import com.happyfeet.model.RegistroJornadaVacunacion;
import com.happyfeet.service.RegistroJornadaVacunacionService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de registros de jornadas de vacunación
 */
public class RegistroJornadaVacunacionController {
    private final RegistroJornadaVacunacionService registroJornadaVacunacionService;
    
    public RegistroJornadaVacunacionController() throws VeterinariaException {
        this.registroJornadaVacunacionService = new RegistroJornadaVacunacionService();
    }
    
    /**
     * Registra un nuevo registro de vacunación en jornada
     */
    public boolean registrarVacunacion(Integer jornadaId, Integer mascotaId, Integer duenoId, 
                                      Integer vacunaId, LocalDateTime fechaHora, String loteVacuna, 
                                      Integer veterinarioId, String observaciones) {
        try {
            RegistroJornadaVacunacion registro = registroJornadaVacunacionService.registrarVacunacion(
                jornadaId, mascotaId, duenoId, vacunaId, fechaHora, loteVacuna, veterinarioId, observaciones);
            System.out.println("✅ Registro de vacunación creado exitosamente: " + registro);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar vacunación: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los registros
     */
    public void listarRegistros() {
        try {
            List<RegistroJornadaVacunacion> registros = registroJornadaVacunacionService.listarRegistros();
            if (registros.isEmpty()) {
                System.out.println("No hay registros de vacunación.");
            } else {
                System.out.println("\n=== LISTA DE REGISTROS DE VACUNACIÓN ===");
                registros.forEach(System.out::println);
                System.out.println("Total: " + registros.size() + " registros");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar registros: " + e.getMessage());
        }
    }
    
    /**
     * Busca un registro por su ID
     */
    public void buscarRegistroPorId(Integer id) {
        try {
            Optional<RegistroJornadaVacunacion> registro = registroJornadaVacunacionService.buscarRegistroPorId(id);
            if (registro.isPresent()) {
                System.out.println("Registro encontrado: " + registro.get());
            } else {
                System.out.println("No se encontró ningún registro con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar registro: " + e.getMessage());
        }
    }
    
    /**
     * Busca registros por jornada
     */
    public void buscarRegistrosPorJornada(Integer jornadaId) {
        try {
            List<RegistroJornadaVacunacion> registros = registroJornadaVacunacionService.buscarRegistrosPorJornada(jornadaId);
            if (registros.isEmpty()) {
                System.out.println("No se encontraron registros para la jornada con ID: " + jornadaId);
            } else {
                System.out.println("\n=== REGISTROS DE LA JORNADA " + jornadaId + " ===");
                registros.forEach(System.out::println);
                System.out.println("Se encontraron " + registros.size() + " registros");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar registros por jornada: " + e.getMessage());
        }
    }
    
    /**
     * Busca registros por mascota
     */
    public void buscarRegistrosPorMascota(Integer mascotaId) {
        try {
            List<RegistroJornadaVacunacion> registros = registroJornadaVacunacionService.buscarRegistrosPorMascota(mascotaId);
            if (registros.isEmpty()) {
                System.out.println("No se encontraron registros para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n=== REGISTROS DE LA MASCOTA " + mascotaId + " ===");
                registros.forEach(System.out::println);
                System.out.println("Se encontraron " + registros.size() + " registros");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar registros por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Programa la próxima dosis de vacunación
     */
    public boolean programarProximaDosis(Integer registroId, java.time.LocalDate proximaDosis) {
        try {
            RegistroJornadaVacunacion registro = registroJornadaVacunacionService.programarProximaDosis(registroId, proximaDosis);
            System.out.println("✅ Próxima dosis programada exitosamente: " + registro);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al programar próxima dosis: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un registro del sistema
     */
    public boolean eliminarRegistro(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<RegistroJornadaVacunacion> registro = registroJornadaVacunacionService.buscarRegistroPorId(id);
            if (registro.isPresent()) {
                registroJornadaVacunacionService.eliminarRegistro(id);
                System.out.println("✅ Registro eliminado exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró el registro con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar registro: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Muestra estadísticas de registros
     */
    public void mostrarEstadisticas() {
        try {
            long totalRegistros = registroJornadaVacunacionService.contarRegistros();
            
            System.out.println("\n=== ESTADÍSTICAS DE REGISTROS ===");
            System.out.println("Total de registros: " + totalRegistros);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Muestra estadísticas de una jornada específica
     */
    public void mostrarEstadisticasJornada(Integer jornadaId) {
        try {
            long totalRegistros = registroJornadaVacunacionService.contarRegistrosPorJornada(jornadaId);
            
            System.out.println("\n=== ESTADÍSTICAS DE LA JORNADA " + jornadaId + " ===");
            System.out.println("Total de vacunaciones registradas: " + totalRegistros);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas de la jornada: " + e.getMessage());
        }
    }
}