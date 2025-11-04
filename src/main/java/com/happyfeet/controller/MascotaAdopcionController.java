package com.happyfeet.controller;

import com.happyfeet.model.MascotaAdopcion;
import com.happyfeet.service.MascotaAdopcionService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de mascotas en adopción
 */
public class MascotaAdopcionController {
    private final MascotaAdopcionService mascotaAdopcionService;
    
    public MascotaAdopcionController() throws VeterinariaException {
        this.mascotaAdopcionService = new MascotaAdopcionService();
    }
    
    /**
     * Registra una nueva mascota para adopción
     */
    public boolean registrarMascotaAdopcion(Integer mascotaId, String motivoIngreso, 
                                           String historia, String temperamento, 
                                           String necesidadesEspeciales) {
        try {
            MascotaAdopcion mascotaAdopcion = mascotaAdopcionService.registrarMascotaAdopcion(
                mascotaId, motivoIngreso, historia, temperamento, necesidadesEspeciales);
            System.out.println("✅ Mascota registrada para adopción exitosamente: " + mascotaAdopcion);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar mascota para adopción: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las mascotas en adopción activas
     */
    public void listarMascotasAdopcion() {
        try {
            List<MascotaAdopcion> mascotasAdopcion = mascotaAdopcionService.listarMascotasAdopcionActivas();
            if (mascotasAdopcion.isEmpty()) {
                System.out.println("No hay mascotas en adopción registradas.");
            } else {
                System.out.println("\n=== LISTA DE MASCOTAS EN ADOPCIÓN ===");
                mascotasAdopcion.forEach(System.out::println);
                System.out.println("Total: " + mascotasAdopcion.size() + " mascotas en adopción");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar mascotas en adopción: " + e.getMessage());
        }
    }
    
    /**
     * Lista mascotas disponibles para adopción
     */
    public void listarMascotasDisponibles() {
        try {
            List<MascotaAdopcion> mascotasDisponibles = mascotaAdopcionService.buscarMascotasDisponibles();
            if (mascotasDisponibles.isEmpty()) {
                System.out.println("No hay mascotas disponibles para adopción.");
            } else {
                System.out.println("\n=== MASCOTAS DISPONIBLES PARA ADOPCIÓN ===");
                mascotasDisponibles.forEach(System.out::println);
                System.out.println("Total: " + mascotasDisponibles.size() + " mascotas disponibles");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar mascotas disponibles: " + e.getMessage());
        }
    }
    
    /**
     * Busca una mascota en adopción por su ID
     */
    public void buscarMascotaAdopcionPorId(Integer id) {
        try {
            Optional<MascotaAdopcion> mascotaAdopcion = mascotaAdopcionService.buscarMascotaAdopcionPorId(id);
            if (mascotaAdopcion.isPresent()) {
                System.out.println("Mascota en adopción encontrada: " + mascotaAdopcion.get());
            } else {
                System.out.println("No se encontró ninguna mascota en adopción con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar mascota en adopción: " + e.getMessage());
        }
    }
    
    /**
     * Cambia el estado de una mascota en adopción
     */
    public boolean cambiarEstadoMascotaAdopcion(Integer id, String nuevoEstado) {
        try {
            MascotaAdopcion mascotaAdopcion = mascotaAdopcionService.cambiarEstadoMascotaAdopcion(id, nuevoEstado);
            System.out.println("✅ Estado de mascota actualizado exitosamente: " + mascotaAdopcion);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al cambiar estado de mascota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una mascota en adopción del sistema (soft delete)
     */
    public boolean eliminarMascotaAdopcion(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<MascotaAdopcion> mascotaAdopcion = mascotaAdopcionService.buscarMascotaAdopcionPorId(id);
            if (mascotaAdopcion.isPresent()) {
                mascotaAdopcionService.eliminarMascotaAdopcion(id);
                System.out.println("✅ Mascota en adopción eliminada exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró la mascota en adopción con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar mascota en adopción: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Muestra estadísticas de mascotas en adopción
     */
    public void mostrarEstadisticas() {
        try {
            long totalMascotas = mascotaAdopcionService.contarMascotasAdopcionActivas();
            String estadisticas = mascotaAdopcionService.obtenerEstadisticas();
            
            System.out.println("\n=== ESTADÍSTICAS DE MASCOTAS EN ADOPCIÓN ===");
            System.out.println("Total de mascotas en adopción: " + totalMascotas);
            System.out.println("Distribución por estado: " + estadisticas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}   