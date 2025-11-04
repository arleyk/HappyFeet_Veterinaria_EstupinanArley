package com.happyfeet.model;

import java.time.LocalDate;

/**
 * Entidad que representa el historial médico especial de las mascotas
 * Extiende BaseEntity para heredar campos comunes
 */
public class HistorialMedicoEspecial extends BaseEntity {
    private Integer mascotaId;
    private LocalDate fechaEvento;
    private Integer eventoTipoId;
    private String descripcion;
    private String diagnostico;
    private String tratamientoRecomendado;
    private Integer veterinarioId;
    private Integer consultaId;
    private Integer procedimientoId;

    // Constructores
    public HistorialMedicoEspecial() {
        super();
    }

    public HistorialMedicoEspecial(Integer id) {
        super(id);
    }

    public HistorialMedicoEspecial(Integer mascotaId, LocalDate fechaEvento, Integer eventoTipoId, 
                                 String descripcion, String diagnostico, String tratamientoRecomendado) {
        super();
        this.mascotaId = mascotaId;
        this.fechaEvento = fechaEvento;
        this.eventoTipoId = eventoTipoId;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamientoRecomendado = tratamientoRecomendado;
    }

    // Getters y Setters
    public Integer getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Integer getEventoTipoId() {
        return eventoTipoId;
    }

    public void setEventoTipoId(Integer eventoTipoId) {
        this.eventoTipoId = eventoTipoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamientoRecomendado() {
        return tratamientoRecomendado;
    }

    public void setTratamientoRecomendado(String tratamientoRecomendado) {
        this.tratamientoRecomendado = tratamientoRecomendado;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public Integer getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Integer consultaId) {
        this.consultaId = consultaId;
    }

    public Integer getProcedimientoId() {
        return procedimientoId;
    }

    public void setProcedimientoId(Integer procedimientoId) {
        this.procedimientoId = procedimientoId;
    }

    @Override
    public String toString() {
        return "HistorialMedicoEspecial{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", fechaEvento=" + fechaEvento +
                ", eventoTipoId=" + eventoTipoId +
                ", descripcion='" + descripcion + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamientoRecomendado='" + tratamientoRecomendado + '\'' +
                ", veterinarioId=" + veterinarioId +
                ", consultaId=" + consultaId +
                ", procedimientoId=" + procedimientoId +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }
}