package com.rubem.oliota.api2023.pt.exception;

public class StockMovementNotFoundException extends RuntimeException {

    public StockMovementNotFoundException() {
        super();
    }

    public StockMovementNotFoundException(String message) {
        super(message);
    }

    public StockMovementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}