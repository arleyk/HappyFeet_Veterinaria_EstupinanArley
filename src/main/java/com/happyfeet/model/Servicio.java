package com.happyfeet.model;

import java.math.BigDecimal;

/**
 * Modelo que representa un servicio ofrecido por la veterinaria
 */
public class Servicio extends BaseEntity {
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precioBase;
    private Integer duracionEstimadaMinutos;
    
    // Constructor por defecto
    public Servicio() {
        super();
    }

    // Constructor con parámetros
    public Servicio(String nombre, String descripcion, String categoria, 
                   BigDecimal precioBase, Integer duracionEstimadaMinutos) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }
    
    public Integer getDuracionEstimadaMinutos() { return duracionEstimadaMinutos; }
    public void setDuracionEstimadaMinutos(Integer duracionEstimadaMinutos) { this.duracionEstimadaMinutos = duracionEstimadaMinutos; }

    @Override
    public String toString() {
        return String.format("Servicio{id=%d, nombre='%s', precio=%.2f, categoria='%s'}", 
            id, nombre, precioBase, categoria);
    }
}