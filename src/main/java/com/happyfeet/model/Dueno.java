package com.happyfeet.model;

/**
 * Modelo que representa a un dueño de mascota en el sistema
 * Corresponde a la tabla 'duenos' en la base de datos
 */
public class Dueno extends BaseEntity {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String direccion;
    private String telefono;
    private String email;
    private String contactoEmergencia;
    
    // Constructor principal
    public Dueno(String nombreCompleto, String documentoIdentidad, String email) {
        super();
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.email = email;
    }
    
    // Constructor completo
    public Dueno(Integer id, String nombreCompleto, String documentoIdentidad, 
                String direccion, String telefono, String email, 
                String contactoEmergencia) {
        super(id);
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contactoEmergencia = contactoEmergencia;
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
    
    public String getDireccion() { 
        return direccion; 
    }
    
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
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
    
    public String getContactoEmergencia() { 
        return contactoEmergencia; 
    }
    
    public void setContactoEmergencia(String contactoEmergencia) { 
        this.contactoEmergencia = contactoEmergencia; 
    }
    
    @Override
    public String toString() {
        return String.format("Dueño[id=%d, nombre=%s, documento=%s, email=%s]", 
                           id, nombreCompleto, documentoIdentidad, email);
    }
}