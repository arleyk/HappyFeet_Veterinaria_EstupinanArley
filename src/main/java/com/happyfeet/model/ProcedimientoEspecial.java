package com.happyfeet.model;

import java.time.LocalDateTime;

/**
 * Entidad que representa un procedimiento especial en el sistema
 * Extiende BaseEntity para heredar campos comunes
 */
public class ProcedimientoEspecial extends BaseEntity {
    private Integer mascotaId;
    private Integer veterinarioId;
    private String tipoProcedimiento;
    private String nombreProcedimiento;
    private LocalDateTime fechaHora;
    private Integer duracionEstimadaMinutos;
    private String informacionPreoperatoria;
    private String detalleProcedimiento;
    private String complicaciones;
    private String seguimientoPostoperatorio;
    private LocalDateTime proximoControl;
    private String estado;
    private Double costoProcedimiento;

    // Constructores
    public ProcedimientoEspecial() {
        super();
    }

    public ProcedimientoEspecial(Integer id) {
        super(id);
    }

    public ProcedimientoEspecial(Integer mascotaId, Integer veterinarioId, String tipoProcedimiento,
                                String nombreProcedimiento, LocalDateTime fechaHora, String detalleProcedimiento) {
        super();
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.tipoProcedimiento = tipoProcedimiento;
        this.nombreProcedimiento = nombreProcedimiento;
        this.fechaHora = fechaHora;
        this.detalleProcedimiento = detalleProcedimiento;
        this.estado = "Programado";
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

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getDuracionEstimadaMinutos() {
        return duracionEstimadaMinutos;
    }

    public void setDuracionEstimadaMinutos(Integer duracionEstimadaMinutos) {
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
    }

    public String getInformacionPreoperatoria() {
        return informacionPreoperatoria;
    }

    public void setInformacionPreoperatoria(String informacionPreoperatoria) {
        this.informacionPreoperatoria = informacionPreoperatoria;
    }

    public String getDetalleProcedimiento() {
        return detalleProcedimiento;
    }

    public void setDetalleProcedimiento(String detalleProcedimiento) {
        this.detalleProcedimiento = detalleProcedimiento;
    }

    public String getComplicaciones() {
        return complicaciones;
    }

    public void setComplicaciones(String complicaciones) {
        this.complicaciones = complicaciones;
    }

    public String getSeguimientoPostoperatorio() {
        return seguimientoPostoperatorio;
    }

    public void setSeguimientoPostoperatorio(String seguimientoPostoperatorio) {
        this.seguimientoPostoperatorio = seguimientoPostoperatorio;
    }

    public LocalDateTime getProximoControl() {
        return proximoControl;
    }

    public void setProximoControl(LocalDateTime proximoControl) {
        this.proximoControl = proximoControl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCostoProcedimiento() {
        return costoProcedimiento;
    }

    public void setCostoProcedimiento(Double costoProcedimiento) {
        this.costoProcedimiento = costoProcedimiento;
    }

    @Override
    public String toString() {
        return "ProcedimientoEspecial{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", veterinarioId=" + veterinarioId +
                ", tipoProcedimiento='" + tipoProcedimiento + '\'' +
                ", nombreProcedimiento='" + nombreProcedimiento + '\'' +
                ", fechaHora=" + fechaHora +
                ", duracionEstimadaMinutos=" + duracionEstimadaMinutos +
                ", informacionPreoperatoria='" + informacionPreoperatoria + '\'' +
                ", detalleProcedimiento='" + detalleProcedimiento + '\'' +
                ", complicaciones='" + complicaciones + '\'' +
                ", seguimientoPostoperatorio='" + seguimientoPostoperatorio + '\'' +
                ", proximoControl=" + proximoControl +
                ", estado='" + estado + '\'' +
                ", costoProcedimiento=" + costoProcedimiento +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }
}