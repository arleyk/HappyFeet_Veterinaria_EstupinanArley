package com.happyfeet.dao;

import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO especializado para generar reportes del sistema
 * Aplica principios SOLID: Single Responsibility - solo se encarga de reportes
 */
public class ReporteDAO {
    protected Connection connection;
    protected final java.util.logging.Logger logger;
    
    public ReporteDAO() throws VeterinariaException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.logger = java.util.logging.Logger.getLogger(getClass().getName());
    }

    /**
     * Reporte de servicios más solicitados en un período
     */
    public List<ServicioReporte> obtenerServiciosMasSolicitados(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        List<ServicioReporte> reportes = new ArrayList<>();
        String sql = """
            SELECT s.nombre, s.categoria, COUNT(if.servicio_id) as total_veces, 
                   SUM(if.subtotal) as ingreso_total
            FROM items_factura if
            JOIN servicios s ON if.servicio_id = s.id
            JOIN facturas f ON if.factura_id = f.id
            WHERE f.fecha_emision BETWEEN ? AND ?
            GROUP BY s.id, s.nombre, s.categoria
            ORDER BY total_veces DESC, ingreso_total DESC
            LIMIT 10
            """;
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                reportes.add(new ServicioReporte(
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getInt("total_veces"),
                    rs.getBigDecimal("ingreso_total")
                ));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener reporte de servicios: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener reporte de servicios", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return reportes;
    }

    /**
     * Reporte de desempeño de veterinarios
     */
    public List<VeterinarioReporte> obtenerDesempenioVeterinarios(LocalDate fechaInicio, LocalDate fechaFin) throws VeterinariaException {
        List<VeterinarioReporte> reportes = new ArrayList<>();
        String sql = """
            SELECT v.nombre_completo, v.especialidad,
                   COUNT(DISTINCT c.id) as total_consultas,
                   COUNT(DISTINCT p.id) as total_procedimientos,
                   SUM(CASE WHEN c.id IS NOT NULL THEN s.precio_base ELSE 0 END) as ingreso_consultas,
                   SUM(CASE WHEN p.id IS NOT NULL THEN p.costo_procedimiento ELSE 0 END) as ingreso_procedimientos
            FROM veterinarios v
            LEFT JOIN consultas_medicas c ON v.id = c.veterinario_id AND c.fecha_hora BETWEEN ? AND ?
            LEFT JOIN procedimientos_especiales p ON v.id = p.veterinario_id AND p.fecha_hora BETWEEN ? AND ?
            LEFT JOIN servicios s ON s.nombre LIKE '%consulta%'
            WHERE v.activo = TRUE
            GROUP BY v.id, v.nombre_completo, v.especialidad
            ORDER BY (total_consultas + total_procedimientos) DESC
            """;
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ps.setDate(3, Date.valueOf(fechaInicio));
            ps.setDate(4, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                reportes.add(new VeterinarioReporte(
                    rs.getString("nombre_completo"),
                    rs.getString("especialidad"),
                    rs.getInt("total_consultas"),
                    rs.getInt("total_procedimientos"),
                    rs.getBigDecimal("ingreso_consultas"),
                    rs.getBigDecimal("ingreso_procedimientos")
                ));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener reporte de veterinarios: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener reporte de veterinarios", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return reportes;
    }

    /**
     * Reporte de estado de inventario con alertas
     */
    public List<InventarioReporte> obtenerEstadoInventario() throws VeterinariaException {
        List<InventarioReporte> reportes = new ArrayList<>();
        String sql = """
            SELECT nombre_producto, producto_tipo_id, cantidad_stock, stock_minimo, 
                   fecha_vencimiento, precio_venta,
                   CASE 
                     WHEN cantidad_stock <= stock_minimo THEN 'STOCK_BAJO'
                     WHEN fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) THEN 'PROXIMO_VENCER'
                     ELSE 'NORMAL'
                   END as estado
            FROM inventario 
            WHERE activo = TRUE 
            AND (cantidad_stock <= stock_minimo OR fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 30 DAY))
            ORDER BY estado, fecha_vencimiento, cantidad_stock
            """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                reportes.add(new InventarioReporte(
                    rs.getString("nombre_producto"),
                    rs.getInt("producto_tipo_id"),
                    rs.getInt("cantidad_stock"),
                    rs.getInt("stock_minimo"),
                    rs.getDate("fecha_vencimiento") != null ? 
                        rs.getDate("fecha_vencimiento").toLocalDate() : null,
                    rs.getBigDecimal("precio_venta"),
                    rs.getString("estado")
                ));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener reporte de inventario: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener reporte de inventario", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return reportes;
    }

    /**
     * Reporte de facturación por período
     */
    public List<FacturacionReporte> obtenerFacturacionPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin, String periodo) throws VeterinariaException {
        List<FacturacionReporte> reportes = new ArrayList<>();
        String groupByClause = getGroupByClause(periodo);
        String sql = String.format("""
            SELECT %s as periodo, 
                   COUNT(*) as total_facturas,
                   SUM(subtotal) as subtotal_total,
                   SUM(impuesto) as impuesto_total,
                   SUM(descuento) as descuento_total,
                   SUM(total) as total_facturado
            FROM facturas 
            WHERE fecha_emision BETWEEN ? AND ?
            AND estado = 'PAGADA'
            GROUP BY %s
            ORDER BY periodo
            """, groupByClause, groupByClause);
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                reportes.add(new FacturacionReporte(
                    rs.getString("periodo"),
                    rs.getInt("total_facturas"),
                    rs.getBigDecimal("subtotal_total"),
                    rs.getBigDecimal("impuesto_total"),
                    rs.getBigDecimal("descuento_total"),
                    rs.getBigDecimal("total_facturado")
                ));
            }
            
        } catch (SQLException e) {
            logger.severe("Error al obtener reporte de facturación: " + e.getMessage());
            throw new VeterinariaException("Error en operación de BD al obtener reporte de facturación", 
                                         e, VeterinariaException.ErrorType.DATABASE_ERROR);
        }
        
        return reportes;
    }

    private String getGroupByClause(String periodo) {
        return switch (periodo.toUpperCase()) {
            case "DIARIO" -> "DATE(fecha_emision)";
            case "SEMANAL" -> "YEARWEEK(fecha_emision)";
            case "MENSUAL" -> "DATE_FORMAT(fecha_emision, '%%Y-%%m')";
            case "ANUAL" -> "YEAR(fecha_emision)";
            default -> "DATE(fecha_emision)";
        };
    }

    // Clases internas para los reportes
    public static class ServicioReporte {
        public final String nombre;
        public final String categoria;
        public final int totalVeces;
        public final BigDecimal ingresoTotal;

        public ServicioReporte(String nombre, String categoria, int totalVeces, BigDecimal ingresoTotal) {
            this.nombre = nombre;
            this.categoria = categoria;
            this.totalVeces = totalVeces;
            this.ingresoTotal = ingresoTotal;
        }
    }

    public static class VeterinarioReporte {
        public final String nombre;
        public final String especialidad;
        public final int totalConsultas;
        public final int totalProcedimientos;
        public final BigDecimal ingresoConsultas;
        public final BigDecimal ingresoProcedimientos;

        public VeterinarioReporte(String nombre, String especialidad, int totalConsultas, 
                                 int totalProcedimientos, BigDecimal ingresoConsultas, 
                                 BigDecimal ingresoProcedimientos) {
            this.nombre = nombre;
            this.especialidad = especialidad;
            this.totalConsultas = totalConsultas;
            this.totalProcedimientos = totalProcedimientos;
            this.ingresoConsultas = ingresoConsultas;
            this.ingresoProcedimientos = ingresoProcedimientos;
        }
    }

    public static class InventarioReporte {
        public final String nombreProducto;
        public final int productoTipoId;
        public final int cantidadStock;
        public final int stockMinimo;
        public final LocalDate fechaVencimiento;
        public final BigDecimal precioVenta;
        public final String estado;

        public InventarioReporte(String nombreProducto, int productoTipoId, int cantidadStock,
                                int stockMinimo, LocalDate fechaVencimiento, BigDecimal precioVenta,
                                String estado) {
            this.nombreProducto = nombreProducto;
            this.productoTipoId = productoTipoId;
            this.cantidadStock = cantidadStock;
            this.stockMinimo = stockMinimo;
            this.fechaVencimiento = fechaVencimiento;
            this.precioVenta = precioVenta;
            this.estado = estado;
        }
    }

    public static class FacturacionReporte {
        public final String periodo;
        public final int totalFacturas;
        public final BigDecimal subtotalTotal;
        public final BigDecimal impuestoTotal;
        public final BigDecimal descuentoTotal;
        public final BigDecimal totalFacturado;

        public FacturacionReporte(String periodo, int totalFacturas, BigDecimal subtotalTotal,
                                 BigDecimal impuestoTotal, BigDecimal descuentoTotal, 
                                 BigDecimal totalFacturado) {
            this.periodo = periodo;
            this.totalFacturas = totalFacturas;
            this.subtotalTotal = subtotalTotal;
            this.impuestoTotal = impuestoTotal;
            this.descuentoTotal = descuentoTotal;
            this.totalFacturado = totalFacturado;
        }
    }
}