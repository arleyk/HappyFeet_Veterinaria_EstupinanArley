package com.happyfeet.view;

import com.happyfeet.controller.ConsultaMedicaController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Menú para la gestión de consultas médicas
 */
public class ConsultaMedicaMenu extends BaseMenu {
    private final ConsultaMedicaController consultaMedicaController;
    
    public ConsultaMedicaMenu(Scanner scanner, ConsultaMedicaController controller) {
        super(scanner);
        this.consultaMedicaController = controller;
    }
    
    @Override
    public void mostrarMenu() {
        menuActivo = true;
        
        while (menuActivo) {
            System.out.println("\n=== GESTIÓN DE CONSULTAS MÉDICAS ===");
            System.out.println("1. Registrar Nueva Consulta Médica");
            System.out.println("2. Registrar Consulta Médica Completa");
            System.out.println("3. Listar Todas las Consultas Médicas");
            System.out.println("4. Buscar Consulta Médica por ID");
            System.out.println("5. Buscar Consultas por Mascota");
            System.out.println("6. Buscar Consultas por Veterinario");
            System.out.println("7. Estadísticas de Consultas Médicas");
            System.out.println("8. Estadísticas de Consultas por Mascota");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    @Override
    protected void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarNuevaConsultaMedica();
                break;
            case 2:
                registrarConsultaMedicaCompleta();
                break;
            case 3:
                consultaMedicaController.listarConsultasMedicas();
                break;
            case 4:
                buscarConsultaMedicaPorId();
                break;
            case 5:
                buscarConsultasPorMascota();
                break;
            case 6:
                buscarConsultasPorVeterinario();
                break;
            case 7:
                consultaMedicaController.mostrarEstadisticas();
                break;
            case 8:
                mostrarEstadisticasPorMascota();
                break;
            case 0:
                cerrarMenu();
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }
    
    private void registrarNuevaConsultaMedica() {
        System.out.println("\n=== REGISTRAR NUEVA CONSULTA MÉDICA ===");

        try {
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID del veterinario: ");
            Integer veterinarioId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Motivo de la consulta: ");
            String motivo = scanner.nextLine().trim();

            System.out.print("Síntomas: ");
            String sintomas = scanner.nextLine().trim();

            System.out.print("Diagnóstico: ");
            String diagnostico = scanner.nextLine().trim();

            boolean exito = consultaMedicaController.registrarConsultaMedica(
                mascotaId, veterinarioId, fechaHora, motivo, sintomas, diagnostico);

            if (exito) {
                System.out.println("✅ Consulta médica registrada exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar la consulta médica");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void registrarConsultaMedicaCompleta() {
        System.out.println("\n=== REGISTRAR CONSULTA MÉDICA COMPLETA ===");

        try {
            System.out.print("ID de la mascota: ");
            Integer mascotaId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID del veterinario: ");
            Integer veterinarioId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("ID de la cita (opcional, presione Enter para omitir): ");
            String citaInput = scanner.nextLine().trim();
            Integer citaId = citaInput.isEmpty() ? null : Integer.parseInt(citaInput);

            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr + ":00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.print("Motivo de la consulta: ");
            String motivo = scanner.nextLine().trim();

            System.out.print("Síntomas: ");
            String sintomas = scanner.nextLine().trim();

            System.out.print("Diagnóstico: ");
            String diagnostico = scanner.nextLine().trim();

            System.out.print("Recomendaciones: ");
            String recomendaciones = scanner.nextLine().trim();

            System.out.print("Observaciones: ");
            String observaciones = scanner.nextLine().trim();

            System.out.print("Peso registrado (opcional, presione Enter para omitir): ");
            String pesoInput = scanner.nextLine().trim();
            Double pesoRegistrado = pesoInput.isEmpty() ? null : Double.parseDouble(pesoInput);

            System.out.print("Temperatura (opcional, presione Enter para omitir): ");
            String tempInput = scanner.nextLine().trim();
            Double temperatura = tempInput.isEmpty() ? null : Double.parseDouble(tempInput);

            boolean exito = consultaMedicaController.registrarConsultaMedicaCompleta(
                mascotaId, veterinarioId, citaId, fechaHora, motivo, sintomas, 
                diagnostico, recomendaciones, observaciones, pesoRegistrado, temperatura);

            if (exito) {
                System.out.println("✅ Consulta médica registrada exitosamente");
            } else {
                System.out.println("❌ No se pudo registrar la consulta médica");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Los campos numéricos deben contener valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private void buscarConsultaMedicaPorId() {
        System.out.print("\nIngrese el ID de la consulta médica a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            consultaMedicaController.buscarConsultaMedicaPorId(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarConsultasPorMascota() {
        System.out.print("\nIngrese el ID de la mascota: ");
        try {
            int mascotaId = Integer.parseInt(scanner.nextLine().trim());
            consultaMedicaController.buscarConsultasPorMascota(mascotaId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void buscarConsultasPorVeterinario() {
        System.out.print("\nIngrese el ID del veterinario: ");
        try {
            int veterinarioId = Integer.parseInt(scanner.nextLine().trim());
            consultaMedicaController.buscarConsultasPorVeterinario(veterinarioId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
    
    private void mostrarEstadisticasPorMascota() {
        System.out.print("\nIngrese el ID de la mascota: ");
        try {
            int mascotaId = Integer.parseInt(scanner.nextLine().trim());
            consultaMedicaController.mostrarEstadisticasPorMascota(mascotaId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
        }
    }
}