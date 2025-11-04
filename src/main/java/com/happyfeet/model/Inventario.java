package com.happyfeet.model;

import java.time.LocalDate;

public class Inventario extends BaseEntity {
    private String nombreProducto;
    private Integer productoTipoId;
    private String descripcion;
    private String fabricante;
    private Integer proveedorId;
    private String lote;
    private Integer cantidadStock;
    private Integer stockMinimo;
    private String unidadMedida;
    private LocalDate fechaVencimiento;
    private Double precioCompra;
    private Double precioVenta;
    private Boolean requiereReceta;

    // Constructores
    public Inventario() {
        super();
    }

    public Inventario(String nombreProducto, Integer productoTipoId, Integer cantidadStock, 
                     Integer stockMinimo, Double precioVenta) {
        super();
        this.nombreProducto = nombreProducto;
        this.productoTipoId = productoTipoId;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Integer getProductoTipoId() { return productoTipoId; }
    public void setProductoTipoId(Integer productoTipoId) { this.productoTipoId = productoTipoId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public Integer getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(Integer cantidadStock) { this.cantidadStock = cantidadStock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }

    public Double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(Double precioVenta) { this.precioVenta = precioVenta; }

    public Boolean getRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(Boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    @Override
    public String toString() {
        return String.format("Inventario [ID: %d, Producto: %s, Stock: %d, Mínimo: %d, Precio: $%.2f, Vencimiento: %s]",
                getId(), nombreProducto, cantidadStock, stockMinimo, precioVenta, 
                fechaVencimiento != null ? fechaVencimiento.toString() : "N/A");
    }
}