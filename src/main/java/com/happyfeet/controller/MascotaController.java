package com.happyfeet.controller;

import com.happyfeet.model.Mascota;
import com.happyfeet.service.MascotaService;
import com.happyfeet.exception.VeterinariaException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de mascotas
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class MascotaController {
    private final MascotaService mascotaService;
    
    public MascotaController() throws VeterinariaException {
        this.mascotaService = new MascotaService();
    }
    
    /**
     * Registra una nueva mascota en el sistema
     */
    public boolean registrarMascota(Integer duenoId, String nombre, Integer razaId, String sexo) {
        try {
            Mascota mascota = mascotaService.registrarMascota(duenoId, nombre, razaId, sexo);
            System.out.println("✅ Mascota registrada exitosamente: " + mascota);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar mascota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra una mascota con todos sus datos
     */
    public boolean registrarMascotaCompleta(Integer duenoId, String nombre, Integer razaId, 
                                           String fechaNacimiento, String sexo, Double pesoActual,
                                           String microchip, String alergias, String condicionesPreexistentes) {
        try {
            Mascota mascota = mascotaService.registrarMascotaCompleta(duenoId, nombre, razaId, 
                                                                     fechaNacimiento, sexo, pesoActual,
                                                                     microchip, alergias, condicionesPreexistentes);
            System.out.println("✅ Mascota registrada exitosamente: " + mascota);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar mascota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las mascotas activas
     */
    public void listarMascotas() {
        try {
            List<Mascota> mascotas = mascotaService.listarMascotasActivas();
            if (mascotas.isEmpty()) {
                System.out.println("No hay mascotas registradas.");
            } else {
                System.out.println("\n=== LISTA DE MASCOTAS ===");
                // Usando method reference para mayor legibilidad
                mascotas.forEach(System.out::println);
                System.out.println("Total: " + mascotas.size() + " mascotas registradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar mascotas: " + e.getMessage());
        }
    }
    
    /**
     * Busca una mascota por su ID
     */
    public void buscarMascotaPorId(Integer id) {
        try {
            Optional<Mascota> mascota = mascotaService.buscarMascotaPorId(id);
            if (mascota.isPresent()) {
                System.out.println("Mascota encontrada: " + mascota.get());
            } else {
                System.out.println("No se encontró ninguna mascota con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
        }
    }
    
    /**
     * Busca mascotas por nombre
     */
    public void buscarMascotasPorNombre(String nombre) {
        try {
            List<Mascota> mascotas = mascotaService.buscarMascotasPorNombre(nombre);
            if (mascotas.isEmpty()) {
                System.out.println("No se encontraron mascotas con nombre que contenga: " + nombre);
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                mascotas.forEach(System.out::println);
                System.out.println("Se encontraron " + mascotas.size() + " mascotas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar mascotas: " + e.getMessage());
        }
    }
    
    /**
     * Busca mascotas por dueño
     */
    public void buscarMascotasPorDuenoId(Integer duenoId) {
        try {
            List<Mascota> mascotas = mascotaService.buscarMascotasPorDuenoId(duenoId);
            if (mascotas.isEmpty()) {
                System.out.println("No se encontraron mascotas para el dueño con ID: " + duenoId);
            } else {
                System.out.println("\n=== MASCOTAS DEL DUEÑO ===");
                mascotas.forEach(System.out::println);
                System.out.println("Total: " + mascotas.size() + " mascotas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar mascotas por dueño: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una mascota del sistema (soft delete)
     */
    public boolean eliminarMascota(Integer id) {
        try {
            // Confirmación de eliminación
            Optional<Mascota> mascota = mascotaService.buscarMascotaPorId(id);
            if (mascota.isPresent()) {
                mascotaService.eliminarMascota(id);
                System.out.println("✅ Mascota eliminada exitosamente - ID: " + id);
                return true;
            } else {
                System.out.println("No se encontró la mascota con ID: " + id);
                return false;
            }
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al eliminar mascota: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de mascotas
     */
    public void mostrarEstadisticas() {
        try {
            long totalMascotas = mascotaService.contarMascotasActivas();
            System.out.println("\n=== ESTADÍSTICAS DE MASCOTAS ===");
            System.out.println("Total de mascotas registradas: " + totalMascotas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza el peso de una mascota
     */
    public boolean actualizarPesoMascota(Integer mascotaId, Double nuevoPeso) {
        try {
            mascotaService.actualizarPesoMascota(mascotaId, nuevoPeso);
            System.out.println("✅ Peso actualizado exitosamente para mascota ID: " + mascotaId);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar peso: " + e.getMessage());
            return false;
        }
    }
    /**
 * Cambia el dueño de una mascota
 */
public boolean cambiarDuenoMascota(Integer mascotaId, Integer nuevoDuenoId) {
    try {
        Mascota mascota = mascotaService.cambiarDuenoMascota(mascotaId, nuevoDuenoId);
        System.out.println("✅ Dueño de la mascota actualizado exitosamente: " + mascota);
        return true;
    } catch (VeterinariaException e) {
        System.err.println("❌ Error al cambiar dueño de la mascota: " + e.getMessage());
        return false;
    }
}
}