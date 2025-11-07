package com.happyfeet.service;

import com.happyfeet.model.Factura;
import com.happyfeet.model.ItemFactura;
import com.happyfeet.model.Servicio;
import com.happyfeet.dao.FacturaDAO;
import com.happyfeet.dao.ServicioDAO;
import com.happyfeet.exception.VeterinariaException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de facturación
 * Contiene la lógica de negocio relacionada con facturas y servicios
 */
public class FacturacionService {
    private final FacturaDAO facturaDAO;
    private final ServicioDAO servicioDAO;

    public FacturacionService() throws VeterinariaException {
        this.facturaDAO = new FacturaDAO();
        this.servicioDAO = new ServicioDAO();
    }

    /**
     * Crea una factura completa con sus items
     */
    public Factura crearFacturaCompleta(Integer duenoId, List<ItemFactura> items, 
                                       Factura.MetodoPago metodoPago, String observaciones) throws VeterinariaException {
        
        // Validaciones de negocio
        validarDatosFactura(duenoId, items);
        
        // Calcular totales usando programación funcional
        BigDecimal subtotal = calcularSubtotal(items);
        BigDecimal impuesto = calcularImpuesto(subtotal);
        BigDecimal descuento = BigDecimal.ZERO; // Por ahora sin descuentos
        BigDecimal total = subtotal.add(impuesto).subtract(descuento);
        
        // Generar número de factura
        String numeroFactura = facturaDAO.obtenerSiguienteNumeroFactura();
        
        // Crear factura
        Factura factura = new Factura(
            duenoId, numeroFactura, LocalDateTime.now(), 
            subtotal, impuesto, descuento, total, 
            metodoPago, Factura.EstadoFactura.PENDIENTE, observaciones
        );
        
        // Guardar factura
        Factura facturaGuardada = facturaDAO.save(factura);
        
        // Guardar items
        for (ItemFactura item : items) {
            item.setFacturaId(facturaGuardada.getId());
            facturaDAO.insertarItemFactura(item);
        }
        
        facturaGuardada.setItems(items);
        return facturaGuardada;
    }

