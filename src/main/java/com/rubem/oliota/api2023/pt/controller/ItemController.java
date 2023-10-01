package com.rubem.oliota.api2023.pt.controller;

import com.rubem.oliota.api2023.pt.model.dto.ItemDTO;
import com.rubem.oliota.api2023.pt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing items.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Get all items.
     *
     * @return A ResponseEntity containing the list of items and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Get an item based on its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A ResponseEntity containing the requested item and HTTP status 200 (OK) if found.
     * If the item is not found, returns an HTTP status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO item = itemService.getItemById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new item.
     *
     * @param newItemDTO The DTO (Data Transfer Object) representing the new item to be created.
     * @return A ResponseEntity containing the newly created item and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO newItemDTO) {
        ItemDTO newItem = itemService.createItem(newItemDTO);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    /**
     * Update an item based on its ID.
     *
     * @param id             The ID of the item to update.
     * @param updatedItemDTO The DTO (Data Transfer Object) representing the updated item.
     * @return A ResponseEntity containing the updated item and HTTP status 200 (OK) if found.
     * If the item is not found, returns an HTTP status 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO updatedItemDTO) {
        ItemDTO item = itemService.updateItem(id, updatedItemDTO);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an item based on its ID.
     *
     * @param id The ID of the item to be deleted.
     * @return A ResponseEntity with an HTTP status 204 (No Content) if the item is deleted successfully.
     * If the item is not found, returns an HTTP status 404 (Not Found).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean deleted = itemService.deleteItem(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
