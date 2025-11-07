package com.happyfeet.controller;

import com.happyfeet.model.Factura;
import com.happyfeet.model.ItemFactura;
import com.happyfeet.model.Servicio;
import com.happyfeet.service.FacturacionService;
import com.happyfeet.exception.VeterinariaException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controlador para la gestión de facturación
 * Coordina entre la vista y el servicio, manejando el flujo de la aplicación
 */
public class FacturacionController {
    private final FacturacionService facturacionService;
    private final Scanner scanner;
    
    public FacturacionController() throws VeterinariaException {
        this.facturacionService = new FacturacionService();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Muestra el menú de facturación
     */
    public void mostrarMenuFacturacion() {
        while (true) {
            System.out.println("\n=== MÓDULO DE FACTURACIÓN ===");
            System.out.println("1. Generar nueva factura");
            System.out.println("2. Consultar factura por ID");
            System.out.println("3. Generar factura en texto plano");
            System.out.println("4. Listar servicios disponibles");
            System.out.println("5. Consultar facturas por dueño");
            System.out.println("6. Consultar facturas por documento");
            System.out.println("7. Actualizar estado de factura");
            System.out.println("8. Crear nuevo servicio");
            System.out.println("9. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> generarNuevaFactura();
                case 2 -> consultarFacturaPorId();
                case 3 -> generarFacturaTextoPlano();
                case 4 -> listarServiciosDisponibles();
                case 5 -> consultarFacturasPorDueno();
                case 6 -> consultarFacturasPorDocumento();
                case 7 -> actualizarEstadoFactura();
                case 8 -> crearNuevoServicio();
                case 9-> { return; }
                default -> System.out.println("❌ Opción no válida.");
            }
        }
    }
    
    // filtra facturas por documento
    private void consultarFacturasPorDocumento(){
                try {
            System.out.print("Ingrese el documento del dueño: ");
            String duenoDocumento = scanner.nextLine();
            scanner.nextLine();
            
            List<Factura> facturas = facturacionService.obtenerFacturasPorDocumento(duenoDocumento);
            
            if (facturas.isEmpty()) {
                System.out.println("No se encontraron facturas para este dueño.");
            } else {
                System.out.println("\n=== FACTURAS DEL DUEÑO ===");
                facturas.forEach(factura -> {
                    System.out.printf("ID: %d | Factura: %s | Fecha: %s | Total: $%,.2f | Estado: %s\n",
                        factura.getId(), factura.getNumeroFactura(), 
                        factura.getFechaEmision().toLocalDate(), 
                        factura.getTotal(), factura.getEstado());
                });
                System.out.println("Total: " + facturas.size() + " facturas encontradas");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al consultar facturas: " + e.getMessage());
        }
    }
    
    
    
    /**
     * Genera una nueva factura
     */
    private void generarNuevaFactura() {
        try {
            System.out.println("\n--- Generar Nueva Factura ---");
            
            System.out.print("ID del dueño: ");
            Integer duenoId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Método de pago (EFECTIVO/TARJETA/TRANSFERENCIA/MIXTO): ");
            String metodoPagoStr = scanner.nextLine();
            Factura.MetodoPago metodoPago = Factura.MetodoPago.valueOf(metodoPagoStr.toUpperCase());
            
            System.out.print("Observaciones: ");
            String observaciones = scanner.nextLine();
            
            // Por simplicidad, creamos una factura de ejemplo con un servicio
            List<ItemFactura> items = List.of(
                new ItemFactura(ItemFactura.TipoItem.SERVICIO, null, 1, 
                               "Consulta general", 1, new BigDecimal("50000"), 
                               new BigDecimal("50000"))
            );
            
            Factura factura = facturacionService.crearFacturaCompleta(duenoId, items, metodoPago, observaciones);
            
            System.out.println("✅ Factura creada exitosamente:");
            mostrarFactura(factura);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar factura: " + e.getMessage());
        }
    }
    
    /**
     * Consulta una factura por ID
     */
    private void consultarFacturaPorId() {
        try {
            System.out.print("Ingrese el ID de la factura: ");
            Integer facturaId = scanner.nextInt();
            scanner.nextLine();
            
            Optional<Factura> factura = facturacionService.obtenerFacturaCompleta(facturaId);
            
            if (factura.isPresent()) {
                mostrarFactura(factura.get());
            } else {
                System.out.println("❌ No se encontró ninguna factura con ID: " + facturaId);
            }
        } catch (Exception e) {
            System.err.println("❌ Error al consultar factura: " + e.getMessage());
        }
    }
    
    /**
     * Genera la factura en formato texto plano
     */
    private void generarFacturaTextoPlano() {
        try {
            System.out.print("Ingrese el ID de la factura: ");
            Integer facturaId = scanner.nextInt();
            scanner.nextLine();
            
            String facturaTexto = facturacionService.generarFacturaTextoPlano(facturaId);
            
            System.out.println("\n--- FACTURA EN TEXTO PLANO ---");
            System.out.println(facturaTexto);
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar factura en texto plano: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos los servicios disponibles
     */
    private void listarServiciosDisponibles() {
        try {
            List<Servicio> servicios = facturacionService.obtenerTodosLosServicios();
            
            if (servicios.isEmpty()) {
                System.out.println("No hay servicios registrados.");
            } else {
                System.out.println("\n=== SERVICIOS DISPONIBLES ===");
                servicios.forEach(servicio -> {
                    System.out.printf("ID: %d | %s | $%,.2f | %s | %d min\n",
                        servicio.getId(), servicio.getNombre(), servicio.getPrecioBase(),
                        servicio.getCategoria(), servicio.getDuracionEstimadaMinutos());
                    
                    if (servicio.getDescripcion() != null && !servicio.getDescripcion().isEmpty()) {
                        System.out.printf("     Descripción: %s\n", servicio.getDescripcion());
                    }
                });
                System.out.println("Total: " + servicios.size() + " servicios registrados");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al listar servicios: " + e.getMessage());
        }
    }
    
    /**
     * Consulta facturas por dueño
     */
    private void consultarFacturasPorDueno() {
        try {
            System.out.print("Ingrese el ID del dueño: ");
            Integer duenoId = scanner.nextInt();
            scanner.nextLine();
            
            List<Factura> facturas = facturacionService.obtenerFacturasPorDueno(duenoId);
            
            if (facturas.isEmpty()) {
                System.out.println("No se encontraron facturas para este dueño.");
            } else {
                System.out.println("\n=== FACTURAS DEL DUEÑO ===");
                facturas.forEach(factura -> {
                    System.out.printf("ID: %d | Factura: %s | Fecha: %s | Total: $%,.2f | Estado: %s\n",
                        factura.getId(), factura.getNumeroFactura(), 
                        factura.getFechaEmision().toLocalDate(), 
                        factura.getTotal(), factura.getEstado());
                });
                System.out.println("Total: " + facturas.size() + " facturas encontradas");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al consultar facturas: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza el estado de una factura
     */
    private void actualizarEstadoFactura() {
        try {
            System.out.print("Ingrese el ID de la factura: ");
            Integer facturaId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Nuevo estado (PENDIENTE/PAGADA/ANULADA): ");
            String estadoStr = scanner.nextLine();
            Factura.EstadoFactura estado = Factura.EstadoFactura.valueOf(estadoStr.toUpperCase());
            
            facturacionService.actualizarEstadoFactura(facturaId, estado);
            
            System.out.println("✅ Estado de factura actualizado exitosamente.");
            
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estado: " + e.getMessage());
        }
    }
    
    /**
     * Crea un nuevo servicio
     */
    private void crearNuevoServicio() {
        try {
            System.out.println("\n--- Crear Nuevo Servicio ---");
            
            System.out.print("Nombre del servicio: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();
            
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine();
            
            System.out.print("Precio base: ");
            BigDecimal precioBase = scanner.nextBigDecimal();
            scanner.nextLine();
            
            System.out.print("Duración estimada (minutos): ");
            Integer duracion = scanner.nextInt();
            scanner.nextLine();
            
            Servicio servicio = facturacionService.crearServicio(nombre, descripcion, categoria, precioBase, duracion);
            
            System.out.println("✅ Servicio creado exitosamente: " + servicio);
            
        } catch (Exception e) {
            System.err.println("❌ Error al crear servicio: " + e.getMessage());
        }
    }
    
    /**
     * Muestra los detalles de una factura
     */
    private void mostrarFactura(Factura factura) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           CLÍNICA VETERINARIA HAPPY FEET");
        System.out.println("=".repeat(50));
        System.out.printf("Factura No: %s\n", factura.getNumeroFactura());
        System.out.printf("Fecha: %s\n", factura.getFechaEmision().toLocalDate());
        System.out.printf("Estado: %s\n", factura.getEstado());
        System.out.printf("Método de Pago: %s\n", factura.getMetodoPago());
        System.out.println("-".repeat(50));
        
        if (factura.getItems() != null && !factura.getItems().isEmpty()) {
            System.out.println("ITEMS:");
            factura.getItems().forEach(item -> {
                String descripcion = item.getTipoItem() == ItemFactura.TipoItem.SERVICIO 
                    ? (item.getServicioDescripcion() != null ? item.getServicioDescripcion() : "Servicio")
                    : "Producto";
                
                System.out.printf("  %s x%d - $%,.2f c/u = $%,.2f\n", 
                    descripcion, item.getCantidad(), item.getPrecioUnitario(), item.getSubtotal());
            });
            System.out.println("-".repeat(50));
        }
        
        System.out.printf("Subtotal: $%,.2f\n", factura.getSubtotal());
        System.out.printf("Impuesto (19%%): $%,.2f\n", factura.getImpuesto());
        System.out.printf("Descuento: $%,.2f\n", factura.getDescuento());
        System.out.printf("TOTAL: $%,.2f\n", factura.getTotal());
        System.out.println("=".repeat(50));
        
        if (factura.getObservaciones() != null && !factura.getObservaciones().isEmpty()) {
            System.out.printf("Observaciones: %s\n", factura.getObservaciones());
        }
    }
    
    
}