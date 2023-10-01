package com.rubem.oliota.api2023.pt.repository;

import com.rubem.oliota.api2023.pt.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing customer order entities.
 */
@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    /**
     * Custom query to find reserved customer orders for a specific item.
     *
     * @param itemId The ID of the item for which reserved orders are to be found.
     * @return A list of customer orders that are reserved for the specified item and are not completed.
     */
    @Query("SELECT co FROM CustomerOrder co WHERE co.item.id = :itemId AND co.completed = false")
    List<CustomerOrder> findReservedOrdersForItemId(@Param("itemId") Long itemId);
}