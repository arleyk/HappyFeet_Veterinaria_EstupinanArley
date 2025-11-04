package com.happyfeet.view;

import com.happyfeet.controller.FacturacionController;
import com.happyfeet.exception.VeterinariaException;

import java.util.Scanner;

/**
 * Menú para la gestión de facturación
 */
public class FacturacionMenu extends BaseMenu {
    private final FacturacionController facturacionController;
    
    public FacturacionMenu(Scanner scanner) throws VeterinariaException {
        super(scanner);
        this.facturacionController = new FacturacionController();
    }
    
    @Override
    public void mostrarMenu() {
        while (menuActivo) {
            System.out.println("\n=== MÓDULO DE FACTURACIÓN ===");
            System.out.println("1. Generar nueva factura");
            System.out.println("2. Consultar factura por ID");
            System.out.println("3. Generar factura en texto plano");
            System.out.println("4. Listar servicios disponibles");
            System.out.println("5. Consultar facturas por dueño");
            System.out.println("6. Actualizar estado de factura");
            System.out.println("7. Crear nuevo servicio");
            System.out.println("8. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    @Override
    protected void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> generarNuevaFactura();
            case 2 -> consultarFacturaPorId();
            case 3 -> generarFacturaTextoPlano();
            case 4 -> listarServiciosDisponibles();
            case 5 -> consultarFacturasPorDueno();
            case 6 -> actualizarEstadoFactura();
            case 7 -> crearNuevoServicio();
            case 8 -> cerrarMenu();
            default -> System.out.println("❌ Opción no válida.");
        }
    }
    
    /**
     * Genera una nueva factura
     */
    private void generarNuevaFactura() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al generar factura: " + e.getMessage());
        }
    }
    
    /**
     * Consulta una factura por ID
     */
    private void consultarFacturaPorId() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al consultar factura: " + e.getMessage());
        }
    }
    
    /**
     * Genera la factura en formato texto plano
     */
    private void generarFacturaTextoPlano() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al generar factura en texto plano: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos los servicios disponibles
     */
    private void listarServiciosDisponibles() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al listar servicios: " + e.getMessage());
        }
    }
    
    /**
     * Consulta facturas por dueño
     */
    private void consultarFacturasPorDueno() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al consultar facturas: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza el estado de una factura
     */
    private void actualizarEstadoFactura() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estado: " + e.getMessage());
        }
    }
    
    /**
     * Crea un nuevo servicio
     */
    private void crearNuevoServicio() {
        try {
            facturacionController.mostrarMenuFacturacion();
        } catch (Exception e) {
            System.err.println("❌ Error al crear servicio: " + e.getMessage());
        }
    }
}