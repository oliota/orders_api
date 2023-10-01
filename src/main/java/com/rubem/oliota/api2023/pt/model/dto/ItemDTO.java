package com.rubem.oliota.api2023.pt.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private int quantity;
    private List<StockMovementDTO> stockMovements;

}
