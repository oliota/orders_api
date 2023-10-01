package com.rubem.oliota.api2023.pt.controller;

import com.rubem.oliota.api2023.pt.exception.InsufficientStockException;
import com.rubem.oliota.api2023.pt.model.CustomerOrder;
import com.rubem.oliota.api2023.pt.model.StockMovement;
import com.rubem.oliota.api2023.pt.model.dto.CustomerOrderDTO;
import com.rubem.oliota.api2023.pt.service.CustomerOrderService;
import com.rubem.oliota.api2023.pt.service.LoggingService;
import com.rubem.oliota.api2023.pt.service.StockMovementService;
import com.rubem.oliota.api2023.pt.service.UserSystemService;
import com.rubem.oliota.api2023.pt.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing customer orders.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer-orders")
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;
    private final StockMovementService stockMovementService;
    private final ModelMapper modelMapper;
    private final LoggingService loggingService;
    private final UserSystemService userSystemService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, StockMovementService stockMovementService, ModelMapper modelMapper, LoggingService loggingService, UserSystemService userSystemService) {
        this.customerOrderService = customerOrderService;
        this.stockMovementService = stockMovementService;
        this.modelMapper = modelMapper;
        this.loggingService = loggingService;
        this.userSystemService = userSystemService;
    }

    /**
     * Get a list of all customer orders.
     *
     * @return ResponseEntity containing a list of CustomerOrderDTOs.
     */
    @GetMapping
    public ResponseEntity<List<CustomerOrderDTO>> getAllCustomerOrders() {
        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
        List<CustomerOrderDTO> customerOrderDTOs = customerOrders.stream()
                .map(customerOrder -> modelMapper.map(customerOrder, CustomerOrderDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerOrderDTOs, HttpStatus.OK);
    }

    /**
     * Get a customer order by its ID.
     *
     * @param id The ID of the customer order to retrieve.
     * @return ResponseEntity containing the CustomerOrderDTO if found, or HttpStatus.NOT_FOUND if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> getCustomerOrderById(@PathVariable Long id) {
        CustomerOrder customerOrder = customerOrderService.getCustomerOrderById(id);
        if (customerOrder != null) {
            CustomerOrderDTO customerOrderDTO = modelMapper.map(customerOrder, CustomerOrderDTO.class);
            return new ResponseEntity<>(customerOrderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new customer order.
     *
     * @param newCustomerOrderDTO The CustomerOrderDTO containing information for the new customer order.
     * @return ResponseEntity containing the created CustomerOrderDTO if successful, or an error message with HttpStatus.BAD_REQUEST if the order cannot be created.
     * @throws InsufficientStockException If there is insufficient stock to fulfill the order.
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerOrderDTO> createCustomerOrder(@RequestBody CustomerOrderDTO newCustomerOrderDTO) {
        CustomerOrder newCustomerOrder = modelMapper.map(newCustomerOrderDTO, CustomerOrder.class);

        int reservedStock = customerOrderService.calculateReservedStockForItem(newCustomerOrderDTO.getItemId());
        int requiredQuantity = newCustomerOrderDTO.getQuantity();

        List<StockMovement> stockMovements = stockMovementService.calculateAvailableStockForItem(newCustomerOrderDTO.getItemId());

        int availableStock = 0;
        List<StockMovement> usedStockMovements = new ArrayList<>();

        for (StockMovement movement : stockMovements) {
            int movementQuantity = movement.getQuantity();
            if (movementQuantity > 0) {
                availableStock += movementQuantity;
                movement.setCustomerOrder(newCustomerOrder);
                usedStockMovements.add(movement);
                break;
            }
        }

        if (availableStock > 0 && availableStock >= requiredQuantity) {
            for (StockMovement usedMovement : usedStockMovements) {
                stockMovementService.updateStockMovement(usedMovement);
            }

            newCustomerOrder.setUserSystem(userSystemService.getUserById(newCustomerOrderDTO.getUserId()));
            newCustomerOrder.setCompleted(false);
            newCustomerOrder.setCreationDate(LocalDateTime.now());

            newCustomerOrder = customerOrderService.createCustomerOrder(newCustomerOrder);

            StockMovement stockMovement = new StockMovement();
            stockMovement.setCustomerOrder(newCustomerOrder);
            stockMovement.setItem(newCustomerOrder.getItem());
            stockMovement.setQuantity(newCustomerOrderDTO.getQuantity());
            stockMovement.setCreationDate(LocalDateTime.now());

            stockMovementService.createStockMovement(stockMovement);

            CustomerOrderDTO createdCustomerOrderDTO = modelMapper.map(newCustomerOrder, CustomerOrderDTO.class);


            return new ResponseEntity<>(createdCustomerOrderDTO, HttpStatus.CREATED);
        } else {
            String errorMessage = "Insufficient stock for item " + newCustomerOrderDTO.getItemId() +
                    " | Required Quantity: " + requiredQuantity +
                    " | Available Stock: " + availableStock +
                    " | Reserved Stock: " + reservedStock +
                    " | Input: " + Utils.convertObjectToJsonString(newCustomerOrderDTO);

            throw new InsufficientStockException(errorMessage, loggingService);
        }
    }

    /**
     * Update an existing customer order with the provided data.
     *
     * @param id                      The ID of the customer order to update.
     * @param updatedCustomerOrderDTO The CustomerOrderDTO containing updated order details.
     * @return ResponseEntity containing the updated CustomerOrderDTO if successful, or an error response if the customer order is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> updateCustomerOrder(
            @PathVariable Long id,
            @RequestBody CustomerOrderDTO updatedCustomerOrderDTO) {
        CustomerOrder existingCustomerOrder = customerOrderService.getCustomerOrderById(id);
        if (existingCustomerOrder != null) {
            CustomerOrder updatedCustomerOrder = modelMapper.map(updatedCustomerOrderDTO, CustomerOrder.class);
            updatedCustomerOrder = customerOrderService.updateCustomerOrder(id, updatedCustomerOrder);
            if (updatedCustomerOrder != null) {
                CustomerOrderDTO response = modelMapper.map(updatedCustomerOrder, CustomerOrderDTO.class);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a customer order by its ID.
     *
     * @param id The ID of the customer order to delete.
     * @return ResponseEntity indicating success (NO_CONTENT) or failure (NOT_FOUND) of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable Long id) {
        boolean isDeleted = customerOrderService.deleteCustomerOrder(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Complete a customer order with the specified ID.
     *
     * @param id The ID of the customer order to complete.
     * @return A ResponseEntity with the HTTP status and, if successful, details of the completed customer order in DTO (Data Transfer Object) format.
     *         If the order is not found, it returns an HTTP status 404 (Not Found).
     */
    @PutMapping("/complete/{id}")
    public ResponseEntity<CustomerOrderDTO> completeCustomerOrder(@PathVariable Long id) {
        CustomerOrder completedOrder = customerOrderService.completeCustomerOrder(id);

        if (completedOrder != null) {
            CustomerOrderDTO completedOrderDTO = modelMapper.map(completedOrder, CustomerOrderDTO.class);


            loggingService.logOrderCompletion(completedOrder);
            return new ResponseEntity<>(completedOrderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
