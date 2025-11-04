package com.happyfeet.model;

import java.time.LocalDate;

/**
 * Modelo que representa una mascota en el sistema
 * Corresponde a la tabla 'mascotas' en la base de datos
 */
public class Mascota extends BaseEntity {
    private Integer duenoId;
    private String nombre;
    private Integer razaId;
    private LocalDate fechaNacimiento;
    private String sexo;
    private Double pesoActual;
    private String microchip;
    private String tatuaje;
    private String urlFoto;
    private String alergias;
    private String condicionesPreexistentes;
    
    // Constructor principal
    public Mascota(Integer duenoId, String nombre, Integer razaId, String sexo) {
        super();
        this.duenoId = duenoId;
        this.nombre = nombre;
        this.razaId = razaId;
        this.sexo = sexo;
    }
    
    // Constructor completo
    public Mascota(Integer id, Integer duenoId, String nombre, Integer razaId, 
                  LocalDate fechaNacimiento, String sexo, Double pesoActual, 
                  String microchip, String alergias, String condicionesPreexistentes) {
        super(id);
        this.duenoId = duenoId;
        this.nombre = nombre;
        this.razaId = razaId;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.pesoActual = pesoActual;
        this.microchip = microchip;
        this.alergias = alergias;
        this.condicionesPreexistentes = condicionesPreexistentes;
    }
    
    // Getters y Setters
    public Integer getDuenoId() { 
        return duenoId; 
    }
    
    public void setDuenoId(Integer duenoId) { 
        this.duenoId = duenoId; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public Integer getRazaId() { 
        return razaId; 
    }
    
    public void setRazaId(Integer razaId) { 
        this.razaId = razaId; 
    }
    
    public LocalDate getFechaNacimiento() { 
        return fechaNacimiento; 
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
    
    public String getSexo() { 
        return sexo; 
    }
    
    public void setSexo(String sexo) { 
        this.sexo = sexo; 
    }
    
    public Double getPesoActual() { 
        return pesoActual; 
    }
    
    public void setPesoActual(Double pesoActual) { 
        this.pesoActual = pesoActual; 
    }
    
    public String getMicrochip() { 
        return microchip; 
    }
    
    public void setMicrochip(String microchip) { 
        this.microchip = microchip; 
    }
    
    public String getTatuaje() { 
        return tatuaje; 
    }
    
    public void setTatuaje(String tatuaje) { 
        this.tatuaje = tatuaje; 
    }
    
    public String getUrlFoto() { 
        return urlFoto; 
    }
    
    public void setUrlFoto(String urlFoto) { 
        this.urlFoto = urlFoto; 
    }
    
    public String getAlergias() { 
        return alergias; 
    }
    
    public void setAlergias(String alergias) { 
        this.alergias = alergias; 
    }
    
    public String getCondicionesPreexistentes() { 
        return condicionesPreexistentes; 
    }
    
    public void setCondicionesPreexistentes(String condicionesPreexistentes) { 
        this.condicionesPreexistentes = condicionesPreexistentes; 
    }
    
    @Override
    public String toString() {
        return String.format("Mascota[id=%d, nombre=%s, dueñoId=%d, sexo=%s]", 
                           id, nombre, duenoId, sexo);
    }
}