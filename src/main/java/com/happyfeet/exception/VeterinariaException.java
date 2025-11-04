package com.happyfeet.exception;

/**
 * Excepción personalizada para el sistema veterinario Happy Feet
 * Aplica el principio de responsabilidad única (SRP) para manejo específico de errores
 */
public class VeterinariaException extends Exception {
    
    private final ErrorType errorType;
    
    public enum ErrorType {
        DATABASE_ERROR,
        VALIDATION_ERROR,
        BUSINESS_RULE_ERROR,
        NOT_FOUND_ERROR,
        DUPLICATE_ENTRY_ERROR
    }
    
    public VeterinariaException(String message) {
        super(message);
        this.errorType = ErrorType.BUSINESS_RULE_ERROR;
    }
    
    public VeterinariaException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    public VeterinariaException(String message, Throwable cause, ErrorType errorType) {
        super(message, cause);
        this.errorType = errorType;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    @Override
    public String toString() {
        return String.format("VeterinariaException{errorType=%s, message=%s}", errorType, getMessage());
    }
}