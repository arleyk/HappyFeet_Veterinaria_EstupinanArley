package com.happyfeet.view;

import com.happyfeet.controller.ReporteController;
import com.happyfeet.exception.VeterinariaException;

import java.util.Scanner;

/**
 * Menú para la gestión de reportes gerenciales
 */
public class ReporteMenu extends BaseMenu {
    private final ReporteController reporteController;
    
    public ReporteMenu(Scanner scanner) throws VeterinariaException {
        super(scanner);
        this.reporteController = new ReporteController();
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== MÓDULO DE REPORTES ===");
            System.out.println("1. Servicios más solicitados");
            System.out.println("2. Desempeño del equipo veterinario");
            System.out.println("3. Estado del inventario");
            System.out.println("4. Análisis de facturación por período");
            System.out.println("5. Resumen ejecutivo");
            System.out.println("6. Reportes automáticos del mes");
            System.out.println("7. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    @Override
    protected void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> generarReporteServicios();
            case 2 -> generarReporteVeterinarios();
            case 3 -> generarReporteInventario();
            case 4 -> generarReporteFacturacion();
            case 5 -> generarResumenEjecutivo();
            case 6 -> generarReportesAutomaticos();
            case 7 -> cerrarMenu();
            default -> System.out.println("❌ Opción no válida.");
        }
    }
    
    /**
     * Genera reporte de servicios más solicitados
     */
    private void generarReporteServicios() {
        try {
            reporteController.mostrarMenuReportes();
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte de servicios: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de desempeño de veterinarios
     */
    private void generarReporteVeterinarios() {
        try {
            reporteController.mostrarMenuReportes();
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte de veterinarios: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de estado de inventario
     */
    private void generarReporteInventario() {
        try {
            reporteController.mostrarMenuReportes();
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte de inventario: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de facturación por período
     */
    private void generarReporteFacturacion() {
        try {
            reporteController.mostrarMenuReportes();
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte de facturación: " + e.getMessage());
        }
    }
    
    /**
     * Genera resumen ejecutivo
     */
    private void generarResumenEjecutivo() {
        try {
            reporteController.mostrarMenuReportes();
        } catch (Exception e) {
            System.err.println("❌ Error al generar resumen ejecutivo: " + e.getMessage());
        }
    }
    
    /**
     * Genera reportes automáticos al inicio del sistema
     */
    private void generarReportesAutomaticos() {
        try {
            reporteController.generarReportesAutomaticos();
        } catch (Exception e) {
            System.err.println("❌ Error al generar reportes automáticos: " + e.getMessage());
        }
    }
}