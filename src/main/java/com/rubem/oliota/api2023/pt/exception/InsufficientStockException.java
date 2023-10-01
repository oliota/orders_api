package com.rubem.oliota.api2023.pt.exception;

import com.rubem.oliota.api2023.pt.service.LoggingService;

public class InsufficientStockException extends RuntimeException {
    private final LoggingService loggingService;

    public InsufficientStockException(String message, LoggingService loggingService) {
        super(message);
        this.loggingService = loggingService;

        // Registre o erro usando o serviço de log
        loggingService.logError(message);
    }
}
