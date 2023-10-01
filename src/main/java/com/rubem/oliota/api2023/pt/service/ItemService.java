package com.rubem.oliota.api2023.pt.service;

import com.rubem.oliota.api2023.pt.exception.ItemNotFoundException;
import com.rubem.oliota.api2023.pt.model.Item;
import com.rubem.oliota.api2023.pt.model.dto.ItemDTO;
import com.rubem.oliota.api2023.pt.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to items.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all existing items.
     *
     * @return A list of all items in DTO (Data Transfer Object) format.
     */
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Get an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The corresponding item in DTO (Data Transfer Object) format.
     * @throws ItemNotFoundException If the item is not found with the specified ID.
     */
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + id));
        return modelMapper.map(item, ItemDTO.class);
    }

    /**
     * Create a new item based on the provided information.
     *
     * @param newItemDTO The information of the new item in DTO (Data Transfer Object) format.
     * @return The newly created item in DTO format.
     */
    public ItemDTO createItem(ItemDTO newItemDTO) {
        Item newItem = modelMapper.map(newItemDTO, Item.class);
        newItem = itemRepository.save(newItem);
        return modelMapper.map(newItem, ItemDTO.class);
    }

    /**
     * Update an existing item based on its ID and the provided information.
     *
     * @param id             The ID of the item to update.
     * @param updatedItemDTO The updated information of the item in DTO (Data Transfer Object) format.
     * @return The updated item in DTO format.
     * @throws ItemNotFoundException If the item is not found with the specified ID.
     */
    public ItemDTO updateItem(Long id, ItemDTO updatedItemDTO) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + id));
        modelMapper.map(updatedItemDTO, existingItem); // Update fields of the existing item
        existingItem = itemRepository.save(existingItem);
        return modelMapper.map(existingItem, ItemDTO.class);
    }

    /**
     * Delete an existing item based on its ID.
     *
     * @param id The ID of the item to delete.
     * @return true if the item is successfully deleted, false if the item is not found.
     */
    public boolean deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
