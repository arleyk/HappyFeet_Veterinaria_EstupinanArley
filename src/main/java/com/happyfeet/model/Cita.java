package com.happyfeet.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa una cita en el sistema
 * Corresponde a la tabla 'citas' en la base de datos
 */
public class Cita extends BaseEntity {
    private Integer mascotaId;
    private Integer veterinarioId;
    private LocalDateTime fechaHora;
    private String motivo;
    private Integer estadoId;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    
    // Constructores
    public Cita() {
        super();
    }
    
    public Cita(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora, 
                String motivo, Integer estadoId) {
        super();
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estadoId = estadoId;
    }
    
    public Cita(Integer id, Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora,
                String motivo, Integer estadoId, String observaciones, LocalDateTime fechaCreacion) {
        super(id);
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estadoId = estadoId;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public Integer getMascotaId() { 
        return mascotaId; 
    }
    
    public void setMascotaId(Integer mascotaId) { 
        this.mascotaId = mascotaId; 
    }
    
    public Integer getVeterinarioId() { 
        return veterinarioId; 
    }
    
    public void setVeterinarioId(Integer veterinarioId) { 
        this.veterinarioId = veterinarioId; 
    }
    
    public LocalDateTime getFechaHora() { 
        return fechaHora; 
    }
    
    public void setFechaHora(LocalDateTime fechaHora) { 
        this.fechaHora = fechaHora; 
    }
    
    public String getMotivo() { 
        return motivo; 
    }
    
    public void setMotivo(String motivo) { 
        this.motivo = motivo; 
    }
    
    public Integer getEstadoId() { 
        return estadoId; 
    }
    
    public void setEstadoId(Integer estadoId) { 
        this.estadoId = estadoId; 
    }
    
    public String getObservaciones() { 
        return observaciones; 
    }
    
    public void setObservaciones(String observaciones) { 
        this.observaciones = observaciones; 
    }
    
    public LocalDateTime getFechaCreacion() { 
        return fechaCreacion; 
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) { 
        this.fechaCreacion = fechaCreacion; 
    }
    
    @Override
    public String toString() {
        return String.format("Cita[id=%d, mascotaId=%d, veterinarioId=%d, fechaHora=%s, estadoId=%d]", 
                           id, mascotaId, veterinarioId, fechaHora, estadoId);
    }
}