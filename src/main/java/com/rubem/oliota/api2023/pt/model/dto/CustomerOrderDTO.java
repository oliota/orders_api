package com.rubem.oliota.api2023.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerOrderDTO {
    private Long id;
    private LocalDateTime creationDate;
    private List<StockMovementDTO> stockMovements;
    private Long userId;
    private Long itemId;
    private int quantity;
}
