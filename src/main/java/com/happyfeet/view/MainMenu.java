package com.happyfeet.view;

import com.happyfeet.controller.*;
import com.happyfeet.exception.VeterinariaException;
import com.happyfeet.util.DatabaseConnection;
import java.util.Scanner;

/**
 * Menú principal del sistema veterinario Happy Feet
 * Patrón MVC: Vista - Interfaz de usuario por consola
 * 
 * Responsabilidades:
 * - Coordinar todos los menús especializados
 * - Gestionar el ciclo de vida del sistema
 * - Proporcionar navegación entre módulos
 */
public class MainMenu {
    private final Scanner scanner;
    private final DuenoMenu duenoMenu;
    private final MascotaMenu mascotaMenu;
    private final CitaMenu citaMenu;
    private final VeterinarioMenu veterinarioMenu;
    private final ConsultaMedicaMenu consultaMedicaMenu;
    private final ProcedimientoEspecialMenu procedimientoEspecialMenu;
    private final ProveedorMenu proveedorMenu;
    private final InventarioMenu inventarioMenu;
    private final AdopcionMenu adopcionMenu;
    private final MascotaAdopcionMenu mascotaAdopcionMenu;
    private final JornadasVacunacionMenu jornadasVacunacionMenu;
    private final RegistroJornadaVacunacionMenu registroJornadaVacunacionMenu;
    private final FacturacionMenu facturacionMenu;
    private final ReporteMenu reporteMenu;
    private boolean sistemaActivo;

    public MainMenu() throws VeterinariaException {
        this.scanner = new Scanner(System.in);
        
        // Inicializar controladores
        DuenoController duenoController = new DuenoController();
        MascotaController mascotaController = new MascotaController();
        CitaController citaController = new CitaController();
        VeterinarioController veterinarioController = new VeterinarioController();
        ConsultaMedicaController consultaMedicaController = new ConsultaMedicaController();
        ProcedimientoEspecialController procedimientoEspecialController = new ProcedimientoEspecialController();
        ProveedorController proveedorController = new ProveedorController();
        InventarioController inventarioController = new InventarioController();
        
        // Inicializar menús especializados
        this.duenoMenu = new DuenoMenu(scanner, duenoController);
        this.mascotaMenu = new MascotaMenu(scanner, mascotaController);
        this.citaMenu = new CitaMenu(scanner, citaController);
        this.veterinarioMenu = new VeterinarioMenu(scanner, veterinarioController);
        this.consultaMedicaMenu = new ConsultaMedicaMenu(scanner, consultaMedicaController);
        this.procedimientoEspecialMenu = new ProcedimientoEspecialMenu(scanner, procedimientoEspecialController);
        this.proveedorMenu = new ProveedorMenu(scanner, proveedorController);
        this.inventarioMenu = new InventarioMenu(scanner, inventarioController);
        this.adopcionMenu = new AdopcionMenu(scanner);
        this.mascotaAdopcionMenu = new MascotaAdopcionMenu(scanner);
        this.jornadasVacunacionMenu = new JornadasVacunacionMenu(scanner);
        this.registroJornadaVacunacionMenu = new RegistroJornadaVacunacionMenu(scanner);
        this.facturacionMenu = new FacturacionMenu(scanner);
        this.reporteMenu = new ReporteMenu(scanner);
        
        this.sistemaActivo = true;
    }

    /**
     * Inicia el sistema y muestra el menú principal
     */
    public void iniciar() {
        System.out.println("🐾 BIENVENIDO AL SISTEMA VETERINARIO HAPPY FEET 🐾");
        System.out.println("===================================================");

        while (sistemaActivo) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }

        cerrarSistema();
    }

    /**
     * Muestra el menú principal con las opciones disponibles
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Dueños");
        System.out.println("2. Gestión de Mascotas");
        System.out.println("3. Gestión de Citas");
        System.out.println("4. Gestión de Consultas Médicas");
        System.out.println("5. Gestión de Veterinarios");
        System.out.println("6. Gestión de Inventario");
        System.out.println("7. Gestión de Proveedores");
        System.out.println("8. Gestión de Mascotas en Adopción");
        System.out.println("9. Gestión de Adopciones");
        System.out.println("10. Gestión de Jornadas de Vacunación");
        System.out.println("11. Gestión de Registros de Vacunación");
        System.out.println("12. Gestión de Procedimientos Especiales");
        System.out.println("13. Módulo de Facturación");
        System.out.println("14. Módulo de Reportes");
        System.out.println("0. Salir del Sistema");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Lee y valida la opción ingresada por el usuario
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    /**
     * Procesa la opción seleccionada por el usuario
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                duenoMenu.mostrarMenu();
                break;
            case 2:
                mascotaMenu.mostrarMenu();
                break;
            case 3:
                citaMenu.mostrarMenu();
                break;
            case 4:
                consultaMedicaMenu.mostrarMenu();
                break;
            case 5:
                veterinarioMenu.mostrarMenu();
                break;
            case 6:
                inventarioMenu.mostrarMenu();
                break;
            case 7:
                proveedorMenu.mostrarMenu();
                break;
            case 8:
                mascotaAdopcionMenu.mostrarMenu();
                break;
            case 9:
                adopcionMenu.mostrarMenu();
                break;
            case 10:
                jornadasVacunacionMenu.mostrarMenu();
                break;
            case 11:
                registroJornadaVacunacionMenu.mostrarMenu();
                break;
            case 12:
                procedimientoEspecialMenu.mostrarMenu();
                break;
            case 13:
                facturacionMenu.mostrarMenu();
                break;
            case 14:
                reporteMenu.mostrarMenu();
                break;
            case 0:
                sistemaActivo = false;
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("❌ Opción inválida. Por favor, seleccione una opción válida.");
        }
    }

    /**
     * Cierra el sistema y libera recursos
     */
    private void cerrarSistema() {
        try {
            scanner.close();
            DatabaseConnection.getInstance().closeConnection();
            System.out.println("✅ Sistema cerrado correctamente. ¡Hasta pronto! 🐕🐈");
        } catch (Exception e) {
            System.err.println("Error al cerrar el sistema: " + e.getMessage());
        }
    }
}