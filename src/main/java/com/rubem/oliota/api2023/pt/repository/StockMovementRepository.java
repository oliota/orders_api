package com.rubem.oliota.api2023.pt.repository;

import com.rubem.oliota.api2023.pt.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing stock movement entities.
 */
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    /**
     * Find stock movements by item ID where the customer order is null.
     *
     * @param itemId The ID of the item.
     * @return A list of stock movements.
     */
    List<StockMovement> findByItemIdAndCustomerOrderIsNull(Long itemId);
}