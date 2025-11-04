package com.happyfeet.model;

import java.time.LocalDateTime;

/**
 * Entidad que representa una consulta médica en el sistema
 * Extiende BaseEntity para heredar campos comunes
 */
public class ConsultaMedica extends BaseEntity {
    private Integer mascotaId;
    private Integer veterinarioId;
    private Integer citaId;
    private LocalDateTime fechaHora;
    private String motivo;
    private String sintomas;
    private String diagnostico;
    private String recomendaciones;
    private String observaciones;
    private Double pesoRegistrado;
    private Double temperatura;

    // Constructores
    public ConsultaMedica() {
        super();
    }

    public ConsultaMedica(Integer id) {
        super(id);
    }

    public ConsultaMedica(Integer mascotaId, Integer veterinarioId, LocalDateTime fechaHora, 
                         String motivo, String sintomas, String diagnostico) {
        super();
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
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

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
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

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getPesoRegistrado() {
        return pesoRegistrado;
    }

    public void setPesoRegistrado(Double pesoRegistrado) {
        this.pesoRegistrado = pesoRegistrado;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "ConsultaMedica{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", veterinarioId=" + veterinarioId +
                ", citaId=" + citaId +
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                ", sintomas='" + sintomas + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", recomendaciones='" + recomendaciones + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", pesoRegistrado=" + pesoRegistrado +
                ", temperatura=" + temperatura +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }
}