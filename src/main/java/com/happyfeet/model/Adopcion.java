package com.happyfeet.model;

import java.time.LocalDate;

/**
 * Entidad que representa una adopción realizada
 */
public class Adopcion extends BaseEntity {
    private Integer mascotaAdopcionId;
    private Integer duenoId;
    private LocalDate fechaAdopcion;
    private String contratoTexto;
    private String condicionesEspeciales;
    private Boolean seguimientoRequerido;
    private LocalDate fechaPrimerSeguimiento;
    
    // Constructores
    public Adopcion() {
        super();
    }
    
    public Adopcion(Integer mascotaAdopcionId, Integer duenoId, LocalDate fechaAdopcion) {
        this();
        this.mascotaAdopcionId = mascotaAdopcionId;
        this.duenoId = duenoId;
        this.fechaAdopcion = fechaAdopcion;
        this.seguimientoRequerido = true;
    }
    
    // Getters y Setters
    public Integer getMascotaAdopcionId() { return mascotaAdopcionId; }
    public void setMascotaAdopcionId(Integer mascotaAdopcionId) { this.mascotaAdopcionId = mascotaAdopcionId; }
    
    public Integer getDuenoId() { return duenoId; }
    public void setDuenoId(Integer duenoId) { this.duenoId = duenoId; }
    
    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(LocalDate fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }
    
    public String getContratoTexto() { return contratoTexto; }
    public void setContratoTexto(String contratoTexto) { this.contratoTexto = contratoTexto; }
    
    public String getCondicionesEspeciales() { return condicionesEspeciales; }
    public void setCondicionesEspeciales(String condicionesEspeciales) { this.condicionesEspeciales = condicionesEspeciales; }
    
    public Boolean getSeguimientoRequerido() { return seguimientoRequerido; }
    public void setSeguimientoRequerido(Boolean seguimientoRequerido) { this.seguimientoRequerido = seguimientoRequerido; }
    
    public LocalDate getFechaPrimerSeguimiento() { return fechaPrimerSeguimiento; }
    public void setFechaPrimerSeguimiento(LocalDate fechaPrimerSeguimiento) { this.fechaPrimerSeguimiento = fechaPrimerSeguimiento; }
    
    @Override
    public String toString() {
        return "Adopcion{" +
                "id=" + id +
                ", mascotaAdopcionId=" + mascotaAdopcionId +
                ", duenoId=" + duenoId +
                ", fechaAdopcion=" + fechaAdopcion +
                ", seguimientoRequerido=" + seguimientoRequerido +
                ", activo=" + activo +
                '}';
    }
}