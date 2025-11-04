package com.happyfeet.view;

import java.util.Scanner;

/**
 * Clase base abstracta para todos los menús del sistema
 * Proporciona funcionalidad común para la gestión de menús
 */
public abstract class BaseMenu {
    protected final Scanner scanner;
    protected boolean menuActivo;
    
    public BaseMenu(Scanner scanner) {
        this.scanner = scanner;
        this.menuActivo = true;
    }
    
    /**
     * Lee y valida la opción ingresada por el usuario
     */
    protected int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }
    
    /**
     * Muestra el menú específico de cada subclase
     */
    public abstract void mostrarMenu();
    
    /**
     * Procesa la opción seleccionada por el usuario
     */
    protected abstract void procesarOpcion(int opcion);
    
    /**
     * Cierra el menú actual
     */
    public void cerrarMenu() {
        this.menuActivo = false;
    }
}