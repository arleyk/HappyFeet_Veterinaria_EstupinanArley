package com.happyfeet.controller;

import com.happyfeet.model.ConsultaMedica;
import com.happyfeet.service.ConsultaMedicaService;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de consultas médicas
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 * Patrón MVC: Controlador
 */
public class ConsultaMedicaController {
    private final ConsultaMedicaService consultaMedicaService;
    
    public ConsultaMedicaController() throws VeterinariaException {
        this.consultaMedicaService = new ConsultaMedicaService();
    }
    
    /**
     * Registra una nueva consulta médica en el sistema
     */
    public boolean registrarConsultaMedica(Integer mascotaId, Integer veterinarioId, 
                                          LocalDateTime fechaHora, String motivo, 
                                          String sintomas, String diagnostico) {
        try {
            ConsultaMedica consulta = consultaMedicaService.registrarConsultaMedica(
                mascotaId, veterinarioId, fechaHora, motivo, sintomas, diagnostico);
            System.out.println("✅ Consulta médica registrada exitosamente: " + consulta);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar consulta médica: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registra una consulta médica completa con todos los datos
     */
    public boolean registrarConsultaMedicaCompleta(Integer mascotaId, Integer veterinarioId, 
                                                  Integer citaId, LocalDateTime fechaHora, 
                                                  String motivo, String sintomas, 
                                                  String diagnostico, String recomendaciones, 
                                                  String observaciones, Double pesoRegistrado, 
                                                  Double temperatura) {
        try {
            ConsultaMedica consulta = consultaMedicaService.registrarConsultaMedicaCompleta(
                mascotaId, veterinarioId, citaId, fechaHora, motivo, sintomas, 
                diagnostico, recomendaciones, observaciones, pesoRegistrado, temperatura);
            System.out.println("✅ Consulta médica registrada exitosamente: " + consulta);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al registrar consulta médica: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todas las consultas médicas
     */
    public void listarConsultasMedicas() {
        try {
            List<ConsultaMedica> consultas = consultaMedicaService.listarConsultasMedicas();
            if (consultas.isEmpty()) {
                System.out.println("No hay consultas médicas registradas.");
            } else {
                System.out.println("\n=== LISTA DE CONSULTAS MÉDICAS ===");
                consultas.forEach(System.out::println);
                System.out.println("Total: " + consultas.size() + " consultas registradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al listar consultas médicas: " + e.getMessage());
        }
    }
    
    /**
     * Busca una consulta médica por su ID
     */
    public void buscarConsultaMedicaPorId(Integer id) {
        try {
            Optional<ConsultaMedica> consulta = consultaMedicaService.buscarConsultaMedicaPorId(id);
            if (consulta.isPresent()) {
                System.out.println("Consulta médica encontrada: " + consulta.get());
            } else {
                System.out.println("No se encontró ninguna consulta médica con ID: " + id);
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar consulta médica: " + e.getMessage());
        }
    }
    
    /**
     * Busca consultas médicas por mascota
     */
    public void buscarConsultasPorMascota(Integer mascotaId) {
        try {
            List<ConsultaMedica> consultas = consultaMedicaService.buscarConsultasPorMascota(mascotaId);
            if (consultas.isEmpty()) {
                System.out.println("No se encontraron consultas médicas para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n=== CONSULTAS MÉDICAS DE LA MASCOTA ===");
                consultas.forEach(System.out::println);
                System.out.println("Total: " + consultas.size() + " consultas encontradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar consultas por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Busca consultas médicas por veterinario
     */
    public void buscarConsultasPorVeterinario(Integer veterinarioId) {
        try {
            List<ConsultaMedica> consultas = consultaMedicaService.buscarConsultasPorVeterinario(veterinarioId);
            if (consultas.isEmpty()) {
                System.out.println("No se encontraron consultas médicas para el veterinario con ID: " + veterinarioId);
            } else {
                System.out.println("\n=== CONSULTAS MÉDICAS DEL VETERINARIO ===");
                consultas.forEach(System.out::println);
                System.out.println("Total: " + consultas.size() + " consultas encontradas");
            }
        } catch (VeterinariaException e) {
            System.err.println("Error al buscar consultas por veterinario: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene estadísticas de consultas médicas
     */
    public void mostrarEstadisticas() {
        try {
            long totalConsultas = consultaMedicaService.contarConsultasMedicas();
            System.out.println("\n=== ESTADÍSTICAS DE CONSULTAS MÉDICAS ===");
            System.out.println("Total de consultas médicas registradas: " + totalConsultas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene estadísticas de consultas por mascota
     */
    public void mostrarEstadisticasPorMascota(Integer mascotaId) {
        try {
            long totalConsultas = consultaMedicaService.contarConsultasPorMascota(mascotaId);
            System.out.println("\n=== ESTADÍSTICAS DE CONSULTAS POR MASCOTA ===");
            System.out.println("Total de consultas para la mascota ID " + mascotaId + ": " + totalConsultas);
        } catch (VeterinariaException e) {
            System.err.println("Error al obtener estadísticas por mascota: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza una consulta médica existente
     */
    public boolean actualizarConsultaMedica(ConsultaMedica consulta) {
        try {
            ConsultaMedica consultaActualizada = consultaMedicaService.actualizarConsultaMedica(consulta);
            System.out.println("✅ Consulta médica actualizada exitosamente: " + consultaActualizada);
            return true;
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al actualizar consulta médica: " + e.getMessage());
            return false;
        }
    }
}