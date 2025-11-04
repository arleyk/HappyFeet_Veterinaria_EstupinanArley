package com.happyfeet.controller;

import com.happyfeet.model.JornadasVacunacion;
import com.happyfeet.service.JornadasVacunacionService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de jornadas de vacunación
 */
public class JornadasVacunacionController {
    private final JornadasVacunacionService jornadasVacunacionService;
    
    public JornadasVacunacionController() throws VeterinariaException {
        this.jornadasVacunacionService = new JornadasVacunacionService();
    }
    
    /**
     * Registra una nueva jornada de vacunación
     */
    public boolean registrarJornada(String nombre, LocalDate fecha, String ubicacion, String estado, 
                                   Integer capacidadMaxima, String descripcion, 
                                   LocalTime horaInicio, LocalTime horaFin) {
        try {
            JornadasVacunacion jornada = jornadasVacunacionService.registrarJornada(
                nombre, fecha, ubicacion, estado, capacidadMaxima, descripcion, horaInicio, horaFin);
            System.out.println("✅ Jornada de vacunación registrada exitosamente: " + jornada);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar jornada de vacunación: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las jornadas
     */
    public void listarJornadas() {
        try {
            List<JornadasVacunacion> jornadas = jornadasVacunacionService.listarJornadas();
            if (jornadas.isEmpty()) {
                System.out.println("No hay jornadas de vacunación registradas.");
            } else {
                System.out.println("\n=== LISTA DE JORNADAS DE VACUNACIÓN ===");
                jornadas.forEach(System.out::println);
                System.out.println("Total: " + jornadas.size() + " jornadas registradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar jornadas: " + e.getMessage());
        }
    }
    
    /**
     * Lista jornadas planificadas
     */
    public void listarJornadasPlanificadas() {
        try {
            List<JornadasVacunacion> jornadas = jornadasVacunacionService.buscarJornadasPlanificadas();
            if (jornadas.isEmpty()) {
                System.out.println("No hay jornadas planificadas.");
            } else {
                System.out.println("\n=== JORNADAS PLANIFICADAS ===");
                jornadas.forEach(System.out::println);
                System.out.println("Total: " + jornadas.size() + " jornadas planificadas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar jornadas planificadas: " + e.getMessage());
        }
    }
    
    /**
     * Busca una jornada por su ID
     */
    public void buscarJornadaPorId(Integer id) {
        try {
            Optional<JornadasVacunacion> jornada = jornadasVacunacionService.buscarJornadaPorId(id);
            if (jornada.isPresent()) {
                System.out.println("Jornada encontrada: " + jornada.get());
            } else {
                System.out.println("No se encontró ninguna jornada con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar jornada: " + e.getMessage());
        }
    }
    
    /**
     * Busca jornadas por estado
     */
    public void buscarJornadasPorEstado(String estado) {
        try {
            List<JornadasVacunacion> jornadas = jornadasVacunacionService.buscarJornadasPorEstado(estado);
            if (jornadas.isEmpty()) {
                System.out.println("No se encontraron jornadas con estado: " + estado);
            } else {
                System.out.println("\n=== JORNADAS CON ESTADO: " + estado + " ===");
                jornadas.forEach(System.out::println);
                System.out.println("Se encontraron " + jornadas.size() + " jornadas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar jornadas por estado: " + e.getMessage());
        }
    }
    
    /**
     * Cambia el estado de una jornada
     */
    public boolean cambiarEstadoJornada(Integer id, String nuevoEstado) {
        try {
            JornadasVacunacion jornada = jornadasVacunacionService.cambiarEstadoJornada(id, nuevoEstado);
            System.out.println("✅ Estado de jornada actualizado exitosamente: " + jornada);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al cambiar estado de jornada: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una jornada del sistema
     */
    public boolean eliminarJornada(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<JornadasVacunacion> jornada = jornadasVacunacionService.buscarJornadaPorId(id);
            if (jornada.isPresent()) {
                jornadasVacunacionService.eliminarJornada(id);
                System.out.println("✅ Jornada eliminada exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró la jornada con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar jornada: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Muestra estadísticas de jornadas
     */
    public void mostrarEstadisticas() {
        try {
            long totalJornadas = jornadasVacunacionService.contarJornadas();
            String estadisticas = jornadasVacunacionService.obtenerEstadisticas();
            
            System.out.println("\n=== ESTADÍSTICAS DE JORNADAS ===");
            System.out.println("Total de jornadas registradas: " + totalJornadas);
            System.out.println("Distribución por estado: " + estadisticas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}