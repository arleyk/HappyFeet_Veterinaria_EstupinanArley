package com.happyfeet.model;

import java.math.BigDecimal;

/**
 * Modelo que representa un item dentro de una factura
 */
public class ItemFactura {
    private Integer id;
    private Integer facturaId;
    private TipoItem tipoItem;
    private Integer productoId;
    private Integer servicioId;
    private String servicioDescripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public enum TipoItem {
        PRODUCTO, SERVICIO
    }

    // Constructor por defecto
    public ItemFactura() {}

    // Constructor con parámetros
    public ItemFactura(TipoItem tipoItem, Integer productoId, Integer servicioId, 
                      String servicioDescripcion, Integer cantidad, 
                      BigDecimal precioUnitario, BigDecimal subtotal) {
        this.tipoItem = tipoItem;
        this.productoId = productoId;
        this.servicioId = servicioId;
        this.servicioDescripcion = servicioDescripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getFacturaId() { return facturaId; }
    public void setFacturaId(Integer facturaId) { this.facturaId = facturaId; }
    
    public TipoItem getTipoItem() { return tipoItem; }
    public void setTipoItem(TipoItem tipoItem) { this.tipoItem = tipoItem; }
    
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    
    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }
    
    public String getServicioDescripcion() { return servicioDescripcion; }
    public void setServicioDescripcion(String servicioDescripcion) { this.servicioDescripcion = servicioDescripcion; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return String.format("ItemFactura{tipo=%s, cantidad=%d, subtotal=%.2f}", 
            tipoItem, cantidad, subtotal);
    }
}