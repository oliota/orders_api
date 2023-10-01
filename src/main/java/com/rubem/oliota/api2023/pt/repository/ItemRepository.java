package com.rubem.oliota.api2023.pt.repository;

import com.rubem.oliota.api2023.pt.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for managing item entities.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}