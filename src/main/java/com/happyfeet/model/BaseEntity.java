package com.happyfeet.model;

import java.time.LocalDateTime;

/**
 * Clase base abstracta para todas las entidades del sistema
 * Aplica el principio DRY (Don't Repeat Yourself) para campos comunes
 */
public abstract class BaseEntity {
    protected Integer id;
    protected LocalDateTime fechaRegistro;
    protected Boolean activo;
    
    // Constructores
    public BaseEntity() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }
    
    public BaseEntity(Integer id) {
        this();
        this.id = id;
    }
    
    // Getters y Setters
    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }
    
    public LocalDateTime getFechaRegistro() { 
        return fechaRegistro; 
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
    
    public Boolean getActivo() { 
        return activo; 
    }
    
    public void setActivo(Boolean activo) { 
        this.activo = activo; 
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity that = (BaseEntity) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }
}