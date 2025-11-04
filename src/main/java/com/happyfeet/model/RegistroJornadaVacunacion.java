package com.happyfeet.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un registro de vacunación en jornada
 */
public class RegistroJornadaVacunacion extends BaseEntity {
    private Integer jornadaId;
    private Integer mascotaId;
    private Integer duenoId;
    private Integer vacunaId;
    private Integer veterinarioId;
    private LocalDateTime fechaHora;
    private String loteVacuna;
    private LocalDate proximaDosis;
    private String observaciones;
    
    // Constructores
    public RegistroJornadaVacunacion() {
        super();
    }
    
    public RegistroJornadaVacunacion(Integer jornadaId, Integer mascotaId, Integer duenoId, 
                                   Integer vacunaId, LocalDateTime fechaHora) {
        this();
        this.jornadaId = jornadaId;
        this.mascotaId = mascotaId;
        this.duenoId = duenoId;
        this.vacunaId = vacunaId;
        this.fechaHora = fechaHora;
    }
    
    // Getters y Setters
    public Integer getJornadaId() { return jornadaId; }
    public void setJornadaId(Integer jornadaId) { this.jornadaId = jornadaId; }
    
    public Integer getMascotaId() { return mascotaId; }
    public void setMascotaId(Integer mascotaId) { this.mascotaId = mascotaId; }
    
    public Integer getDuenoId() { return duenoId; }
    public void setDuenoId(Integer duenoId) { this.duenoId = duenoId; }
    
    public Integer getVacunaId() { return vacunaId; }
    public void setVacunaId(Integer vacunaId) { this.vacunaId = vacunaId; }
    
    public Integer getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(Integer veterinarioId) { this.veterinarioId = veterinarioId; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    
    public String getLoteVacuna() { return loteVacuna; }
    public void setLoteVacuna(String loteVacuna) { this.loteVacuna = loteVacuna; }
    
    public LocalDate getProximaDosis() { return proximaDosis; }
    public void setProximaDosis(LocalDate proximaDosis) { this.proximaDosis = proximaDosis; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    @Override
    public String toString() {
        return "RegistroJornadaVacunacion{" +
                "id=" + id +
                ", jornadaId=" + jornadaId +
                ", mascotaId=" + mascotaId +
                ", duenoId=" + duenoId +
                ", vacunaId=" + vacunaId +
                ", veterinarioId=" + veterinarioId +
                ", fechaHora=" + fechaHora +
                ", loteVacuna='" + loteVacuna + '\'' +
                ", proximaDosis=" + proximaDosis +
                ", activo=" + activo +
                '}';
    }
}               