package com.happyfeet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Modelo que representa una factura en el sistema
 * Extiende BaseEntity para heredar campos comunes
 */
public class Factura extends BaseEntity {
    private Integer duenoId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoFactura estado;
    private String observaciones;
    private List<ItemFactura> items;

    public enum MetodoPago {
        EFECTIVO, TARJETA, TRANSFERENCIA, MIXTO
    }
    
    public enum EstadoFactura {
        PENDIENTE, PAGADA, ANULADA
    }

    // Constructores
    public Factura() {
        super();
        this.fechaEmision = LocalDateTime.now();
        this.estado = EstadoFactura.PENDIENTE;
        this.subtotal = BigDecimal.ZERO;
        this.impuesto = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public Factura(Integer duenoId, String numeroFactura, LocalDateTime fechaEmision, 
                  BigDecimal subtotal, BigDecimal impuesto, BigDecimal descuento, 
                  BigDecimal total, MetodoPago metodoPago, EstadoFactura estado, 
                  String observaciones) {
        this();
        this.duenoId = duenoId;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = total;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public Integer getDuenoId() { return duenoId; }
    public void setDuenoId(Integer duenoId) { this.duenoId = duenoId; }
    
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(BigDecimal impuesto) { this.impuesto = impuesto; }
    
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    
    public EstadoFactura getEstado() { return estado; }
    public void setEstado(EstadoFactura estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public List<ItemFactura> getItems() { return items; }
    public void setItems(List<ItemFactura> items) { this.items = items; }

    @Override
    public String toString() {
        return String.format("Factura{id=%d, numero='%s', total=%.2f, estado=%s}", 
            id, numeroFactura, total, estado);
    }
}