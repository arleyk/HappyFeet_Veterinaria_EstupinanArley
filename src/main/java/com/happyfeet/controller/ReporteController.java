package com.happyfeet.controller;

import com.happyfeet.service.ReporteService;
import com.happyfeet.dao.ReporteDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador para la generación de reportes gerenciales
 */
public class ReporteController {
    private final ReporteService reporteService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    
    public ReporteController() throws VeterinariaException {
        this.reporteService = new ReporteService();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    /**
     * Muestra el menú de reportes
     */
    public void mostrarMenuReportes() {
        while (true) {
            System.out.println("\n=== MÓDULO DE REPORTES ===");
            System.out.println("1. Servicios más solicitados");
            System.out.println("2. Desempeño del equipo veterinario");
            System.out.println("3. Estado del inventario");
            System.out.println("4. Análisis de facturación por período");
            System.out.println("5. Resumen ejecutivo");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> generarReporteServicios();
                case 2 -> generarReporteVeterinarios();
                case 3 -> generarReporteInventario();
                case 4 -> generarReporteFacturacion();
                case 5 -> generarResumenEjecutivo();
                case 6 -> { return; }
                default -> System.out.println("❌ Opción no válida.");
            }
        }
    }
    
    /**
     * Genera reporte de servicios más solicitados
     */
    private void generarReporteServicios() {
        try {
            System.out.println("\n--- Servicios Más Solicitados ---");
            LocalDate[] fechas = solicitarRangoFechas();
            
            // CORREGIDO: Usar el tipo correcto del DAO
            List<ReporteDAO.ServicioReporte> reporte = 
                reporteService.generarReporteServiciosMasSolicitados(fechas[0], fechas[1]);
            
            mostrarReporteServicios(reporte);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de desempeño de veterinarios
     */
    private void generarReporteVeterinarios() {
        try {
            System.out.println("\n--- Desempeño del Equipo Veterinario ---");
            LocalDate[] fechas = solicitarRangoFechas();
            
            // CORREGIDO: Usar el tipo correcto del DAO
            List<ReporteDAO.VeterinarioReporte> reporte = 
                reporteService.generarReporteDesempenioVeterinarios(fechas[0], fechas[1]);
            
            mostrarReporteVeterinarios(reporte);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de estado de inventario
     */
    private void generarReporteInventario() {
        try {
            System.out.println("\n--- Estado del Inventario ---");
            
            // CORREGIDO: Usar el tipo correcto del DAO
            List<ReporteDAO.InventarioReporte> reporte = 
                reporteService.generarReporteEstadoInventario();
            
            mostrarReporteInventario(reporte);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }
    
    /**
     * Genera reporte de facturación por período
     */
    private void generarReporteFacturacion() {
        try {
            System.out.println("\n--- Análisis de Facturación por Período ---");
            LocalDate[] fechas = solicitarRangoFechas();
            
            System.out.print("Período (DIARIO/SEMANAL/MENSUAL/ANUAL): ");
            String periodo = scanner.nextLine();
            
            // CORREGIDO: Usar el tipo correcto del DAO
            List<ReporteDAO.FacturacionReporte> reporte = 
                reporteService.generarReporteFacturacionPorPeriodo(fechas[0], fechas[1], periodo);
            
            mostrarReporteFacturacion(reporte, periodo);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }
    
    /**
     * Genera resumen ejecutivo
     */
    private void generarResumenEjecutivo() {
        try {
            System.out.println("\n--- Resumen Ejecutivo ---");
            LocalDate[] fechas = solicitarRangoFechas();
            
            // CORREGIDO: Este está bien - usa la clase del Service
            ReporteService.ResumenEjecutivo resumen = 
                reporteService.generarResumenEjecutivo(fechas[0], fechas[1]);
            
            mostrarResumenEjecutivo(resumen);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar resumen: " + e.getMessage());
        }
    }
    
    /**
     * Solicita rango de fechas al usuario
     */
    private LocalDate[] solicitarRangoFechas() {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        
        while (fechaInicio == null) {
            System.out.print("Fecha de inicio (yyyy-MM-dd): ");
            String fechaInicioStr = scanner.nextLine();
            try {
                fechaInicio = LocalDate.parse(fechaInicioStr, dateFormatter);
            } catch (Exception e) {
                System.out.println("❌ Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        }
        
        while (fechaFin == null) {
            System.out.print("Fecha de fin (yyyy-MM-dd): ");
            String fechaFinStr = scanner.nextLine();
            try {
                fechaFin = LocalDate.parse(fechaFinStr, dateFormatter);
                if (fechaFin.isBefore(fechaInicio)) {
                    System.out.println("❌ La fecha de fin no puede ser anterior a la fecha de inicio.");
                    fechaFin = null;
                }
            } catch (Exception e) {
                System.out.println("❌ Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        }
        
        return new LocalDate[]{fechaInicio, fechaFin};
    }
    
    /**
     * Muestra reporte de servicios
     */
    // CORREGIDO: Cambiar el tipo del parámetro
    private void mostrarReporteServicios(List<ReporteDAO.ServicioReporte> reporte) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                REPORTE - SERVICIOS MÁS SOLICITADOS");
        System.out.println("=".repeat(80));
        
        if (reporte.isEmpty()) {
            System.out.println("No hay datos para mostrar en el período seleccionado.");
            return;
        }
        
        System.out.printf("%-30s %-15s %-10s %-15s\n", 
            "SERVICIO", "CATEGORÍA", "VEZES", "INGRESO TOTAL");
        System.out.println("-".repeat(80));
        
        reporte.forEach(servicio -> {
            System.out.printf("%-30s %-15s %-10d $%,13.2f\n",
                truncateString(servicio.nombre, 28),
                truncateString(servicio.categoria, 13),
                servicio.totalVeces,
                servicio.ingresoTotal);
        });
        
        System.out.println("=".repeat(80));
    }
    
    /**
     * Muestra reporte de veterinarios
     */
    // CORREGIDO: Cambiar el tipo del parámetro
    private void mostrarReporteVeterinarios(List<ReporteDAO.VeterinarioReporte> reporte) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                 REPORTE - DESEMPEÑO VETERINARIOS");
        System.out.println("=".repeat(100));
        
        if (reporte.isEmpty()) {
            System.out.println("No hay datos para mostrar en el período seleccionado.");
            return;
        }
        
        System.out.printf("%-25s %-15s %-12s %-15s %-15s %-15s\n", 
            "VETERINARIO", "ESPECIALIDAD", "CONSULTAS", "PROCEDIMIENTOS", 
            "INGRESO CONS.", "INGRESO PROC.");
        System.out.println("-".repeat(100));
        
        reporte.forEach(vet -> {
            System.out.printf("%-25s %-15s %-12d %-15d $%,12.2f $%,12.2f\n",
                truncateString(vet.nombre, 23),
                truncateString(vet.especialidad, 13),
                vet.totalConsultas,
                vet.totalProcedimientos,
                vet.ingresoConsultas,
                vet.ingresoProcedimientos);
        });
        
        System.out.println("=".repeat(100));
    }
    
    /**
     * Muestra reporte de inventario
     */
    // CORREGIDO: Cambiar el tipo del parámetro
    private void mostrarReporteInventario(List<ReporteDAO.InventarioReporte> reporte) {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("                 REPORTE - ESTADO DE INVENTARIO");
        System.out.println("=".repeat(90));
        
        if (reporte.isEmpty()) {
            System.out.println("✅ No hay alertas de inventario en este momento.");
            return;
        }
        
        System.out.printf("%-25s %-10s %-8s %-8s %-12s %-10s\n", 
            "PRODUCTO", "TIPO", "STOCK", "MÍNIMO", "VENCIMIENTO", "ESTADO");
        System.out.println("-".repeat(90));
        
        reporte.forEach(item -> {
            String vencimiento = item.fechaVencimiento != null 
                ? item.fechaVencimiento.toString() 
                : "N/A";
            
            String estado = switch (item.estado) {
                case "STOCK_BAJO" -> "STOCK BAJO";
                case "PROXIMO_VENCER" -> "PRÓXIMO VENCER";
                default -> item.estado;
            };
            
            System.out.printf("%-25s %-10d %-8d %-8d %-12s %-10s\n",
                truncateString(item.nombreProducto, 23),
                item.productoTipoId,
                item.cantidadStock,
                item.stockMinimo,
                vencimiento,
                estado);
        });
        
        System.out.println("=".repeat(90));
    }
    
    /**
     * Muestra reporte de facturación
     */
    // CORREGIDO: Cambiar el tipo del parámetro
    private void mostrarReporteFacturacion(List<ReporteDAO.FacturacionReporte> reporte, String periodo) {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("          REPORTE - FACTURACIÓN POR PERÍODO (%s)\n", periodo);
        System.out.println("=".repeat(80));
        
        if (reporte.isEmpty()) {
            System.out.println("No hay datos para mostrar en el período seleccionado.");
            return;
        }
        
        System.out.printf("%-15s %-10s %-15s %-15s %-15s %-15s\n", 
            "PERÍODO", "FACTURAS", "SUBTOTAL", "IMPUESTO", "DESCUENTO", "TOTAL");
        System.out.println("-".repeat(80));
        
        reporte.forEach(fact -> {
            System.out.printf("%-15s %-10d $%,12.2f $%,12.2f $%,12.2f $%,12.2f\n",
                fact.periodo,
                fact.totalFacturas,
                fact.subtotalTotal,
                fact.impuestoTotal,
                fact.descuentoTotal,
                fact.totalFacturado);
        });
        
        // Total general
        double totalGeneral = reporte.stream()
            .mapToDouble(r -> r.totalFacturado.doubleValue())
            .sum();
        
        System.out.println("-".repeat(80));
        System.out.printf("%-15s %-10s %15s %15s %15s $%,12.2f\n",
            "TOTAL", "", "", "", "", totalGeneral);
        System.out.println("=".repeat(80));
    }
    
    /**
     * Muestra resumen ejecutivo
     */
    private void mostrarResumenEjecutivo(ReporteService.ResumenEjecutivo resumen) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("               RESUMEN EJECUTIVO");
        System.out.println("=".repeat(60));
        System.out.printf("Período: %s a %s\n", 
            resumen.fechaInicio, resumen.fechaFin);
        System.out.println("-".repeat(60));
        System.out.printf("Ingreso Total: $%,.2f\n", resumen.ingresoTotal);
        System.out.printf("Total de Servicios Prestados: %,d\n", resumen.totalServicios);
        System.out.printf("Total de Consultas Realizadas: %,d\n", resumen.totalConsultas);
        System.out.printf("Servicio Más Popular: %s\n", resumen.servicioMasPopular);
        System.out.printf("Cantidad de Servicios Diferentes: %,d\n", resumen.cantidadServiciosDiferentes);
        System.out.printf("Veterinarios Activos: %,d\n", resumen.veterinariosActivos);
        System.out.println("=".repeat(60));
    }
    
    /**
     * Trunca una cadena si es muy larga
     */
    private String truncateString(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
    
    /**
     * Genera reportes automáticos al inicio del sistema
     */
    public void generarReportesAutomaticos() {
        try {
            LocalDate hoy = LocalDate.now();
            LocalDate inicioMes = hoy.withDayOfMonth(1);
            
            System.out.println("\n--- REPORTES AUTOMÁTICOS DEL MES ---");
            
            // Reporte de inventario
            // CORREGIDO: Usar el tipo correcto del DAO
            List<ReporteDAO.InventarioReporte> inventario = 
                reporteService.generarReporteEstadoInventario();
            
            if (!inventario.isEmpty()) {
                System.out.println("\n⚠️  Alertas de Inventario:");
                inventario.forEach(item -> 
                    System.out.printf("  - %s: %s (Stock: %d)\n", 
                        item.nombreProducto, item.estado, item.cantidadStock));
            } else {
                System.out.println("\n✅ No hay alertas de inventario.");
            }
            
            // Resumen ejecutivo del mes
            ReporteService.ResumenEjecutivo resumen = 
                reporteService.generarResumenEjecutivo(inicioMes, hoy);
            
            System.out.printf("\n📊 Resumen del Mes (%s a %s):\n", inicioMes, hoy);
            System.out.printf("  - Ingreso Total: $%,.2f\n", resumen.ingresoTotal);
            System.out.printf("  - Total Servicios: %,d\n", resumen.totalServicios);
            System.out.printf("  - Servicio Más Popular: %s\n", resumen.servicioMasPopular);
            
        } catch (VeterinariaException e) {
            System.err.println("❌ Error al generar reportes automáticos: " + e.getMessage());
        }
    }
}