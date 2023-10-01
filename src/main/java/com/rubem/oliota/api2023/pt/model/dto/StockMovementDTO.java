package com.rubem.oliota.api2023.pt.model.dto;


import lombok.Data;

@Data

public class StockMovementDTO {
    private Long id;
    private Long itemId;
    private Integer quantity;
    private Long customerOrderId;

}