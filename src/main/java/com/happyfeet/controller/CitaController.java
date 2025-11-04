package com.happyfeet.controller;

import com.happyfeet.model.Cita;
import com.happyfeet.service.CitaService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de citas
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class CitaController {
    private final CitaService citaService;
    
    public CitaController() throws VeterinariaException {
        this.citaService = new CitaService();
    }
    
    /**
     * Registra una nueva cita en el sistema
     */
    public boolean registrarCita(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora, 
                                String motivo, Integer estadoId) {
        try {
            Cita cita = citaService.registrarCita(mascotaId, veterinarioId, fechaHora, motivo, estadoId);
            System.out.println("✅ Cita registrada exitosamente: " + cita);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar cita: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra una cita con todos sus datos
     */
    public boolean registrarCitaCompleta(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora,
                                        String motivo, Integer estadoId, String observaciones) {
        try {
            Cita cita = citaService.registrarCitaCompleta(mascotaId, veterinarioId, fechaHora, 
                                                         motivo, estadoId, observaciones);
            System.out.println("✅ Cita registrada exitosamente: " + cita);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar cita: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las citas activas
     */
    public void listarCitas() {
        try {
            List<Cita> citas = citaService.listarCitasActivas();
            if (citas.isEmpty()) {
                System.out.println("No hay citas registradas.");
            } else {
                System.out.println("\n=== LISTA DE CITAS ===");
                citas.forEach(System.out::println);
                System.out.println("Total: " + citas.size() + " citas registradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
    }
    
    /**
     * Busca una cita por su ID
     */
    public void buscarCitaPorId(Integer id) {
        try {
            Optional<Cita> cita = citaService.buscarCitaPorId(id);
            if (cita.isPresent()) {
                System.out.println("Cita encontrada: " + cita.get());
            } else {
                System.out.println("No se encontró ninguna cita con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar cita: " + e.getMessage());
        }
    }
    
    /**
     * Busca citas por mascota
     */
    public void buscarCitasPorMascota(Integer mascotaId) {
        try {
            List<Cita> citas = citaService.buscarCitasPorMascota(mascotaId);
            if (citas.isEmpty()) {
                System.out.println("No se encontraron citas para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n=== CITAS DE LA MASCOTA ===");
                citas.forEach(System.out::println);
                System.out.println("Total: " + citas.size() + " citas encontradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar citas por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Busca citas por veterinario
     */
    public void buscarCitasPorVeterinario(Integer veterinarioId) {
        try {
            List<Cita> citas = citaService.buscarCitasPorVeterinario(veterinarioId);
            if (citas.isEmpty()) {
                System.out.println("No se encontraron citas para el veterinario con ID: " + veterinarioId);
            } else {
                System.out.println("\n=== CITAS DEL VETERINARIO ===");
                citas.forEach(System.out::println);
                System.out.println("Total: " + citas.size() + " citas encontradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar citas por veterinario: " + e.getMessage());
        }
    }
    
    /**
     * Busca citas por estado
     */
    public void buscarCitasPorEstado(Integer estadoId) {
        try {
            List<Cita> citas = citaService.buscarCitasPorEstado(estadoId);
            if (citas.isEmpty()) {
                System.out.println("No se encontraron citas con el estado ID: " + estadoId);
            } else {
                System.out.println("\n=== CITAS POR ESTADO ===");
                citas.forEach(System.out::println);
                System.out.println("Total: " + citas.size() + " citas encontradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar citas por estado: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene citas programadas para hoy
     */
    public void obtenerCitasHoy() {
        try {
            List<Cita> citas = citaService.obtenerCitasHoy();
            if (citas.isEmpty()) {
                System.out.println("No hay citas programadas para hoy.");
            } else {
                System.out.println("\n=== CITAS DE HOY ===");
                citas.forEach(System.out::println);
                System.out.println("Total: " + citas.size() + " citas para hoy");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener citas de hoy: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una cita del sistema (soft delete)
     */
    public boolean eliminarCita(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Cita> cita = citaService.buscarCitaPorId(id);
            if (cita.isPresent()) {
                citaService.eliminarCita(id);
                System.out.println("✅ Cita eliminada exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró la cita con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza el estado de una cita
     */
    public boolean actualizarEstadoCita(Integer citaId, Integer nuevoEstadoId) {
        try {
            citaService.actualizarEstadoCita(citaId, nuevoEstadoId);
            System.out.println("✅ Estado de cita actualizado exitosamente - ID: " + citaId);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar estado de cita: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de citas
     */
    public void mostrarEstadisticas() {
        try {
            long totalCitas = citaService.contarCitasActivas();
            System.out.println("\n=== ESTADÍSTICAS DE CITAS ===");
            System.out.println("Total de citas registradas: " + totalCitas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Verifica disponibilidad de veterinario
     */
    public void verificarDisponibilidad(Integer veterinarioId, LocalDateTime fechaHora) {
        try {
            boolean disponible = citaService.verificarDisponibilidad(veterinarioId, fechaHora);
            if (disponible) {
                System.out.println("✅ El veterinario está disponible en la fecha y hora seleccionada");
            } else {
                System.out.println("❌ El veterinario no está disponible en la fecha y hora seleccionada");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al verificar disponibilidad: " + e.getMessage());
        }
    }
}