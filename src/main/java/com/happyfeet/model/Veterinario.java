package com.happyfeet.model;

import java.time.LocalDate;

/**
 * Modelo que representa un veterinario en el sistema
 * Corresponde a la tabla 'veterinarios' en la base de datos
 */
public class Veterinario extends BaseEntity {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String licenciaProfesional;
    private String especialidad;
    private String telefono;
    private String email;
    private LocalDate fechaContratacion;
    
    // Constructores
    public Veterinario() {
        super();
    }
    
    public Veterinario(String nombreCompleto, String documentoIdentidad, String licenciaProfesional) {
        super();
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.licenciaProfesional = licenciaProfesional;
    }
    
    public Veterinario(Integer id, String nombreCompleto, String documentoIdentidad, 
                      String licenciaProfesional, String especialidad, String telefono, 
                      String email, LocalDate fechaContratacion) {
        super(id);
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.licenciaProfesional = licenciaProfesional;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.fechaContratacion = fechaContratacion;
    }
    
    // Getters y Setters
    public String getNombreCompleto() { 
        return nombreCompleto; 
    }
    
    public void setNombreCompleto(String nombreCompleto) { 
        this.nombreCompleto = nombreCompleto; 
    }
    
    public String getDocumentoIdentidad() { 
        return documentoIdentidad; 
    }
    
    public void setDocumentoIdentidad(String documentoIdentidad) { 
        this.documentoIdentidad = documentoIdentidad; 
    }
    
    public String getLicenciaProfesional() { 
        return licenciaProfesional; 
    }
    
    public void setLicenciaProfesional(String licenciaProfesional) { 
        this.licenciaProfesional = licenciaProfesional; 
    }
    
    public String getEspecialidad() { 
        return especialidad; 
    }
    
    public void setEspecialidad(String especialidad) { 
        this.especialidad = especialidad; 
    }
    
    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public LocalDate getFechaContratacion() { 
        return fechaContratacion; 
    }
    
    public void setFechaContratacion(LocalDate fechaContratacion) { 
        this.fechaContratacion = fechaContratacion; 
    }
    
    @Override
    public String toString() {
        return String.format("Veterinario[id=%d, nombre=%s, licencia=%s, especialidad=%s]", 
                           id, nombreCompleto, licenciaProfesional, especialidad);
    }
}