    /**
     * Valida los datos de la factura antes de crearla
     */
    private void validarDatosFactura(Integer duenoId, List<ItemFactura> items) throws VeterinariaException {
        if (duenoId == null || duenoId <= 0) {
            throw new VeterinariaException("El ID del dueño es obligatorio y debe ser válido", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (items == null || items.isEmpty()) {
            throw new VeterinariaException("La factura debe contener al menos un item", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        // Validar que cada item tenga datos válidos
        for (ItemFactura item : items) {
            if (item.getCantidad() == null || item.getCantidad() <= 0) {
                throw new VeterinariaException("La cantidad del item debe ser mayor a 0", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
            
            if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new VeterinariaException("El precio unitario del item debe ser mayor a 0", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
            
            if (item.getTipoItem() == null) {
                throw new VeterinariaException("El tipo de item es obligatorio", 
                                             VeterinariaException.ErrorType.VALIDATION_ERROR);
            }
        }
    }

    /**
     * Calcula el subtotal de los items usando Stream API
     */
    private BigDecimal calcularSubtotal(List<ItemFactura> items) {
        return items.stream()
            .map(ItemFactura::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula el impuesto (19% IVA)
     */
    private BigDecimal calcularImpuesto(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.19"));
    }

    /**
     * Obtiene una factura por su ID con todos sus items
     */
    public Optional<Factura> obtenerFacturaCompleta(Integer id) throws VeterinariaException {
        Optional<Factura> facturaOpt = facturaDAO.findById(id);
        
        if (facturaOpt.isPresent()) {
            Factura factura = facturaOpt.get();
            List<ItemFactura> items = facturaDAO.obtenerItemsPorFacturaId(id);
            factura.setItems(items);
            return Optional.of(factura);
        }
        
        return Optional.empty();
    }

    /**
     * Genera la representación en texto plano de una factura
     */
    public String generarFacturaTextoPlano(Integer facturaId) throws VeterinariaException {
        Optional<Factura> facturaOpt = obtenerFacturaCompleta(facturaId);
        
        if (facturaOpt.isEmpty()) {
            throw new VeterinariaException("No se encontró la factura con ID: " + facturaId, 
                                         VeterinariaException.ErrorType.NOT_FOUND_ERROR);
        }
        
        Factura factura = facturaOpt.get();
        return generarTextoFactura(factura);
    }

    /**
     * Genera el texto formateado de la factura
     */
    private String generarTextoFactura(Factura factura) {
        StringBuilder sb = new StringBuilder();
        
        // Encabezado
        sb.append("==================================================\n");
        sb.append("           CLÍNICA VETERINARIA HAPPY FEET\n");
        sb.append("          Calle Principal #123, Ciudad\n");
        sb.append("           Tel: (123) 456-7890\n");
        sb.append("           NIT: 123.456.789-0\n");
        sb.append("==================================================\n\n");
        
        // Información de la factura
        sb.append("FACTURA No: ").append(factura.getNumeroFactura()).append("\n");
        sb.append("Fecha: ").append(factura.getFechaEmision().toLocalDate()).append("\n");
        sb.append("Estado: ").append(factura.getEstado()).append("\n");
        sb.append("Método de Pago: ").append(factura.getMetodoPago()).append("\n");
        sb.append("\n");
        
        // Items
        sb.append("DETALLE DE ITEMS:\n");
        sb.append("--------------------------------------------------\n");
        
        if (factura.getItems() != null) {
            for (ItemFactura item : factura.getItems()) {
                String descripcion = obtenerDescripcionItem(item);
                sb.append(String.format("%-30s %6d $%,10.2f $%,10.2f\n",
                    truncateDescripcion(descripcion, 28),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()));
            }
        }
        
        // Totales
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%50s $%,10.2f\n", "Subtotal:", factura.getSubtotal()));
        sb.append(String.format("%50s $%,10.2f\n", "Impuesto (19%):", factura.getImpuesto()));
        sb.append(String.format("%50s $%,10.2f\n", "Descuento:", factura.getDescuento()));
        sb.append(String.format("%50s $%,10.2f\n", "TOTAL:", factura.getTotal()));
        sb.append("==================================================\n");
        
        // Observaciones
        if (factura.getObservaciones() != null && !factura.getObservaciones().isEmpty()) {
            sb.append("\nOBSERVACIONES:\n");
            sb.append(factura.getObservaciones()).append("\n");
        }
        
        // Pie de página
        sb.append("\n");
        sb.append("¡Gracias por confiar en Happy Feet!\n");
        
        return sb.toString();
    }

    private String obtenerDescripcionItem(ItemFactura item) {
        return switch (item.getTipoItem()) {
            case SERVICIO -> item.getServicioDescripcion() != null ? 
                item.getServicioDescripcion() : "Servicio veterinario";
            case PRODUCTO -> "Producto médico/farmacéutico";
        };
    }

    private String truncateDescripcion(String descripcion, int length) {
        if (descripcion.length() > length) {
            return descripcion.substring(0, length - 3) + "...";
        }
        return descripcion;
    }

    /**
     * Obtiene todos los servicios activos
     */
    public List<Servicio> obtenerTodosLosServicios() throws VeterinariaException {
        return servicioDAO.findAll();
    }

    /**
     * Obtiene un servicio por su ID
     */
    public Optional<Servicio> obtenerServicioPorId(Integer id) throws VeterinariaException {
        return servicioDAO.findById(id);
    }

    /**
     * Obtiene servicios por categoría
     */
    public List<Servicio> obtenerServiciosPorCategoria(String categoria) throws VeterinariaException {
        return servicioDAO.findByCategoria(categoria);
    }

    /**
     * Obtiene facturas por dueño
     */
    public List<Factura> obtenerFacturasPorDueno(Integer duenoId) throws VeterinariaException {
        return facturaDAO.obtenerFacturasPorDueno(duenoId);
    }
    /**
     * obtiene facturas por documento
     */
        public List<Factura> obtenerFacturasPorDocumento(String duenoDocumento) throws VeterinariaException {
        return facturaDAO.obtenerFacturasPorDocumento(duenoDocumento);
    }
    
    /**
     * Actualiza el estado de una factura
     */
    public void actualizarEstadoFactura(Integer facturaId, Factura.EstadoFactura estado) throws VeterinariaException {
        facturaDAO.actualizarEstadoFactura(facturaId, estado);
    }

    /**
     * Crea un nuevo servicio
     */
    public Servicio crearServicio(String nombre, String descripcion, String categoria, 
                                 BigDecimal precioBase, Integer duracionEstimada) throws VeterinariaException {
        
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new VeterinariaException("El nombre del servicio es obligatorio", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        if (precioBase == null || precioBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VeterinariaException("El precio base debe ser mayor a 0", 
                                         VeterinariaException.ErrorType.VALIDATION_ERROR);
        }
        
        Servicio servicio = new Servicio(nombre, descripcion, categoria, precioBase, duracionEstimada);
        return servicioDAO.save(servicio);
    }
}