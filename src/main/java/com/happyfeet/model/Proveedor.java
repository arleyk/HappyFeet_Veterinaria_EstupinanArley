package com.happyfeet.model;

public class Proveedor extends BaseEntity {
    private String nombreEmpresa;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private String sitioWeb;

    // Constructores
    public Proveedor() {
        super();
    }

    public Proveedor(String nombreEmpresa, String contacto, String telefono, String email) {
        super();
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    @Override
    public String toString() {
        return String.format("Proveedor [ID: %d, Empresa: %s, Contacto: %s, Tel: %s, Email: %s]",
                getId(), nombreEmpresa, contacto, telefono, email);
    }
}