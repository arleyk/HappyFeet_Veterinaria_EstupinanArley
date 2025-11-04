package com.happyfeet.service;

import com.happyfeet.dao.ReporteDAO;
import com.happyfeet.exception.VeterinariaException;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para la generación de reportes gerenciales
 * Aplica el principio de responsabilidad única para reportes
 */
public class ReporteService {
    private final ReporteDAO reporteDAO;

    public ReporteService() throws VeterinariaException {
        this.reporteDAO = new ReporteDAO();
    }

    /**
     * Genera reporte de servicios más solicitados
     */
    public List<ReporteDAO.ServicioReporte> generarReporteServiciosMasSolicitados(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        validarFechas(fechaInicio, fechaFin);
        return reporteDAO.obtenerServiciosMasSolicitados(fechaInicio, fechaFin);
    }

    /**
     * Genera reporte de desempeño de veterinarios
     */
    public List<ReporteDAO.VeterinarioReporte> generarReporteDesempenioVeterinarios(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        validarFechas(fechaInicio, fechaFin);
        return reporteDAO.obtenerDesempenioVeterinarios(fechaInicio, fechaFin);
    }

    /**
     * Genera reporte de estado de inventario con alertas
     */
    public List<ReporteDAO.InventarioReporte> generarReporteEstadoInventario() throws VeterinariaException {
        return reporteDAO.obtenerEstadoInventario();
    }

    /**
     * Genera reporte de facturación por período
     */
    public List<ReporteDAO.FacturacionReporte> generarReporteFacturacionPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin, String periodo) throws VeterinariaException {
        validarFechas(fechaInicio, fechaFin);
        
        if (!List.of("DIARIO", "SEMANAL", "MENSUAL", "ANUAL").contains(periodo.toUpperCase())) {
            throw new VeterinariaException("Período no válido. Use: DIARIO, SEMANAL, MENSUAL o ANUAL", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        return reporteDAO.obtenerFacturacionPorPeriodo(fechaInicio, fechaFin, periodo);
    }

    /**
     * Valida que las fechas para reportes sean válidas
     */
    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        if (fechaInicio == null || fechaFin == null) {
            throw new VeterinariaException("Las fechas de inicio y fin son obligatorias", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaInicio.isAfter(fechaFin)) {
            throw new VeterinariaException("La fecha de inicio no puede ser posterior a la fecha de fin", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new VeterinariaException("La fecha de inicio no puede ser futura", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
    }

    /**
     * Genera un resumen ejecutivo combinando varios reportes
     */
   public ResumenEjecutivo generarResumenEjecutivo(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
    validarFechas(fechaInicio, fechaFin);
    
    // Obtener datos de diferentes reportes usando programación funcional
    List<ReporteDAO.ServicioReporte> servicios = generarReporteServiciosMasSolicitados(fechaInicio, fechaFin);
    List<ReporteDAO.VeterinarioReporte> veterinarios = generarReporteDesempenioVeterinarios(fechaInicio, fechaFin);
    List<ReporteDAO.FacturacionReporte> facturacion = generarReporteFacturacionPorPeriodo(fechaInicio, fechaFin, "MENSUAL");
    
    // Calcular métricas usando Stream API - CORREGIDO: usar campos directos en lugar de getters
    double ingresoTotal = facturacion.stream()
        .mapToDouble(r -> r.totalFacturado.doubleValue())
        .sum();
    
    long totalServicios = servicios.stream()
        .mapToLong(r -> r.totalVeces)
        .sum();
    
    long totalConsultas = veterinarios.stream()
        .mapToLong(r -> r.totalConsultas) // CORREGIDO: acceso directo al campo
        .sum();
    
    String servicioMasPopular = servicios.stream()
        .findFirst()
        .map(r -> r.nombre)
        .orElse("No hay datos");
    
    return new ResumenEjecutivo(
        fechaInicio, fechaFin, ingresoTotal, totalServicios, 
        totalConsultas, servicioMasPopular, servicios.size(), veterinarios.size()
    );
}    /**
     * Clase para el resumen ejecutivo
     */
    public static class ResumenEjecutivo {
        public final LocalDate fechaInicio;
        public final LocalDate fechaFin;
        public final double ingresoTotal;
        public final long totalServicios;
        public final long totalConsultas;
        public final String servicioMasPopular;
        public final int cantidadServiciosDiferentes;
        public final int veterinariosActivos;

        public ResumenEjecutivo(LocalDate fechaInicio, LocalDate fechaFin, double ingresoTotal,
                               long totalServicios, long totalConsultas, String servicioMasPopular,
                               int cantidadServiciosDiferentes, int veterinariosActivos) {
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.ingresoTotal = ingresoTotal;
            this.totalServicios = totalServicios;
            this.totalConsultas = totalConsultas;
            this.servicioMasPopular = servicioMasPopular;
            this.cantidadServiciosDiferentes = cantidadServiciosDiferentes;
            this.veterinariosActivos = veterinariosActivos;
        }
    }
}