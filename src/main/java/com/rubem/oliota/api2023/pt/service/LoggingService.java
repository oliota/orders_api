package com.rubem.oliota.api2023.pt.service;

import com.rubem.oliota.api2023.pt.model.CustomerOrder;
import com.rubem.oliota.api2023.pt.model.StockMovement;
import com.rubem.oliota.api2023.pt.model.UserSystem;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
/**
 * Service class for logging various events and errors.
 */
@Service
public class LoggingService {

    private static final String LOG_FILE_PATH = "logfile.txt";

    /**
     * Logs the completion of a customer order.
     *
     * @param order The customer order that was completed.
     */
    public void logOrderCompletion(CustomerOrder order) {
        String logMessage = String.format("[%s] Order completed: ID %d", LocalDateTime.now(), order.getId());
        writeToLogFile(logMessage);
    }

    /**
     * Logs a stock movement event.
     *
     * @param stockMovement The stock movement event to log.
     */
    public void logStockMovement(StockMovement stockMovement) {
        String logMessage = String.format("[%s] Stock Movement: ID %d", LocalDateTime.now(), stockMovement.getId());
        writeToLogFile(logMessage);
    }

    /**
     * Logs the successful sending of an email notification.
     *
     * @param user    The user to whom the email was sent.
     * @param message The email message.
     */
    public void logEmailSent(UserSystem user, String message) {
        String logMessage = "Email sent successfully to: " + user.getEmail() + "\nMessage: " + message;
        System.out.println(logMessage);
    }

    /**
     * Logs an error message.
     *
     * @param errorMessage The error message to log.
     */
    public void logError(String errorMessage) {
        String logMessage = "Error: " + errorMessage;
        System.err.println(logMessage);
    }

    private void writeToLogFile(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) {
            message += System.lineSeparator();
            fileWriter.write(message);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
