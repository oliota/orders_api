package com.rubem.oliota.api2023.pt.controller;

import com.rubem.oliota.api2023.pt.exception.StockMovementNotFoundException;
import com.rubem.oliota.api2023.pt.model.StockMovement;
import com.rubem.oliota.api2023.pt.model.dto.StockMovementDTO;
import com.rubem.oliota.api2023.pt.service.StockMovementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for operations related to stock movements.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stock-movements")
public class StockMovementController {
    private final StockMovementService stockMovementService;
    private final ModelMapper modelMapper;

    /**
     * Constructor to inject the necessary dependencies.
     *
     * @param stockMovementService The stock movement service.
     * @param modelMapper          The ModelMapper for mapping objects to DTOs.
     */
    @Autowired
    public StockMovementController(StockMovementService stockMovementService, ModelMapper modelMapper) {
        this.stockMovementService = stockMovementService;
        this.modelMapper = modelMapper;
    }

    /**
     * Get all existing stock movements.
     *
     * @return A list of all stock movements in DTO (Data Transfer Object) format.
     */
    @GetMapping
    public ResponseEntity<List<StockMovementDTO>> getAllStockMovements() {
        List<StockMovement> stockMovements = stockMovementService.getAllStockMovements();
        List<StockMovementDTO> stockMovementDTOs = stockMovements.stream()
                .map(stockMovement -> modelMapper.map(stockMovement, StockMovementDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(stockMovementDTOs, HttpStatus.OK);
    }

    /**
     * Get a stock movement by its ID.
     *
     * @param id The ID of the stock movement to retrieve.
     * @return The corresponding stock movement in DTO (Data Transfer Object) format.
     * @throws StockMovementNotFoundException If the stock movement is not found with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDTO> getStockMovementById(@PathVariable Long id) {
        StockMovement stockMovement = stockMovementService.getStockMovementById(id);
        if (stockMovement != null) {
            StockMovementDTO stockMovementDTO = modelMapper.map(stockMovement, StockMovementDTO.class);
            return new ResponseEntity<>(stockMovementDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new stock movement based on the provided information.
     *
     * @param newStockMovementDTO The information of the new stock movement in DTO (Data Transfer Object) format.
     * @return The newly created stock movement in DTO format.
     */
    @PostMapping
    public ResponseEntity<StockMovementDTO> createStockMovement(@RequestBody StockMovementDTO newStockMovementDTO) {
        StockMovement stockMovement = modelMapper.map(newStockMovementDTO, StockMovement.class);
        StockMovement createdStockMovement = stockMovementService.createStockMovement(stockMovement);
        StockMovementDTO createdStockMovementDTO = modelMapper.map(createdStockMovement, StockMovementDTO.class);
        return new ResponseEntity<>(createdStockMovementDTO, HttpStatus.CREATED);
    }

    /**
     * Update an existing stock movement based on its ID and the provided information.
     *
     * @param id                      The ID of the stock movement to update.
     * @param updatedStockMovementDTO The updated information of the stock movement in DTO format.
     * @return The updated stock movement in DTO format.
     * @throws StockMovementNotFoundException If the stock movement is not found with the specified ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockMovementDTO> updateStockMovement(
            @PathVariable Long id,
            @RequestBody StockMovementDTO updatedStockMovementDTO) {

        StockMovement existingStockMovement = stockMovementService.getStockMovementById(id);
        if (existingStockMovement != null) {
            modelMapper.map(updatedStockMovementDTO, existingStockMovement);
            StockMovement updatedStockMovement = stockMovementService.updateStockMovement(existingStockMovement);
            StockMovementDTO response = modelMapper.map(updatedStockMovement, StockMovementDTO.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an existing stock movement based on its ID.
     *
     * @param id The ID of the stock movement to delete.
     * @return HttpStatus.NO_CONTENT if the stock movement is successfully deleted, or HttpStatus.NOT_FOUND if the stock movement is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
        boolean isDeleted = stockMovementService.deleteStockMovement(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}