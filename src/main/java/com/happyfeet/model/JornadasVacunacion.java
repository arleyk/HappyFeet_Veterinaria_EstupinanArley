package com.happyfeet.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una jornada de vacunación
 */
public class JornadasVacunacion extends BaseEntity {
    private String nombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String ubicacion;
    private String descripcion;
    private Integer capacidadMaxima;
    private String estado; // Planificada, En Curso, Finalizada, Cancelada
    
    // Constructores
    public JornadasVacunacion() {
        super();
    }
    
    public JornadasVacunacion(String nombre, LocalDate fecha, String ubicacion, String estado) {
        this();
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return "JornadasVacunacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", ubicacion='" + ubicacion + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", estado='" + estado + '\'' +
                ", activo=" + activo +
                '}';
    }
}