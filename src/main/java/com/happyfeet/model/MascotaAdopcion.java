package com.happyfeet.model;

import java.time.LocalDate;

/**
 * Entidad que representa una mascota disponible para adopción
 */
public class MascotaAdopcion extends BaseEntity {
    private Integer mascotaId;
    private LocalDate fechaIngreso;
    private String motivoIngreso;
    private String estado; // Disponible, En Proceso, Adoptada, Retirada
    private String historia;
    private String temperamento;
    private String necesidadesEspeciales;
    private String fotoAdicionalUrl;
    
    // Constructores
    public MascotaAdopcion() {
        super();
    }
    
    public MascotaAdopcion(Integer mascotaId, LocalDate fechaIngreso, String motivoIngreso, 
                          String estado, String historia) {
        this();
        this.mascotaId = mascotaId;
        this.fechaIngreso = fechaIngreso;
        this.motivoIngreso = motivoIngreso;
        this.estado = estado;
        this.historia = historia;
    }
    
    // Getters y Setters
    public Integer getMascotaId() { return mascotaId; }
    public void setMascotaId(Integer mascotaId) { this.mascotaId = mascotaId; }
    
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public String getMotivoIngreso() { return motivoIngreso; }
    public void setMotivoIngreso(String motivoIngreso) { this.motivoIngreso = motivoIngreso; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getHistoria() { return historia; }
    public void setHistoria(String historia) { this.historia = historia; }
    
    public String getTemperamento() { return temperamento; }
    public void setTemperamento(String temperamento) { this.temperamento = temperamento; }
    
    public String getNecesidadesEspeciales() { return necesidadesEspeciales; }
    public void setNecesidadesEspeciales(String necesidadesEspeciales) { this.necesidadesEspeciales = necesidadesEspeciales; }
    
    public String getFotoAdicionalUrl() { return fotoAdicionalUrl; }
    public void setFotoAdicionalUrl(String fotoAdicionalUrl) { this.fotoAdicionalUrl = fotoAdicionalUrl; }
    
    @Override
    public String toString() {
        return "MascotaAdopcion{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", fechaIngreso=" + fechaIngreso +
                ", estado='" + estado + '\'' +
                ", motivoIngreso='" + motivoIngreso + '\'' +
                ", temperamento='" + temperamento + '\'' +
                ", activo=" + activo +
                '}';
    }
}