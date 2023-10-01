package com.rubem.oliota.api2023.pt.service;

import com.rubem.oliota.api2023.pt.model.StockMovement;
import com.rubem.oliota.api2023.pt.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing stock movements.
 */
@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final LoggingService loggingService;

    /**
     * Constructs a new StockMovementService with the provided dependencies.
     *
     * @param stockMovementRepository The repository for stock movements.
     * @param loggingService          The logging service for logging stock movements.
     */
    @Autowired
    public StockMovementService(StockMovementRepository stockMovementRepository,
                                LoggingService loggingService) {
        this.stockMovementRepository = stockMovementRepository;
        this.loggingService = loggingService;
    }

    /**
     * Retrieves a list of all stock movements.
     *
     * @return A list of all stock movements.
     */
    public List<StockMovement> getAllStockMovements() {
        return stockMovementRepository.findAll();
    }

    /**
     * Retrieves a stock movement by its ID.
     *
     * @param id The ID of the stock movement to retrieve.
     * @return The stock movement with the specified ID, or null if not found.
     */
    public StockMovement getStockMovementById(Long id) {
        return stockMovementRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new stock movement and logs it.
     *
     * @param stockMovement The stock movement to create.
     * @return The created stock movement.
     */
    public StockMovement createStockMovement(StockMovement stockMovement) {
        StockMovement saved = stockMovementRepository.save(stockMovement);
        loggingService.logStockMovement(saved);

        return saved;
    }

    /**
     * Updates an existing stock movement.
     *
     * @param stockMovement The stock movement to update.
     * @return The updated stock movement.
     */
    public StockMovement updateStockMovement(StockMovement stockMovement) {
        return stockMovementRepository.save(stockMovement);
    }

    /**
     * Deletes a stock movement by its ID.
     *
     * @param id The ID of the stock movement to delete.
     * @return True if the stock movement was deleted, false if it was not found.
     */
    public boolean deleteStockMovement(Long id) {
        if (stockMovementRepository.existsById(id)) {
            stockMovementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Calculates available stock movements for a specific item.
     *
     * @param itemId The ID of the item for which to calculate available stock movements.
     * @return A list of available stock movements for the specified item.
     */
    public List<StockMovement> calculateAvailableStockForItem(Long itemId) {
        return stockMovementRepository.findByItemIdAndCustomerOrderIsNull(itemId);
    }
}